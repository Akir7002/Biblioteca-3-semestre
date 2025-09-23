package controlador;

import repositorio.BibliotecaRepositorio;
import repositorio.IRepositorio;
import modelo.Libro;
import vista.VistaUsuarioExterno;
import controlador.ControladorUsuarioExterno;

public class AplicacionUsuarioExterno {
    public static void main(String[] args) {
        iniciarCatalogoPublico();
    }
    
    private static void iniciarCatalogoPublico() {
        // Crear repositorio (solo lectura)
        IRepositorio<Libro> repositorioLibros = new BibliotecaRepositorio();
        
        // Crear vista específica para usuarios externos
        VistaUsuarioExterno vista = new VistaUsuarioExterno();
        
        // Crear controlador específico
        ControladorUsuarioExterno controlador = new ControladorUsuarioExterno(
                repositorioLibros, vista);
        
        // Cargar datos de ejemplo
        cargarDatosEjemplo(repositorioLibros);
        
        // Ejecutar aplicación pública
        controlador.ejecutar();
    }
    
    private static void cargarDatosEjemplo(IRepositorio<Libro> repositorio) {
        // Libros de ejemplo para consulta pública
        repositorio.crear(new Libro("Don Quijote de la Mancha", "Miguel de Cervantes", 3));
        repositorio.crear(new Libro("Cien Años de Soledad", "Gabriel García Márquez", 2));
        repositorio.crear(new Libro("1984", "George Orwell", 4));
        repositorio.crear(new Libro("El Principito", "Antoine de Saint-Exupéry", 0)); // Agotado
        repositorio.crear(new Libro("Orgullo y Prejuicio", "Jane Austen", 3));
        repositorio.crear(new Libro("Crimen y Castigo", "Fiódor Dostoyevski", 1));
        repositorio.crear(new Libro("La Odisea", "Homero", 2));
        repositorio.crear(new Libro("Hamlet", "William Shakespeare", 0)); // Agotado
        repositorio.crear(new Libro("El Gran Gatsby", "F. Scott Fitzgerald", 2));
        repositorio.crear(new Libro("Rayuela", "Julio Cortázar", 1));
    }
}

