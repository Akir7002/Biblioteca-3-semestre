package vista;
import modelo.*;
import java.util.*;

public class BibliotecaVista implements IVista {
    private Scanner scanner;
    
    public BibliotecaVista() {
        this.scanner = new Scanner(System.in);
    }
    
    @Override
    public void mostrarMenu() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("🏛️  SISTEMA DE GESTIÓN DE BIBLIOTECA  🏛️");
        System.out.println("=".repeat(50));
        System.out.println("📚 GESTIÓN DE LIBROS:");
        System.out.println("  1. Agregar libro");
        System.out.println("  2. Listar todos los libros");
        System.out.println("  3. Buscar libro");
        System.out.println("  4. Eliminar libro");
        System.out.println("");
        System.out.println("👥 GESTIÓN DE USUARIOS:");
        System.out.println("  5. Agregar usuario");
        System.out.println("  6. Listar todos los usuarios");
        System.out.println("  7. Buscar usuario");
        System.out.println("  8. Eliminar usuario");
        System.out.println("");
        System.out.println("📤 GESTIÓN DE PRÉSTAMOS:");
        System.out.println("  9. Realizar préstamo");
        System.out.println(" 10. Devolver libro");
        System.out.println(" 11. Listar préstamos activos");
        System.out.println(" 12. Ver préstamos vencidos");
        System.out.println("");
        System.out.println(" 13. 📊 Mostrar estadísticas");
        System.out.println("  0. 🚪 Salir");
        System.out.println("=".repeat(50));
        System.out.print("Seleccione una opción: ");
    }
    
    @Override
    public void mostrarLibros(List<Libro> libros) {
        if (libros.isEmpty()) {
            mostrarMensaje("📚 No hay libros registrados.");
            return;
        }
        
        System.out.println("\n" + "=".repeat(80));
        System.out.println("📚 LISTA DE LIBROS");
        System.out.println("=".repeat(80));
        System.out.printf("%-5s %-30s %-25s %-15s%n", "ID", "TÍTULO", "AUTOR", "DISPONIBLES");
        System.out.println("-".repeat(80));
        
        for (Libro libro : libros) {
            String estado = libro.estaDisponible() ? "✅ " + libro.getCantidadDisponible() + "/" + libro.getCantidadTotal() 
                                                   : "❌ 0/" + libro.getCantidadTotal();
            System.out.printf("%-5d %-30s %-25s %-15s%n", 
                            libro.getId(), 
                            truncar(libro.getTitulo(), 28),
                            truncar(libro.getAutor(), 23),
                            estado);
        }
        System.out.println("=".repeat(80));
    }
    
    @Override
    public void mostrarUsuarios(List<Usuario> usuarios) {
        if (usuarios.isEmpty()) {
            mostrarMensaje("👥 No hay usuarios registrados.");
            return;
        }
        
        System.out.println("\n" + "=".repeat(90));
        System.out.println("👥 LISTA DE USUARIOS");
        System.out.println("=".repeat(90));
        System.out.printf("%-5s %-25s %-30s %-15s %-10s%n", "ID", "NOMBRE", "EMAIL", "TIPO", "PRÉSTAMOS");
        System.out.println("-".repeat(90));
        
        for (Usuario usuario : usuarios) {
            String prestamos = usuario.getPrestamosActivos() + "/" + usuario.getTipoUsuario().getLimitePrestamos();
            String estado = usuario.isActivo() ? "✅" : "❌";
            
            System.out.printf("%-5d %-25s %-30s %-15s %-10s %s%n", 
                            usuario.getId(),
                            truncar(usuario.getNombre(), 23),
                            truncar(usuario.getEmail(), 28),
                            usuario.getTipoUsuario().getDescripcion(),
                            prestamos,
                            estado);
        }
        System.out.println("=".repeat(90));
    }
    
    @Override
    public void mostrarPrestamos(List<Prestamo> prestamos) {
        if (prestamos.isEmpty()) {
            mostrarMensaje("📤 No hay préstamos registrados.");
            return;
        }
        
        System.out.println("\n" + "=".repeat(100));
        System.out.println("📤 LISTA DE PRÉSTAMOS");
        System.out.println("=".repeat(100));
        System.out.printf("%-5s %-20s %-25s %-15s %-12s %-10s%n", 
                         "ID", "USUARIO", "LIBRO", "FECHA LÍMITE", "ESTADO", "DÍAS");
        System.out.println("-".repeat(100));
        
        for (Prestamo prestamo : prestamos) {
            String estado = "";
            String dias = "";
            
            if (prestamo.getEstado() == EstadoPrestamo.ACTIVO) {
                if (prestamo.estaVencido()) {
                    estado = "🔴 VENCIDO";
                    dias = "+" + prestamo.getDiasRetraso();
                } else {
                    estado = "🟢 ACTIVO";
                    dias = "-";
                }
            } else {
                estado = "✅ DEVUELTO";
                dias = "-";
            }
            
            System.out.printf("%-5d %-20s %-25s %-15s %-12s %-10s%n",
                            prestamo.getId(),
                            truncar(prestamo.getUsuario().getNombre(), 18),
                            truncar(prestamo.getLibro().getTitulo(), 23),
                            prestamo.getFechaDevolucionEsperada(),
                            estado,
                            dias);
        }
        System.out.println("=".repeat(100));
    }
    
    @Override
    public void mostrarMensaje(String mensaje) {
        System.out.println("ℹ️  " + mensaje);
    }
    
    @Override
    public void mostrarError(String error) {
        System.out.println("❌ ERROR: " + error);
    }
    
    @Override
    public void mostrarExito(String mensaje) {
        System.out.println("✅ ÉXITO: " + mensaje);
    }
    
    @Override
    public int leerOpcion() {
        try {
            return scanner.nextInt();
        } catch (Exception e) {
            scanner.nextLine(); // Limpiar buffer
            return -1;
        }
    }
    
    @Override
    public String leerTexto(String prompt) {
        System.out.print(prompt);
        scanner.nextLine(); // Limpiar buffer
        return scanner.nextLine().trim(); // Eliminar espacios en blanco adicionales
    }
    
    @Override
    public int leerNumero(String prompt) {
        System.out.print(prompt);
        try {
            return scanner.nextInt();
        } catch (Exception e) {
            scanner.nextLine(); // Limpiar buffer
            return -1;
        }
    }
    
    @Override
    public void limpiarPantalla() {
        // Simular limpieza de pantalla
        for (int i = 0; i < 50; i++) {
            System.out.println();
        }
    }
    
    @Override
    public void pausa() {
        System.out.print("\nPresione Enter para continuar...");
        scanner.nextLine();
        scanner.nextLine();
    }
    
    // Métodos específicos
    public Libro solicitarDatosLibro() {
        System.out.println("\n📝 AGREGAR NUEVO LIBRO");
        System.out.println("-".repeat(30));
        
        String titulo = leerTexto("📖 Título: ");
        String autor = leerTexto("👤 Autor: ");
        int cantidad = leerNumero("📊 Cantidad disponible: ");
        
        if (titulo.isEmpty()) {
            mostrarError("El título no puede estar vacío.");
            return null;
        }

        if (autor.isEmpty()) {
            mostrarError("El autor no puede estar vacío.");
            return null;
        }

        if (cantidad <= 0) {
            mostrarError("La cantidad debe ser mayor a 0.");
            return null;
        }
        
        return new Libro(titulo, autor, cantidad);
    }
    
    public Usuario solicitarDatosUsuario() {
        System.out.println("\n👤 AGREGAR NUEVO USUARIO");
        System.out.println("-".repeat(30));
        
        String nombre = leerTexto("👤 Nombre completo: ");
        String email = leerTexto("📧 Email: ");
        String telefono = leerTexto("📱 Teléfono (opcional): ");
        
        System.out.println("\n🎯 Tipo de usuario:");
        System.out.println("1. Estudiante (máx. 3 préstamos, 15 días)");
        System.out.println("2. Profesor (máx. 5 préstamos, 30 días)");
        System.out.println("3. Administrador (máx. 10 préstamos, 60 días)");
        int tipoOpcion = leerNumero("Seleccione tipo: ");
        
        TipoUsuario tipo;
        switch (tipoOpcion) {
            case 1: tipo = TipoUsuario.ESTUDIANTE; break;
            case 2: tipo = TipoUsuario.PROFESOR; break;
            case 3: tipo = TipoUsuario.ADMINISTRADOR; break;
            default:
                mostrarError("Tipo de usuario inválido.");
                return null;
        }
        
        if (nombre.isEmpty() || email.isEmpty()) {
            mostrarError("Nombre y email son obligatorios.");
            return null;
        }
        
        return new Usuario(nombre, email, telefono, tipo);
    }
    
    public void mostrarEstadisticas(int totalLibros, int librosDisponibles, 
                                  int totalUsuarios, int prestamosActivos, 
                                  int prestamosVencidos) {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("📊 ESTADÍSTICAS DEL SISTEMA");
        System.out.println("=".repeat(60));
        System.out.println("📚 LIBROS:");
        System.out.println("   • Total de libros: " + totalLibros);
        System.out.println("   • Libros disponibles: " + librosDisponibles);
        System.out.println("   • Libros prestados: " + (totalLibros - librosDisponibles));
        System.out.println();
        System.out.println("👥 USUARIOS:");
        System.out.println("   • Total de usuarios: " + totalUsuarios);
        System.out.println();
        System.out.println("📤 PRÉSTAMOS:");
        System.out.println("   • Préstamos activos: " + prestamosActivos);
        System.out.println("   • Préstamos vencidos: " + prestamosVencidos);
        System.out.println("=".repeat(60));
    }
    
    private String truncar(String texto, int longitud) {
        if (texto.length() <= longitud) {
            return texto;
        }
        return texto.substring(0, longitud - 3) + "...";
    }
}