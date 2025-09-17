package repositorio;

import modelo.Libro;
import java.util.*;

public class BibliotecaRepositorio implements IRepositorio<Libro> {
    private List<Libro> libros;
    private int contadorId;
    
    public BibliotecaRepositorio() {
        this.libros = new ArrayList<>();
        this.contadorId = 1;
    }
    
    @Override
    public boolean crear(Libro libro) {
        libro.setId(contadorId++);
        return libros.add(libro);
    }
    
    @Override
    public Libro obtenerPorId(int id) {
        return libros.stream()
                    .filter(libro -> libro.getId() == id)
                    .findFirst()
                    .orElse(null);
    }
    
    @Override
    public List<Libro> obtenerTodos() {
        return new ArrayList<>(libros);
    }
    
    @Override
    public boolean actualizar(Libro libro) {
        for (int i = 0; i < libros.size(); i++) {
            if (libros.get(i).getId() == libro.getId()) {
                libros.set(i, libro);
                return true;
            }
        }
        return false;
    }
    
    @Override
    public boolean eliminar(int id) {
        return libros.removeIf(libro -> libro.getId() == id);
    }
    
    // Métodos específicos
    public List<Libro> buscarPorTitulo(String titulo) {
        return libros.stream()
                    .filter(libro -> libro.getTitulo().toLowerCase()
                                         .contains(titulo.toLowerCase()))
                    .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }
    
    public List<Libro> buscarPorAutor(String autor) {
        return libros.stream()
                    .filter(libro -> libro.getAutor().toLowerCase()
                                         .contains(autor.toLowerCase()))
                    .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }
}