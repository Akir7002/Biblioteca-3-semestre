package controlador;

import modelo.Libro;
import repositorio.IRepositorio;
import repositorio.BibliotecaRepositorio;
import vista.IVista;
import vista.BibliotecaVista;
import java.util.List;

public class BibliotecaControlador implements IControlador {
    private IRepositorio<Libro> repositorio;
    private IVista vista;
    
    public BibliotecaControlador(IRepositorio<Libro> repositorio, IVista vista) {
        this.repositorio = repositorio;
        this.vista = vista;
    }
    
    @Override
    public void ejecutar() {
        int opcion;
        do {
            vista.mostrarMenu();
            opcion = vista.leerOpcion();
            procesarOpcion(opcion);
        } while (opcion != 0);
        
        vista.mostrarMensaje("¡Gracias por usar el sistema!");
    }
    
    private void procesarOpcion(int opcion) {
        switch (opcion) {
            case 1:
                agregarLibro();
                break;
            case 2:
                listarLibros();
                break;
            case 3:
                buscarLibro();
                break;
            case 4:
                prestarLibro();
                break;
            case 5:
                devolverLibro();
                break;
            case 6:
                eliminarLibro();
                break;
            case 0:
                // Salir
                break;
            default:
                vista.mostrarMensaje("Opción inválida. Intente nuevamente.");
        }
    }
    
    @Override
    public void agregarLibro() {
        BibliotecaVista bibliotecaVista = (BibliotecaVista) vista;
        Libro nuevoLibro = bibliotecaVista.solicitarDatosLibro();
        
        if (repositorio.crear(nuevoLibro)) {
            vista.mostrarMensaje("Libro agregado exitosamente.");
        } else {
            vista.mostrarMensaje("Error al agregar el libro.");
        }
    }
    
    @Override
    public void listarLibros() {
        List<Libro> libros = repositorio.obtenerTodos();
        vista.mostrarLibros(libros);
    }
    
    @Override
    public void buscarLibro() {
        vista.mostrarMensaje("\n1. Buscar por ID");
        vista.mostrarMensaje("2. Buscar por título");
        vista.mostrarMensaje("3. Buscar por autor");
        int opcionBusqueda = vista.leerOpcion();
        
        BibliotecaRepositorio repo = (BibliotecaRepositorio) repositorio;
        
        switch (opcionBusqueda) {
            case 1:
                int id = vista.leerNumero("Ingrese el ID: ");
                Libro libro = repositorio.obtenerPorId(id);
                if (libro != null) {
                    vista.mostrarMensaje("Libro encontrado: " + libro);
                } else {
                    vista.mostrarMensaje("Libro no encontrado.");
                }
                break;
            case 2:
                String titulo = vista.leerTexto("Ingrese el título: ");
                List<Libro> librosPorTitulo = repo.buscarPorTitulo(titulo);
                vista.mostrarLibros(librosPorTitulo);
                break;
            case 3:
                String autor = vista.leerTexto("Ingrese el autor: ");
                List<Libro> librosPorAutor = repo.buscarPorAutor(autor);
                vista.mostrarLibros(librosPorAutor);
                break;
            default:
                vista.mostrarMensaje("Opción inválida.");
        }
    }
    
    @Override
    public void prestarLibro() {
        int id = vista.leerNumero("Ingrese el ID del libro a prestar: ");
        Libro libro = repositorio.obtenerPorId(id);
        
        if (libro == null) {
            vista.mostrarMensaje("Libro no encontrado.");
            return;
        }
        
        if (libro.prestar()) {
            repositorio.actualizar(libro);
            vista.mostrarMensaje("Libro prestado exitosamente.");
        } else {
            vista.mostrarMensaje("No hay ejemplares disponibles.");
        }
    }
    
    @Override
    public void devolverLibro() {
        int id = vista.leerNumero("Ingrese el ID del libro a devolver: ");
        Libro libro = repositorio.obtenerPorId(id);
        
        if (libro == null) {
            vista.mostrarMensaje("Libro no encontrado.");
            return;
        }
        
        libro.devolver();
        repositorio.actualizar(libro);
        vista.mostrarMensaje("Libro devuelto exitosamente.");
    }
    
    @Override
    public void eliminarLibro() {
        int id = vista.leerNumero("Ingrese el ID del libro a eliminar: ");
        
        if (repositorio.eliminar(id)) {
            vista.mostrarMensaje("Libro eliminado exitosamente.");
        } else {
            vista.mostrarMensaje("Libro no encontrado.");
        }
    }
}