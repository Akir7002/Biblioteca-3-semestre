package modelo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Usuario extends RecursoBiblioteca {
    private String nombre;
    private String email;
    private String telefono;
    private TipoUsuario tipoUsuario;
    private LocalDate fechaRegistro;
    private List<Prestamo> historialPrestamos;
    private boolean activo;
    
    // Constructores
    public Usuario() {
        this.historialPrestamos = new ArrayList<>();
        this.fechaRegistro = LocalDate.now();
        this.activo = true;
    }
    
    public Usuario(String nombre, String email, String telefono, TipoUsuario tipoUsuario) {
        this();
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;
        this.tipoUsuario = tipoUsuario;
    }
    
    // Getters y Setters
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getTelefono() {
        return telefono;
    }
    
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    
    public TipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }
    
    public void setTipoUsuario(TipoUsuario tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }
    
    public LocalDate getFechaRegistro() {
        return fechaRegistro;
    }
    
    public void setFechaRegistro(LocalDate fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
    
    public List<Prestamo> getHistorialPrestamos() {
        return historialPrestamos;
    }
    
    public void setHistorialPrestamos(List<Prestamo> historialPrestamos) {
        this.historialPrestamos = historialPrestamos;
    }
    
    public boolean isActivo() {
        return activo;
    }
    
    public void setActivo(boolean activo) {
        this.activo = activo;
    }
    
    // Métodos de negocio
    public int getPrestamosActivos() {
        return (int) historialPrestamos.stream()
                .filter(prestamo -> prestamo.getEstado() == EstadoPrestamo.ACTIVO)
                .count();
    }
    
    public boolean puedePrestar() {
        return activo && getPrestamosActivos() < tipoUsuario.getLimitePrestamos();
    }
    
    public void agregarPrestamo(Prestamo prestamo) {
        historialPrestamos.add(prestamo);
    }
    
    @Override
    public String toString() {
        return String.format("ID: %d | Nombre: %s | Email: %s | Tipo: %s | Préstamos Activos: %d/%d", 
                           getId(), nombre, email, tipoUsuario.getDescripcion(), 
                           getPrestamosActivos(), tipoUsuario.getLimitePrestamos());
    }
}
