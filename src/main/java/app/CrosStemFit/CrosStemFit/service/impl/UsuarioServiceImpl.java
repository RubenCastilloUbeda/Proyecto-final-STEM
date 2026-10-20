package app.CrosStemFit.CrosStemFit.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.CrosStemFit.CrosStemFit.entity.Equipo;
import app.CrosStemFit.CrosStemFit.entity.Usuario;
import app.CrosStemFit.CrosStemFit.repository.EquipoRepository;
import app.CrosStemFit.CrosStemFit.repository.UsuarioRepository;
import app.CrosStemFit.CrosStemFit.service.UsuarioService;

/**
 * Implementación de la interfaz {@link UsuarioService}.
 * <p>
 * Esta clase proporciona los métodos necesarios para gestionar
 * los usuarios de la aplicación, realizando operaciones CRUD
 * y administrando la relación entre usuarios y equipos.
 * </p>
 */
@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private EquipoRepository equipoRepository;

    /**
     * Obtiene todos los usuarios registrados
     * en la base de datos.
     *
     * @return lista con todos los usuarios almacenados
     */
    @Override
    public List<Usuario> mostrarTodosUsuarios() {
        return usuarioRepository.findAll();
    }

    /**
     * Busca un usuario a partir de su DNI.
     *
     * @param dni documento nacional de identidad del usuario
     * @return un {@link Optional} que contiene el usuario
     *         si existe, o vacío en caso contrario
     */
    @Override
    public Optional<Usuario> buscarUsuarioPorDni(String dni) {
        return usuarioRepository.findByDni(dni);
    }

    /**
     * Busca un usuario a partir de su identificador.
     *
     * @param id identificador del usuario
     * @return un {@link Optional} que contiene el usuario
     *         si existe, o vacío en caso contrario
     */
    @Override
    public Optional<Usuario> buscarUsuarioById(Long id){
        return usuarioRepository.findById(id);
    }

    /**
     * Guarda un usuario en la base de datos.
     *
     * @param usuario usuario que se desea almacenar
     * @return el usuario almacenado
     * @throws IllegalArgumentException si el usuario
     *                                  es menor de edad
     */
    @Override
    public Usuario save(Usuario usuario) {
        if (usuario.getEdad() < 18) {
            throw new IllegalArgumentException("Debe ser mayor de edad para registrarse");
        }

        return usuarioRepository.save(usuario);
    }

    /**
     * Elimina un usuario de la base de datos
     * y lo desvincula de los equipos a los que pertenece.
     *
     * @param id identificador del usuario a eliminar
     * @throws RuntimeException si el usuario no existe
     */
    @Override
    public void deleteByID(Long id){
        Usuario usuario = usuarioRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        for (Equipo equipo : usuario.getEquipos()) {
            equipo.getMiembrosEquipo().remove(usuario);
        }

        usuario.getEquipos().clear();
        usuarioRepository.delete(usuario);
    }

    /**
     * Crea un nuevo equipo y asigna al usuario indicado
     * como miembro inicial del mismo.
     *
     * @param nombreEquipo nombre del equipo
     * @param maxMiembros número máximo de miembros permitidos
     * @param usuarioId identificador del usuario creador
     * @return el equipo creado y almacenado
     * @throws RuntimeException si el usuario no existe
     */
    @Override
    public Equipo crearEquipo(String nombreEquipo, int maxMiembros, Long usuarioId) {
        Usuario creador = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Equipo equipo = new Equipo();
        equipo.setNombre(nombreEquipo);
        equipo.setMaxMiembros(maxMiembros);
        equipo.getMiembrosEquipo().add(creador);
        return equipoRepository.save(equipo);
    }   

    /**
     * Modifica los datos de un usuario existente.
     *
     * @param id identificador del usuario a modificar
     * @param usuarioActualizado objeto con los nuevos datos
     *                           del usuario
     * @return el usuario actualizado
     * @throws RuntimeException si el usuario no existe
     * @throws IllegalArgumentException si la edad del usuario
     *                                  es menor de 18 años
     */
    @Override
    public Usuario actualizarUsuario(Long id, Usuario usuarioActualizado) {
        Usuario usuarioExistente = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        usuarioExistente.setNombre(usuarioActualizado.getNombre());
        usuarioExistente.setApellidos(usuarioActualizado.getApellidos());
        usuarioExistente.setDni(usuarioActualizado.getDni());
        usuarioExistente.setEdad(usuarioActualizado.getEdad());
        usuarioExistente.setEmail(usuarioActualizado.getEmail());

        if (usuarioExistente.getEdad() < 18) {
            throw new IllegalArgumentException("Debe ser mayor de edad para registrarse");
        }
        return usuarioRepository.save(usuarioExistente);
    }

    /**
     * Obtiene los equipos a los que pertenece un usuario.
     *
     * @param id identificador del usuario
     * @return lista de equipos asociados al usuario
     * @throws RuntimeException si el usuario no existe
     */
    @Override
    public List<Equipo> mostrarEquiposUsuario(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return usuario.getEquipos();
    }
}