package modelo;

import java.time.LocalDate;

public class Prestamo {
    private int id;
    private Usuario usuario;
    private Libro libro;
    private LocalDate fechaPrestamo;
    private LocalDate fechaDevolucionEsperada;
    private LocalDate fechaDevolucionReal;
    private EstadoPrestamo estado;
    private String notas;
    
    // Constructores
    public Prestamo() {
        this.fechaPrestamo = LocalDate.now();
        this.estado = EstadoPrestamo.ACTIVO;
    }
    
    public Prestamo(Usuario usuario, Libro libro) {
        this();
        this.usuario = usuario;
        this.libro = libro;
        this.fechaDevolucionEsperada = LocalDate.now()
                .plusDays(usuario.getTipoUsuario().getDiasMaximoPrestamo());
    }
    
    // Getters y Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public Usuario getUsuario() {
        return usuario;
    }
    
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    public Libro getLibro() {
        return libro;
    }
    
    public void setLibro(Libro libro) {
        this.libro = libro;
    }
    
    public LocalDate getFechaPrestamo() {
        return fechaPrestamo;
    }
    
    public void setFechaPrestamo(LocalDate fechaPrestamo) {
        this.fechaPrestamo = fechaPrestamo;
    }
    
    public LocalDate getFechaDevolucionEsperada() {
        return fechaDevolucionEsperada;
    }
    
    public void setFechaDevolucionEsperada(LocalDate fechaDevolucionEsperada) {
        this.fechaDevolucionEsperada = fechaDevolucionEsperada;
    }
    
    public LocalDate getFechaDevolucionReal() {
        return fechaDevolucionReal;
    }
    
    public void setFechaDevolucionReal(LocalDate fechaDevolucionReal) {
        this.fechaDevolucionReal = fechaDevolucionReal;
    }
    
    public EstadoPrestamo getEstado() {
        return estado;
    }
    
    public void setEstado(EstadoPrestamo estado) {
        this.estado = estado;
    }
    
    public String getNotas() {
        return notas;
    }
    
    public void setNotas(String notas) {
        this.notas = notas;
    }
    
    // Métodos de negocio
    public boolean estaVencido() {
        return LocalDate.now().isAfter(fechaDevolucionEsperada) && 
               estado == EstadoPrestamo.ACTIVO;
    }
    
    public long getDiasRetraso() {
        if (!estaVencido()) {
            return 0;
        }
        return LocalDate.now().toEpochDay() - fechaDevolucionEsperada.toEpochDay();
    }
    
    public void devolver() {
        this.fechaDevolucionReal = LocalDate.now();
        this.estado = EstadoPrestamo.DEVUELTO;
    }
    
    @Override
    public String toString() {
        return String.format("Préstamo ID: %d | Usuario: %s | Libro: %s | Estado: %s | Fecha Límite: %s", 
                           id, usuario.getNombre(), libro.getTitulo(), 
                           estado.getDescripcion(), fechaDevolucionEsperada);
    }
}