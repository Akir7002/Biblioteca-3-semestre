package aplicacion;

import modelo.*;
import repositorio.*;
import vista.*;
import controlador.*;

public class MainConsola {
    public static void main(String[] args) {
        iniciarAplicacion();
    }

    private static void iniciarAplicacion() {
        IRepositorio<Libro> repositorioLibros = new BibliotecaRepositorio();
        IRepositorio<Usuario> repositorioUsuarios = new UsuarioRepositorio();
        IRepositorio<Prestamo> repositorioPrestamos = new PrestamoRepositorio();
        IVista vista = new BibliotecaVista();
        IControlador controlador = new BibliotecaControlador(
                repositorioLibros, repositorioUsuarios, repositorioPrestamos, vista);
        cargarDatosEjemplo(repositorioLibros, repositorioUsuarios, repositorioPrestamos);

        controlador.ejecutar();
    }

    private static void cargarDatosEjemplo(IRepositorio<Libro> repoLibros,
                                           IRepositorio<Usuario> repoUsuarios,
                                           IRepositorio<Prestamo> repoPrestamos) {
        repoLibros.crear(new Libro("Don Quijote de la Mancha", "Miguel de Cervantes", 3));
        repoLibros.crear(new Libro("Cien Años de Soledad", "Gabriel García Márquez", 2));
        repoLibros.crear(new Libro("1984", "George Orwell", 4));
        repoLibros.crear(new Libro("El Principito", "Antoine de Saint-Exupéry", 5));
        repoLibros.crear(new Libro("Crimen y Castigo", "Fiódor Dostoyevski", 2));
        repoLibros.crear(new Libro("Orgullo y Prejuicio", "Jane Austen", 3));
    }
}