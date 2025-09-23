package aplicacion;

import modelo.Libro;
import repositorio.BibliotecaRepositorio;
import repositorio.IRepositorio;
import vista.WebLauncher;

public class AplicacionMain {

    public static void main(String[] args) {
        // Inicia el servidor web sin una ventana de JavaFX
        IRepositorio<Libro> repositorioLibros = new BibliotecaRepositorio();
        WebLauncher webLauncher = new WebLauncher(repositorioLibros);
        webLauncher.iniciar();
    }
}