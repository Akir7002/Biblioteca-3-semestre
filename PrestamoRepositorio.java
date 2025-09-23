package repositorio;

import modelo.Prestamo;
import modelo.Usuario;
import modelo.Libro;
import modelo.EstadoPrestamo;
import java.util.*;

public class PrestamoRepositorio implements IRepositorio<Prestamo> {
    private List<Prestamo> prestamos;
    private int contadorId;
    
    public PrestamoRepositorio() {
        this.prestamos = new ArrayList<>();
        this.contadorId = 1;
    }
    
    @Override
    public boolean crear(Prestamo prestamo) {
        prestamo.setId(contadorId++);
        return prestamos.add(prestamo);
    }
    
    @Override
    public Prestamo obtenerPorId(int id) {
        return prestamos.stream()
                       .filter(prestamo -> prestamo.getId() == id)
                       .findFirst()
                       .orElse(null);
    }
    
    @Override
    public List<Prestamo> obtenerTodos() {
        return new ArrayList<>(prestamos);
    }
    
    @Override
    public boolean actualizar(Prestamo prestamo) {
        for (int i = 0; i < prestamos.size(); i++) {
            if (prestamos.get(i).getId() == prestamo.getId()) {
                prestamos.set(i, prestamo);
                return true;
            }
        }
        return false;
    }
    
    @Override
    public boolean eliminar(int id) {
        return prestamos.removeIf(prestamo -> prestamo.getId() == id);
    }
    
    // Métodos específicos
    public List<Prestamo> obtenerPorUsuario(Usuario usuario) {
        return prestamos.stream()
                       .filter(prestamo -> prestamo.getUsuario().getId() == usuario.getId())
                       .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }
    
    public List<Prestamo> obtenerPorLibro(Libro libro) {
        return prestamos.stream()
                       .filter(prestamo -> prestamo.getLibro().getId() == libro.getId())
                       .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }
    
    public List<Prestamo> obtenerPorEstado(EstadoPrestamo estado) {
        return prestamos.stream()
                       .filter(prestamo -> prestamo.getEstado() == estado)
                       .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }
    
    public List<Prestamo> obtenerPrestamosActivos() {
        return obtenerPorEstado(EstadoPrestamo.ACTIVO);
    }
    
    public List<Prestamo> obtenerPrestamosVencidos() {
        return prestamos.stream()
                       .filter(Prestamo::estaVencido)
                       .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }
    
    public Prestamo buscarPrestamoActivoPorLibroYUsuario(Libro libro, Usuario usuario) {
        return prestamos.stream()
                       .filter(prestamo -> prestamo.getLibro().getId() == libro.getId() &&
                                         prestamo.getUsuario().getId() == usuario.getId() &&
                                         prestamo.getEstado() == EstadoPrestamo.ACTIVO)
                       .findFirst()
                       .orElse(null);
    }
}
