package modelo;

public class Libro extends RecursoBiblioteca {
    private String titulo;
    private String autor;
    private int cantidadDisponible;
    private int cantidadTotal;
    
    // Constructores
    public Libro() {}
    
    public Libro(String titulo, String autor, int cantidadDisponible) {
        this.titulo = titulo;
        this.autor = autor;
        this.cantidadDisponible = cantidadDisponible;
        this.cantidadTotal = cantidadDisponible;
    }
    
    // Getters y Setters
    public String getTitulo() {
        return titulo;
    }
    
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    
    public String getAutor() {
        return autor;
    }
    
    public void setAutor(String autor) {
        this.autor = autor;
    }
    
    public int getCantidadDisponible() {
        return cantidadDisponible;
    }
    
    public void setCantidadDisponible(int cantidadDisponible) {
        this.cantidadDisponible = cantidadDisponible;
    }
    
    public int getCantidadTotal() {
        return cantidadTotal;
    }
    
    public void setCantidadTotal(int cantidadTotal) {
        this.cantidadTotal = cantidadTotal;
    }
    
    // Métodos de negocio
    public boolean prestar() {
        if (cantidadDisponible > 0) {
            cantidadDisponible--;
            return true;
        }
        return false;
    }
    
    public void devolver() {
        if (cantidadDisponible < cantidadTotal) {
            cantidadDisponible++;
        }
    }
    
    public boolean estaDisponible() {
        return cantidadDisponible > 0;
    }
    
    @Override
    public String toString() {
        return String.format("ID: %d | Título: %s | Autor: %s | Disponibles: %d/%d", 
                           getId(), titulo, autor, cantidadDisponible, cantidadTotal);
    }
}