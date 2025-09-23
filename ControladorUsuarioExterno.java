package controlador;


import modelo.Libro;
import repositorio.BibliotecaRepositorio;
import repositorio.IRepositorio;
import vista.VistaUsuarioExterno;
import java.util.List;

public class ControladorUsuarioExterno implements IControladorUsuarioExterno {
    private IRepositorio<Libro> repositorioLibros;
    private VistaUsuarioExterno vista;
    
    public ControladorUsuarioExterno(IRepositorio<Libro> repositorioLibros,
                                   VistaUsuarioExterno vista) {
        this.repositorioLibros = repositorioLibros;
        this.vista = vista;
    }
    
    @Override
    public void ejecutar() {
        vista.mostrarBienvenida();
        
        int opcion;
        do {
            vista.mostrarMenuPublico();
            opcion = vista.leerOpcion();
            procesarOpcion(opcion);
        } while (opcion != 0);
        
        vista.mostrarDespedida();
    }
    
    private void procesarOpcion(int opcion) {
        switch (opcion) {
            case 1: mostrarCatalogoCompleto(); break;
            case 2: buscarPorTitulo(); break;
            case 3: buscarPorAutor(); break;
            case 4: mostrarLibrosDisponibles(); break;
            case 5: mostrarEstadisticas(); break;
            case 6: mostrarLibrosPopulares(); break;
            case 7: mostrarAyuda(); break;
            case 8: mostrarContacto(); break;
            case 0: break;
            default: 
                vista.mostrarMensaje("❌ Opción inválida. Por favor intenta nuevamente.");
        }
        
        if (opcion != 0 && opcion >= 1 && opcion <= 8) {
            vista.pausa();
        }
    }
    
    @Override
    public void mostrarCatalogoCompleto() {
        List<Libro> libros = repositorioLibros.obtenerTodos();
        vista.mostrarCatalogo(libros);
    }
    
    @Override
    public void buscarPorTitulo() {
        String titulo = vista.leerTexto("🔍 Ingresa el título a buscar: ");
        
        if (titulo.isEmpty()) {
            vista.mostrarMensaje("❌ Por favor ingresa un título válido.");
            return;
        }
        
        BibliotecaRepositorio repo = (BibliotecaRepositorio) repositorioLibros;
        List<Libro> resultados = repo.buscarPorTitulo(titulo);
        vista.mostrarResultadoBusqueda(resultados, titulo);
    }
    
    @Override
    public void buscarPorAutor() {
        String autor = vista.leerTexto("🔍 Ingresa el autor a buscar: ");
        
        if (autor.isEmpty()) {
            vista.mostrarMensaje("❌ Por favor ingresa un autor válido.");
            return;
        }
        
        BibliotecaRepositorio repo = (BibliotecaRepositorio) repositorioLibros;
        List<Libro> resultados = repo.buscarPorAutor(autor);
        vista.mostrarResultadoBusqueda(resultados, autor);
    }
    
    @Override
    public void mostrarLibrosDisponibles() {
        BibliotecaRepositorio repo = (BibliotecaRepositorio) repositorioLibros;
        List<Libro> librosDisponibles = repo.obtenerLibrosDisponibles();
        
        System.out.println("\n✅ LIBROS DISPONIBLES PARA PRÉSTAMO:");
        vista.mostrarCatalogo(librosDisponibles);
    }
    
    @Override
    public void mostrarEstadisticas() {
        List<Libro> todosLosLibros = repositorioLibros.obtenerTodos();
        BibliotecaRepositorio repo = (BibliotecaRepositorio) repositorioLibros;
        
        int totalLibros = todosLosLibros.size();
        int librosDisponibles = repo.obtenerLibrosDisponibles().size();
        int totalUsuarios = 58; // Simulado - en implementación real vendría del repositorio
        int prestamosHoy = 12; // Simulado - en implementación real vendría del repositorio
        
        vista.mostrarEstadisticasPublicas(totalLibros, librosDisponibles, 
                                        totalUsuarios, prestamosHoy);
    }
    
    @Override
    public void mostrarLibrosPopulares() {
        vista.mostrarLibrosPopulares();
    }
    
    @Override
    public void mostrarAyuda() {
        vista.mostrarAyudaPrestamos();
    }
    
    @Override
    public void mostrarContacto() {
        vista.mostrarInformacionContacto();
    }
}