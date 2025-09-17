package aplicacion;

import modelo.Libro;
import repositorio.IRepositorio;
import repositorio.BibliotecaRepositorio;
import vista.IVista;
import vista.BibliotecaVista;
import controlador.IControlador;
import controlador.BibliotecaControlador;

public class AplicacionMain {
    public static void main(String[] args) {
        AplicacionMain();
    }
    
    private static void AplicacionMain() {
        // Crear instancias
        IRepositorio<Libro> repositorio = new BibliotecaRepositorio();
        IVista vista = new BibliotecaVista();
        IControlador controlador = new BibliotecaControlador(repositorio, vista);
        
        // Agregar algunos datos de ejemplo
        repositorio.crear(new Libro("El Quijote", "Miguel de Cervantes", 3));
        repositorio.crear(new Libro("Cien años de soledad", "Gabriel García Márquez", 2));
        repositorio.crear(new Libro("1984", "George Orwell", 5));
        
        // Ejecutar aplicación
        controlador.ejecutar();
    }
}