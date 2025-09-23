package controlador;

public interface IControlador {
    void ejecutar();
    void agregarLibro();
    void listarLibros();
    void buscarLibro();
    void eliminarLibro();
    void agregarUsuario();
    void listarUsuarios();
    void buscarUsuario();
    void eliminarUsuario();
    void realizarPrestamo();
    void devolverLibro();
    void listarPrestamosActivos();
    void mostrarPrestamosVencidos();
    void mostrarEstadisticas();
}