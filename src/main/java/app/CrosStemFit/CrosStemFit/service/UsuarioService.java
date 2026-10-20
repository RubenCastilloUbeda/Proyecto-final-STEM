package app.CrosStemFit.CrosStemFit.service;

import java.util.List;
import java.util.Optional;

import app.CrosStemFit.CrosStemFit.entity.Equipo;
import app.CrosStemFit.CrosStemFit.entity.Usuario;

public interface UsuarioService {
    List<Usuario> mostrarTodosUsuarios();
    Optional<Usuario> buscarUsuarioById(Long id);
    Optional<Usuario> buscarUsuarioPorDni(String dni);
    Usuario save(Usuario usuario);
    void deleteByID(Long id);
    Equipo crearEquipo(String nombreEquipo, int maxMiembros, Long usuarioId);
    Usuario actualizarUsuario(Long id, Usuario usuarioActualizado);
    List<Equipo> mostrarEquiposUsuario(Long id);
}
