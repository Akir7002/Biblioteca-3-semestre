package controlador;

public interface IControlador {
    void ejecutar();
    void agregarLibro();
    void listarLibros();
    void buscarLibro();
    void prestarLibro();
    void devolverLibro();
    void eliminarLibro();
}