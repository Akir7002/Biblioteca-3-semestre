package repositorio;

import modelo.Usuario;
import modelo.TipoUsuario;
import java.util.*;
import java.util.stream.Collectors;

public class UsuarioRepositorio implements IRepositorio<Usuario> {
    private final List<Usuario> usuarios;
    private int contadorId;

    public UsuarioRepositorio() {
        this.usuarios = new ArrayList<>();
        this.contadorId = 1;
    }

    @Override
    public boolean crear(Usuario usuario) {
        // Verificar email único
        if (existeEmail(usuario.getEmail())) {
            return false;
        }
        usuario.setId(contadorId++);
        return usuarios.add(usuario);
    }

    private boolean existeEmail(String email) {
        return usuarios.stream().anyMatch(usuario -> usuario.getEmail().equals(email));
    }

    @Override
    public Usuario obtenerPorId(int id) {
        return usuarios.stream()
                      .filter(usuario -> usuario.getId() == id)
                      .findFirst()
                      .orElse(null);
    }

    @Override
    public List<Usuario> obtenerTodos() {
        return new ArrayList<>(usuarios);
    }

    @Override
    public boolean actualizar(Usuario usuario) {
        for (int i = 0; i < usuarios.size(); i++) {
            if (usuarios.get(i).getId() == usuario.getId()) {
                usuarios.set(i, usuario);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean eliminar(int id) {
        return usuarios.removeIf(usuario -> usuario.getId() == id);
    }

    public List<Usuario> buscarPorNombre(String nombre) {
        return usuarios.stream()
                      .filter(usuario -> usuario.getNombre().equalsIgnoreCase(nombre))
                      .collect(Collectors.toList());
    }

    public Usuario buscarPorEmail(String email) {
        return usuarios.stream()
                      .filter(usuario -> usuario.getEmail().equalsIgnoreCase(email))
                      .findFirst()
                      .orElse(null);
    }

    public List<Usuario> obtenerPorTipo(TipoUsuario tipo) {
        return usuarios.stream()
                      .filter(usuario -> usuario.getTipoUsuario() == tipo)
                      .collect(Collectors.toList());
    }
}