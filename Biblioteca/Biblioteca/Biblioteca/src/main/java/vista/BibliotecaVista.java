package vista;

import modelo.Libro;
import java.util.*;

public class BibliotecaVista implements IVista {
    private Scanner scanner;
    
    public BibliotecaVista() {
        this.scanner = new Scanner(System.in);
    }
    
    @Override
    public void mostrarMenu() {
        System.out.println("\n=== SISTEMA DE BIBLIOTECA ===");
        System.out.println("1. Agregar libro");
        System.out.println("2. Listar todos los libros");
        System.out.println("3. Buscar libro");
        System.out.println("4. Prestar libro");
        System.out.println("5. Devolver libro");
        System.out.println("6. Eliminar libro");
        System.out.println("0. Salir");
        System.out.print("Seleccione una opción: ");
    }
    
    @Override
    public void mostrarLibros(List<Libro> libros) {
        if (libros.isEmpty()) {
            System.out.println("No hay libros registrados.");
            return;
        }
        
        System.out.println("\n=== LISTA DE LIBROS ===");
        for (Libro libro : libros) {
            System.out.println(libro);
        }
    }
    
    @Override
    public void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }
    
    @Override
    public int leerOpcion() {
        try {
            return scanner.nextInt();
        } catch (Exception e) {
            scanner.nextLine(); // Limpiar buffer
            return -1;
        }
    }
    
    @Override
    public String leerTexto(String prompt) {
        System.out.print(prompt);
        scanner.nextLine(); // Limpiar buffer
        return scanner.nextLine();
    }
    
    @Override
    public int leerNumero(String prompt) {
        System.out.print(prompt);
        try {
            return scanner.nextInt();
        } catch (Exception e) {
            scanner.nextLine(); // Limpiar buffer
            return -1;
        }
    }
    
    // Método específico
    public Libro solicitarDatosLibro() {
        String titulo = leerTexto("Ingrese el título: ");
        String autor = leerTexto("Ingrese el autor: ");
        int cantidad = leerNumero("Ingrese la cantidad disponible: ");
        
        return new Libro(titulo, autor, cantidad);
    }
}