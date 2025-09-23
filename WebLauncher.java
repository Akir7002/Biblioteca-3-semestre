package vista;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import modelo.Libro;
import repositorio.IRepositorio;
import repositorio.BibliotecaRepositorio;

import java.io.*;
import java.net.*;
import java.awt.Desktop;
import java.util.List;
import java.util.stream.Collectors;

public class WebLauncher {
    private HttpServer server;
    private final IRepositorio<Libro> repositorioLibros;
    private final int puerto = 8080;

    public WebLauncher(IRepositorio<Libro> repositorioLibros) {
        this.repositorioLibros = repositorioLibros;
    }

    public void iniciar() {
        try {
            // Crear servidor HTTP
            server = HttpServer.create(new InetSocketAddress(puerto), 0);

            // Configurar rutas
            server.createContext("/", new PaginaPrincipalHandler());
            server.createContext("/api/libros", new LibrosApiHandler(repositorioLibros));

            // Iniciar servidor
            server.setExecutor(null);
            server.start();

            System.out.println("Servidor iniciado con el repositorio de libros.");
            System.out.println("Accede a la página web en: http://localhost:" + puerto);

            // Lógica para abrir automáticamente el navegador
            if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                try {
                    Desktop.getDesktop().browse(new URI("http://localhost:" + puerto));
                } catch (Exception e) {
                    System.err.println("Error al abrir el navegador: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Error al iniciar el servidor: " + e.getMessage());
        }
    }

    static class PaginaPrincipalHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String htmlResponse = """
                <!DOCTYPE html>
                <html lang="es">
                <head>
                    <meta charset="UTF-8">
                    <meta name="viewport" content="width=device-width, initial-scale=1.0">
                    <title>Catálogo de Libros</title>
                    <style>
                        body { font-family: Arial, sans-serif; margin: 0; padding: 0; background-color: #f4f4f4; color: #333; }
                        header { background-color: #4CAF50; color: white; padding: 1rem; text-align: center; }
                        .container { max-width: 1200px; margin: 2rem auto; padding: 1rem; }
                        .book { background: white; padding: 1rem; margin-bottom: 1rem; border-radius: 8px; box-shadow: 0 2px 4px rgba(0,0,0,0.1); }
                        .book h2 { color: #4CAF50; margin-top: 0; }
                    </style>
                </head>
                <body>
                    <header>
                        <h1>Sistema de Biblioteca</h1>
                    </header>
                    <div class="container">
                        <div id="books">Cargando libros...</div>
                    </div>
                    <script>
                        async function fetchBooks() {
                            try {
                                const response = await fetch('/api/libros');
                                const books = await response.json();
                                const booksContainer = document.getElementById('books');
                                booksContainer.innerHTML = books.map(book => `
                                    <div class="book">
                                        <h2>${book.title}</h2>
                                        <p>Autor: ${book.author}</p>
                                        <p>Disponibles: ${book.available}</p>
                                    </div>
                                `).join('');
                            } catch (error) {
                                console.error('Error fetching books:', error);
                                const booksContainer = document.getElementById('books');
                                booksContainer.innerHTML = '<p style="color:red;">Error al cargar los libros. Por favor, asegúrate de que el servidor está funcionando.</p>';
                            }
                        }
                        fetchBooks();
                    </script>
                </body>
                </html>
                """;
            exchange.getResponseHeaders().set("Content-Type", "text/html");
            exchange.sendResponseHeaders(200, htmlResponse.length());
            OutputStream os = exchange.getResponseBody();
            os.write(htmlResponse.getBytes());
            os.close();
        }
    }

    static class LibrosApiHandler implements HttpHandler {
        private final IRepositorio<Libro> repositorioLibros;

        public LibrosApiHandler(IRepositorio<Libro> repositorioLibros) {
            this.repositorioLibros = repositorioLibros;
        }

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String jsonResponse = repositorioLibros.obtenerTodos().stream()
                .map(libro -> String.format(
                    "{\"title\": \"%s\", \"author\": \"%s\", \"available\": %d}",
                    libro.getTitulo(), libro.getAutor(), libro.getCantidadDisponible()
                ))
                .collect(Collectors.joining(", ", "[", "]"));
                
            exchange.getResponseHeaders().set("Content-Type", "application/json");
            exchange.sendResponseHeaders(200, jsonResponse.length());
            OutputStream os = exchange.getResponseBody();
            os.write(jsonResponse.getBytes());
            os.close();
        }
    }
}