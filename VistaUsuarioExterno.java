package vista;

import modelo.Libro;
import java.util.*;

public class VistaUsuarioExterno implements IVistaUsuarioExterno {
    private Scanner scanner;
    
    public VistaUsuarioExterno() {
        this.scanner = new Scanner(System.in);
    }
    
    @Override
    public void mostrarBienvenida() {
        limpiarPantalla();
        System.out.println("\n" + "═".repeat(70));
        System.out.println("🌟 BIENVENIDO AL CATÁLOGO PÚBLICO DE LA BIBLIOTECA 🌟");
        System.out.println("═".repeat(70));
        System.out.println("📚 Explora nuestro catálogo de libros disponibles");
        System.out.println("🔍 Busca por título, autor o navega por categorías");
        System.out.println("📖 Consulta disponibilidad en tiempo real");
        System.out.println("🏛️ Para realizar préstamos, acércate al mostrador");
        System.out.println("═".repeat(70));
        pausa();
    }
    
    @Override
    public void mostrarMenuPublico() {
        System.out.println("\n" + "═".repeat(50));
        System.out.println("📖 CATÁLOGO PÚBLICO - BIBLIOTECA");
        System.out.println("═".repeat(50));
        System.out.println("🔍 CONSULTAR CATÁLOGO:");
        System.out.println("  1. 📋 Ver catálogo completo");
        System.out.println("  2. 🔍 Buscar por título");
        System.out.println("  3. 👤 Buscar por autor");
        System.out.println("  4. ✅ Ver solo libros disponibles");
        System.out.println("");
        System.out.println("📊 INFORMACIÓN:");
        System.out.println("  5. 📈 Estadísticas de la biblioteca");
        System.out.println("  6. 📚 Los más populares");
        System.out.println("");
        System.out.println("ℹ️  AYUDA:");
        System.out.println("  7. ❓ ¿Cómo solicitar un préstamo?");
        System.out.println("  8. 📍 Información de contacto");
        System.out.println("");
        System.out.println("  0. 🚪 Salir del catálogo");
        System.out.println("═".repeat(50));
        System.out.print("👆 Seleccione una opción: ");
    }
    
    @Override
    public void mostrarCatalogo(List<Libro> libros) {
        if (libros.isEmpty()) {
            mostrarMensaje("📚 El catálogo está vacío en este momento.");
            return;
        }
        
        System.out.println("\n" + "═".repeat(90));
        System.out.println("📚 CATÁLOGO DE LIBROS - BIBLIOTECA PÚBLICA");
        System.out.println("═".repeat(90));
        System.out.printf("%-5s %-35s %-25s %-15s %-8s%n", 
                         "ID", "TÍTULO", "AUTOR", "DISPONIBILIDAD", "ESTADO");
        System.out.println("─".repeat(90));
        
        for (Libro libro : libros) {
            String disponibilidad = libro.getCantidadDisponible() + "/" + libro.getCantidadTotal();
            String estado = libro.estaDisponible() ? "✅ DISP" : "❌ AGOTADO";
            String colorEstado = libro.estaDisponible() ? "🟢" : "🔴";
            
            System.out.printf("%-5d %-35s %-25s %-15s %s %-6s%n", 
                            libro.getId(),
                            truncarTexto(libro.getTitulo(), 33),
                            truncarTexto(libro.getAutor(), 23),
                            disponibilidad,
                            colorEstado,
                            estado.replace("✅ ", "").replace("❌ ", ""));
        }
        
        System.out.println("═".repeat(90));
        System.out.println("📊 Total de libros en catálogo: " + libros.size());
        long disponibles = libros.stream().filter(Libro::estaDisponible).count();
        System.out.println("✅ Libros disponibles: " + disponibles);
        System.out.println("❌ Libros agotados: " + (libros.size() - disponibles));
        
        // Mostrar instrucciones para préstamos
        System.out.println("\n💡 PARA SOLICITAR UN PRÉSTAMO:");
        System.out.println("   • Acércate al mostrador de la biblioteca");
        System.out.println("   • Presenta tu documento de identidad");
        System.out.println("   • Menciona el ID del libro que deseas");
    }
    
    @Override
    public void mostrarDetalleLibro(Libro libro) {
        System.out.println("\n" + "═".repeat(60));
        System.out.println("📖 DETALLE DEL LIBRO");
        System.out.println("═".repeat(60));
        System.out.println("🆔 ID: " + libro.getId());
        System.out.println("📚 Título: " + libro.getTitulo());
        System.out.println("👤 Autor: " + libro.getAutor());
        System.out.println("📊 Ejemplares totales: " + libro.getCantidadTotal());
        System.out.println("✅ Disponibles ahora: " + libro.getCantidadDisponible());
        
        if (libro.estaDisponible()) {
            System.out.println("🟢 Estado: DISPONIBLE PARA PRÉSTAMO");
            System.out.println("💡 Puedes solicitarlo en el mostrador");
        } else {
            System.out.println("🔴 Estado: TEMPORALMENTE AGOTADO");
            System.out.println("⏰ Consulta disponibilidad más tarde");
        }
        
        System.out.println("═".repeat(60));
    }
    
    @Override
    public void mostrarResultadoBusqueda(List<Libro> libros, String termino) {
        if (libros.isEmpty()) {
            System.out.println("\n🔍 RESULTADO DE BÚSQUEDA");
            System.out.println("─".repeat(40));
            System.out.println("❌ No se encontraron libros con: \"" + termino + "\"");
            System.out.println("💡 Sugerencias:");
            System.out.println("   • Verifica la ortografía");
            System.out.println("   • Intenta con palabras más generales");
            System.out.println("   • Busca por autor en lugar de título");
            return;
        }
        
        System.out.println("\n🔍 RESULTADO DE BÚSQUEDA: \"" + termino + "\"");
        System.out.println("─".repeat(50));
        System.out.println("✅ Se encontraron " + libros.size() + " resultado(s):");
        System.out.println();
        
        mostrarCatalogo(libros);
    }
    
    @Override
    public void mostrarMensaje(String mensaje) {
        System.out.println("ℹ️  " + mensaje);
    }
    
    @Override
    public void mostrarDespedida() {
        limpiarPantalla();
        System.out.println("\n" + "═".repeat(60));
        System.out.println("🌟 ¡GRACIAS POR VISITAR NUESTRA BIBLIOTECA! 🌟");
        System.out.println("═".repeat(60));
        System.out.println("📚 Esperamos haberte ayudado a encontrar tu próxima lectura");
        System.out.println("🏛️ Te esperamos pronto para realizar tus préstamos");
        System.out.println("📞 ¿Dudas? Llama al: (01) 123-4567");
        System.out.println("🌐 Visita: www.biblioteca.gov.co");
        System.out.println("📧 Escríbenos: info@biblioteca.gov.co");
        System.out.println("═".repeat(60));
        System.out.println("🕐 Horarios de atención:");
        System.out.println("   • Lunes a Viernes: 8:00 AM - 8:00 PM");
        System.out.println("   • Sábados: 9:00 AM - 5:00 PM");
        System.out.println("   • Domingos: 10:00 AM - 4:00 PM");
        System.out.println("═".repeat(60));
        System.out.println("\n✨ ¡Hasta la próxima! ✨\n");
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
        scanner.nextLine(); // Limpiar buffer previo
        return scanner.nextLine().trim();
    }
    
    @Override
    public void pausa() {
        System.out.print("\n⏳ Presiona Enter para continuar...");
        try {
            scanner.nextLine();
        } catch (Exception e) {
            // Manejar excepción si es necesario
        }
    }
    
    // Métodos específicos adicionales
    public void mostrarAyudaPrestamos() {
        System.out.println("\n" + "═".repeat(70));
        System.out.println("❓ ¿CÓMO SOLICITAR UN PRÉSTAMO?");
        System.out.println("═".repeat(70));
        System.out.println("📝 PASOS PARA REALIZAR UN PRÉSTAMO:");
        System.out.println();
        System.out.println("1️⃣  CONSULTA EL CATÁLOGO");
        System.out.println("   • Usa este sistema para encontrar libros disponibles");
        System.out.println("   • Anota el ID del libro que te interesa");
        System.out.println();
        System.out.println("2️⃣  ACÉRCATE AL MOSTRADOR");
        System.out.println("   • Ve al área de préstamos en la biblioteca");
        System.out.println("   • Presenta tu documento de identidad");
        System.out.println();
        System.out.println("3️⃣  REGISTRATE (SI ES LA PRIMERA VEZ)");
        System.out.println("   • Completa el formulario de registro");
        System.out.println("   • Proporciona tus datos de contacto");
        System.out.println();
        System.out.println("4️⃣  SOLICITA EL PRÉSTAMO");
        System.out.println("   • Menciona el ID del libro");
        System.out.println("   • El bibliotecario procesará tu solicitud");
        System.out.println();
        System.out.println("⏰ PLAZOS DE DEVOLUCIÓN:");
        System.out.println("   • 📚 Estudiantes: 15 días (máx. 3 libros)");
        System.out.println("   • 👨‍🏫 Profesores: 30 días (máx. 5 libros)");
        System.out.println("   • 🎓 Personal: 60 días (máx. 10 libros)");
        System.out.println("═".repeat(70));
    }
    
    public void mostrarInformacionContacto() {
        System.out.println("\n" + "═".repeat(60));
        System.out.println("📍 INFORMACIÓN DE CONTACTO");
        System.out.println("═".repeat(60));
        System.out.println("🏛️  BIBLIOTECA PÚBLICA CENTRAL");
        System.out.println();
        System.out.println("📍 DIRECCIÓN:");
        System.out.println("   Calle 123 #45-67");
        System.out.println("   Bogotá D.C., Colombia");
        System.out.println("   Código Postal: 110111");
        System.out.println();
        System.out.println("📞 TELÉFONOS:");
        System.out.println("   • Principal: (01) 123-4567");
        System.out.println("   • Préstamos: (01) 123-4568");
        System.out.println("   • Renovaciones: (01) 123-4569");
        System.out.println();
        System.out.println("🌐 ONLINE:");
        System.out.println("   • Web: www.biblioteca.gov.co");
        System.out.println("   • Email: info@biblioteca.gov.co");
        System.out.println("   • Redes: @BibliotecaCentral");
        System.out.println();
        System.out.println("🚌 TRANSPORTE:");
        System.out.println("   • TransMilenio: Estación Biblioteca");
        System.out.println("   • Buses: Rutas 15, 23, 45, 67");
        System.out.println("   • Parqueadero gratuito disponible");
        System.out.println("═".repeat(60));
    }
    
    public void mostrarEstadisticasPublicas(int totalLibros, int librosDisponibles, 
                                          int totalUsuarios, int prestamosHoy) {
        System.out.println("\n" + "═".repeat(50));
        System.out.println("📈 ESTADÍSTICAS DE LA BIBLIOTECA");
        System.out.println("═".repeat(50));
        System.out.println("📚 COLECCIÓN:");
        System.out.println("   • Total de libros: " + String.format("%,d", totalLibros));
        System.out.println("   • Disponibles ahora: " + String.format("%,d", librosDisponibles));
        System.out.println("   • En préstamo: " + String.format("%,d", (totalLibros - librosDisponibles)));
        System.out.println();
        System.out.println("👥 COMUNIDAD:");
        System.out.println("   • Usuarios registrados: " + String.format("%,d", totalUsuarios));
        System.out.println("   • Préstamos hoy: " + prestamosHoy);
        System.out.println();
        System.out.println("📊 PORCENTAJE DE DISPONIBILIDAD:");
        double porcentaje = totalLibros > 0 ? ((double) librosDisponibles / totalLibros) * 100 : 0;
        String barraProgreso = generarBarraProgreso(porcentaje);
        System.out.println("   " + barraProgreso + String.format(" %.1f%%", porcentaje));
        System.out.println("═".repeat(50));
    }
    
    public void mostrarLibrosPopulares() {
        System.out.println("\n" + "═".repeat(60));
        System.out.println("🔥 LOS MÁS POPULARES DE ESTE MES");
        System.out.println("═".repeat(60));
        System.out.println("🥇 1. Don Quijote de la Mancha - Miguel de Cervantes");
        System.out.println("     ⭐ 45 préstamos este mes");
        System.out.println();
        System.out.println("🥈 2. Cien Años de Soledad - Gabriel García Márquez");
        System.out.println("     ⭐ 38 préstamos este mes");
        System.out.println();
        System.out.println("🥉 3. 1984 - George Orwell");
        System.out.println("     ⭐ 32 préstamos este mes");
        System.out.println();
        System.out.println("4️⃣  El Principito - Antoine de Saint-Exupéry");
        System.out.println("     ⭐ 28 préstamos este mes");
        System.out.println();
        System.out.println("5️⃣  Orgullo y Prejuicio - Jane Austen");
        System.out.println("     ⭐ 21 préstamos este mes");
        System.out.println("═".repeat(60));
        System.out.println("💡 ¿Te interesa alguno? ¡Consúltalo en nuestro catálogo!");
    }
    
    // Métodos auxiliares
    private void limpiarPantalla() {
        // Simular limpieza de pantalla
        for (int i = 0; i < 3; i++) {
            System.out.println();
        }
    }
    
    private String truncarTexto(String texto, int longitud) {
        if (texto == null) return "";
        if (texto.length() <= longitud) return texto;
        return texto.substring(0, longitud - 3) + "...";
    }
    
    private String generarBarraProgreso(double porcentaje) {
        int barrasCompletas = (int) (porcentaje / 10);
        StringBuilder barra = new StringBuilder("[");
        
        for (int i = 0; i < 10; i++) {
            if (i < barrasCompletas) {
                barra.append("█");
            } else {
                barra.append("░");
            }
        }
        barra.append("]");
        return barra.toString();
    }
}
