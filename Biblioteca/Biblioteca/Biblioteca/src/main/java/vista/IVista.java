package vista;

import modelo.Libro;
import java.util.List;

public interface IVista {
    void mostrarMenu();
    void mostrarLibros(List<Libro> libros);
    void mostrarMensaje(String mensaje);
    int leerOpcion();
    String leerTexto(String prompt);
    int leerNumero(String prompt);
}