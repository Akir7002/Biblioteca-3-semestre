package controlador;

import modelo.*;
import repositorio.*;
import vista.*;
import java.util.List;
import java.util.stream.Collectors;

public class BibliotecaControlador implements IControlador {
    private final IRepositorio<Libro> repositorioLibros;
    private final IRepositorio<Usuario> repositorioUsuarios;
    private final IRepositorio<Prestamo> repositorioPrestamos;
    private final IVista vista;

    public BibliotecaControlador(IRepositorio<Libro> repositorioLibros,
                               IRepositorio<Usuario> repositorioUsuarios,
                               IRepositorio<Prestamo> repositorioPrestamos,
                               IVista vista) {
        this.repositorioLibros = repositorioLibros;
        this.repositorioUsuarios = repositorioUsuarios;
        this.repositorioPrestamos = repositorioPrestamos;
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

        vista.mostrarExito("¡Gracias por usar el Sistema de Gestión de Biblioteca!");
    }

    private void procesarOpcion(int opcion) {
        switch (opcion) {
            case 1 -> agregarLibro();
            case 2 -> listarLibros();
            case 3 -> buscarLibro();
            case 4 -> eliminarLibro();
            case 5 -> agregarUsuario();
            case 6 -> listarUsuarios();
            case 7 -> buscarUsuario();
            case 8 -> eliminarUsuario();
            case 9 -> realizarPrestamo();
            case 10 -> devolverLibro();
            case 11 -> listarPrestamosActivos();
            case 12 -> mostrarPrestamosVencidos();
            case 13 -> mostrarEstadisticas();
            case 0 -> {}
            default -> vista.mostrarError("Opción inválida. Intente nuevamente.");
        }

        if (opcion != 0) {
            vista.pausa();
        }
    }

    @Override
    public void agregarLibro() {
        BibliotecaVista bibliotecaVista = (BibliotecaVista) vista;
        Libro nuevoLibro = bibliotecaVista.solicitarDatosLibro();

        if (nuevoLibro != null && repositorioLibros.crear(nuevoLibro)) {
            vista.mostrarExito("Libro agregado exitosamente con ID: " + nuevoLibro.getId());
        } else {
            vista.mostrarError("Error al agregar el libro.");
        }
    }

    @Override
    public void listarLibros() {
        List<Libro> libros = repositorioLibros.obtenerTodos();
        vista.mostrarLibros(libros);
    }

    @Override
    public void buscarLibro() {
        vista.mostrarMensaje("\n🔍 OPCIONES DE BÚSQUEDA:");
        vista.mostrarMensaje("1. Buscar por ID");
        vista.mostrarMensaje("2. Buscar por título");
        vista.mostrarMensaje("3. Buscar por autor");
        vista.mostrarMensaje("4. Mostrar solo libros disponibles");
        int opcionBusqueda = vista.leerOpcion();

        BibliotecaRepositorio repo = (BibliotecaRepositorio) repositorioLibros;

        switch (opcionBusqueda) {
            case 1:
                int id = vista.leerNumero("🔢 Ingrese el ID: ");
                Libro libro = repositorioLibros.obtenerPorId(id);
                if (libro != null) {
                    vista.mostrarMensaje("📖 Libro encontrado:");
                    vista.mostrarMensaje(libro.toString());
                } else {
                    vista.mostrarError("Libro no encontrado.");
                }
                break;
            case 2:
                String titulo = vista.leerTexto("📖 Ingrese el título: ");
                List<Libro> librosPorTitulo = repo.buscarPorTitulo(titulo);
                vista.mostrarLibros(librosPorTitulo);
                break;
            case 3:
                String autor = vista.leerTexto("👤 Ingrese el autor: ");
                List<Libro> librosPorAutor = repo.buscarPorAutor(autor);
                vista.mostrarLibros(librosPorAutor);
                break;
            case 4:
                List<Libro> librosDisponibles = repo.obtenerLibrosDisponibles();
                vista.mostrarLibros(librosDisponibles);
                break;
            default:
                vista.mostrarError("Opción inválida.");
        }
    }

    @Override
    public void eliminarLibro() {
        int id = vista.leerNumero("🗑️ Ingrese el ID del libro a eliminar: ");
        Libro libro = repositorioLibros.obtenerPorId(id);

        if (libro == null) {
            vista.mostrarError("Libro no encontrado.");
            return;
        }

        // Verificar si tiene préstamos activos
        PrestamoRepositorio prestamosRepo = (PrestamoRepositorio) repositorioPrestamos;
        List<Prestamo> prestamosActivos = prestamosRepo.obtenerPorLibro(libro)
                .stream()
                .filter(p -> p.getEstado() == EstadoPrestamo.ACTIVO)
                .collect(Collectors.toList());

        if (!prestamosActivos.isEmpty()) {
            vista.mostrarError("No se puede eliminar el libro. Tiene " +
                             prestamosActivos.size() + " préstamo(s) activo(s).");
            return;
        }

        vista.mostrarMensaje("📖 Libro a eliminar: " + libro.getTitulo());
        String confirmacion = vista.leerTexto("⚠️ ¿Está seguro? (s/N): ");

        if (confirmacion.toLowerCase().equals("s")) {
            if (repositorioLibros.eliminar(id)) {
                vista.mostrarExito("Libro eliminado exitosamente.");
            } else {
                vista.mostrarError("Error al eliminar el libro.");
            }
        } else {
            vista.mostrarMensaje("Operación cancelada.");
        }
    }

    @Override
    public void agregarUsuario() {
        BibliotecaVista bibliotecaVista = (BibliotecaVista) vista;
        Usuario nuevoUsuario = bibliotecaVista.solicitarDatosUsuario();

        if (nuevoUsuario != null && repositorioUsuarios.crear(nuevoUsuario)) {
            vista.mostrarExito("Usuario agregado exitosamente con ID: " + nuevoUsuario.getId());
        } else {
            vista.mostrarError("Error al agregar el usuario. Verifique que el email no esté en uso.");
        }
    }

    @Override
    public void listarUsuarios() {
        List<Usuario> usuarios = repositorioUsuarios.obtenerTodos();
        vista.mostrarUsuarios(usuarios);
    }

    @Override
    public void buscarUsuario() {
        vista.mostrarMensaje("\n🔍 OPCIONES DE BÚSQUEDA:");
        vista.mostrarMensaje("1. Buscar por ID");
        vista.mostrarMensaje("2. Buscar por nombre");
        vista.mostrarMensaje("3. Buscar por email");
        vista.mostrarMensaje("4. Filtrar por tipo");
        int opcionBusqueda = vista.leerOpcion();

        UsuarioRepositorio repo = (UsuarioRepositorio) repositorioUsuarios;

        switch (opcionBusqueda) {
            case 1 -> {
                int id = vista.leerNumero("🔢 Ingrese el ID: ");
                Usuario usuario = repositorioUsuarios.obtenerPorId(id);
                if (usuario != null) {
                    vista.mostrarUsuarios(List.of(usuario));
                } else {
                    vista.mostrarError("Usuario no encontrado.");
                }
            }
            case 2 -> {
                String nombre = vista.leerTexto("👤 Ingrese el nombre: ");
                List<Usuario> usuariosPorNombre = repo.buscarPorNombre(nombre);
                vista.mostrarUsuarios(usuariosPorNombre);
            }
            case 3 -> {
                String email = vista.leerTexto("📧 Ingrese el email: ");
                Usuario usuarioPorEmail = repo.buscarPorEmail(email);
                if (usuarioPorEmail != null) {
                    vista.mostrarUsuarios(List.of(usuarioPorEmail));
                } else {
                    vista.mostrarError("Usuario no encontrado.");
                }
            }
            case 4 -> {
                vista.mostrarMensaje("Tipos de usuario:");
                vista.mostrarMensaje("1. Estudiante");
                vista.mostrarMensaje("2. Profesor");
                vista.mostrarMensaje("3. Administrador");
                int tipoOpcion = vista.leerNumero("Seleccione tipo: ");

                TipoUsuario tipo = switch (tipoOpcion) {
                    case 1 -> TipoUsuario.ESTUDIANTE;
                    case 2 -> TipoUsuario.PROFESOR;
                    case 3 -> TipoUsuario.ADMINISTRADOR;
                    default -> null;
                };

                if (tipo != null) {
                    List<Usuario> usuariosPorTipo = repo.obtenerPorTipo(tipo);
                    vista.mostrarUsuarios(usuariosPorTipo);
                } else {
                    vista.mostrarError("Opción inválida.");
                }
            }
            default -> vista.mostrarError("Opción inválida.");
        }
    }

    @Override
    public void eliminarUsuario() {
        int id = vista.leerNumero("🗑️ Ingrese el ID del usuario a eliminar: ");
        Usuario usuario = repositorioUsuarios.obtenerPorId(id);

        if (usuario == null) {
            vista.mostrarError("Usuario no encontrado.");
            return;
        }

        // Verificar si tiene préstamos activos
        if (usuario.getPrestamosActivos() > 0) {
            vista.mostrarError("No se puede eliminar el usuario. Tiene " +
                             usuario.getPrestamosActivos() + " préstamo(s) activo(s).");
            return;
        }

        vista.mostrarMensaje("👤 Usuario a eliminar: " + usuario.getNombre());
        String confirmacion = vista.leerTexto("⚠️ ¿Está seguro? (s/N): ");

        if (confirmacion.toLowerCase().equals("s")) {
            if (repositorioUsuarios.eliminar(id)) {
                vista.mostrarExito("Usuario eliminado exitosamente.");
            } else {
                vista.mostrarError("Error al eliminar el usuario.");
            }
        } else {
            vista.mostrarMensaje("Operación cancelada.");
        }
    }

    @Override
    public void realizarPrestamo() {
        // Seleccionar libro
        int idLibro = vista.leerNumero("📖 Ingrese el ID del libro a prestar: ");
        Libro libro = repositorioLibros.obtenerPorId(idLibro);

        if (libro == null) {
            vista.mostrarError("Libro no encontrado.");
            return;
        }

        if (!libro.estaDisponible()) {
            vista.mostrarError("El libro no está disponible para préstamo.");
            return;
        }

        // Seleccionar usuario
        int idUsuario = vista.leerNumero("👤 Ingrese el ID del usuario: ");
        Usuario usuario = repositorioUsuarios.obtenerPorId(idUsuario);

        if (usuario == null) {
            vista.mostrarError("Usuario no encontrado.");
            return;
        }

        if (!usuario.isActivo()) {
            vista.mostrarError("El usuario no está activo.");
            return;
        }

        if (!usuario.puedePrestar()) {
            vista.mostrarError("El usuario ha alcanzado su límite de préstamos (" +
                             usuario.getTipoUsuario().getLimitePrestamos() + ").");
            return;
        }

        // Crear préstamo
        Prestamo prestamo = new Prestamo(usuario, libro);

        // Mostrar resumen
        vista.mostrarMensaje("\n📋 RESUMEN DEL PRÉSTAMO:");
        vista.mostrarMensaje("📖 Libro: " + libro.getTitulo());
        vista.mostrarMensaje("👤 Usuario: " + usuario.getNombre());
        vista.mostrarMensaje("📅 Fecha límite: " + prestamo.getFechaDevolucionEsperada());

        String confirmacion = vista.leerTexto("✅ ¿Confirmar préstamo? (s/N): ");

        if (confirmacion.toLowerCase().equals("s")) {
            if (libro.prestar() && repositorioPrestamos.crear(prestamo) &&
                repositorioLibros.actualizar(libro)) {
                usuario.agregarPrestamo(prestamo);
                repositorioUsuarios.actualizar(usuario);
                vista.mostrarExito("Préstamo realizado exitosamente. ID: " + prestamo.getId());
            } else {
                vista.mostrarError("Error al realizar el préstamo.");
            }
        } else {
            vista.mostrarMensaje("Préstamo cancelado.");
        }
    }

    @Override
    public void devolverLibro() {
        int idPrestamo = vista.leerNumero("📥 Ingrese el ID del préstamo a devolver: ");
        Prestamo prestamo = repositorioPrestamos.obtenerPorId(idPrestamo);

        if (prestamo == null) {
            vista.mostrarError("Préstamo no encontrado.");
            return;
        }

        if (prestamo.getEstado() != EstadoPrestamo.ACTIVO) {
            vista.mostrarError("El préstamo ya fue devuelto.");
            return;
        }

        // Mostrar información del préstamo
        vista.mostrarMensaje("\n📋 INFORMACIÓN DEL PRÉSTAMO:");
        vista.mostrarMensaje("📖 Libro: " + prestamo.getLibro().getTitulo());
        vista.mostrarMensaje("👤 Usuario: " + prestamo.getUsuario().getNombre());
        vista.mostrarMensaje("📅 Fecha límite: " + prestamo.getFechaDevolucionEsperada());

        if (prestamo.estaVencido()) {
            vista.mostrarMensaje("⚠️ PRÉSTAMO VENCIDO - Días de retraso: " + prestamo.getDiasRetraso());
        }

        String confirmacion = vista.leerTexto("✅ ¿Confirmar devolución? (s/N): ");

        if (confirmacion.toLowerCase().equals("s")) {
            prestamo.devolver();
            prestamo.getLibro().devolver();

            if (repositorioPrestamos.actualizar(prestamo) &&
                repositorioLibros.actualizar(prestamo.getLibro())) {
                vista.mostrarExito("Libro devuelto exitosamente.");
                if (prestamo.getDiasRetraso() > 0) {
                    vista.mostrarMensaje("📝 Nota: El libro fue devuelto con " +
                                       prestamo.getDiasRetraso() + " día(s) de retraso.");
                }
            } else {
                vista.mostrarError("Error al procesar la devolución.");
            }
        } else {
            vista.mostrarMensaje("Devolución cancelada.");
        }
    }

    @Override
    public void listarPrestamosActivos() {
        PrestamoRepositorio repo = (PrestamoRepositorio) repositorioPrestamos;
        List<Prestamo> prestamosActivos = repo.obtenerPrestamosActivos();
        vista.mostrarPrestamos(prestamosActivos);
    }

    @Override
    public void mostrarPrestamosVencidos() {
        PrestamoRepositorio repo = (PrestamoRepositorio) repositorioPrestamos;
        List<Prestamo> prestamosVencidos = repo.obtenerPrestamosVencidos();

        if (prestamosVencidos.isEmpty()) {
            vista.mostrarExito("¡No hay préstamos vencidos!");
            return;
        }

        vista.mostrarMensaje("🔴 PRÉSTAMOS VENCIDOS:");
        vista.mostrarPrestamos(prestamosVencidos);
    }

    @Override
    public void mostrarEstadisticas() {
        // Obtener datos
        List<Libro> todosLosLibros = repositorioLibros.obtenerTodos();
        List<Usuario> todosLosUsuarios = repositorioUsuarios.obtenerTodos();
        PrestamoRepositorio prestamoRepo = (PrestamoRepositorio) repositorioPrestamos;

        int totalLibros = todosLosLibros.size();
        int librosDisponibles = (int) todosLosLibros.stream()
                                      .filter(Libro::estaDisponible)
                                      .count();
        int totalUsuarios = todosLosUsuarios.size();
        int prestamosActivos = prestamoRepo.obtenerPrestamosActivos().size();
        int prestamosVencidos = prestamoRepo.obtenerPrestamosVencidos().size();

        BibliotecaVista bibliotecaVista = (BibliotecaVista) vista;
        bibliotecaVista.mostrarEstadisticas(totalLibros, librosDisponibles,
                                          totalUsuarios, prestamosActivos, prestamosVencidos);
    }
}