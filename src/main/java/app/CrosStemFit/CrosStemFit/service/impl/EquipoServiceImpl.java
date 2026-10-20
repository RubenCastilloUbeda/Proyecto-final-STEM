package app.CrosStemFit.CrosStemFit.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.CrosStemFit.CrosStemFit.entity.Clasificacion;
import app.CrosStemFit.CrosStemFit.entity.Equipo;
import app.CrosStemFit.CrosStemFit.entity.Resultado;
import app.CrosStemFit.CrosStemFit.entity.Usuario;
import app.CrosStemFit.CrosStemFit.repository.EquipoRepository;
import app.CrosStemFit.CrosStemFit.repository.UsuarioRepository;
import app.CrosStemFit.CrosStemFit.service.EquipoService;

/**
 * Implementación de la interfaz {@link EquipoService}.
 * <p>
 * Esta clase proporciona los métodos necesarios para gestionar
 * los equipos de la aplicación, realizando operaciones CRUD
 * y administrando los miembros, resultados y clasificaciones
 * asociados a cada equipo.
 * </p>
 */
@Service
public class EquipoServiceImpl implements EquipoService{
    
    @Autowired
    public EquipoRepository equipoRepository;
    @Autowired
    public UsuarioRepository usuarioRepository;

    /**
     * Obtiene todos los equipos registrados
     * en la base de datos.
     *
     * @return lista con todos los equipos almacenados
     */
    @Override
    public List<Equipo> findAll(){
        return equipoRepository.findAll();
    }

    /**
     * Busca un equipo a partir de su identificador.
     *
     * @param id identificador del equipo
     * @return un {@link Optional} que contiene el equipo
     *         si existe, o vacío en caso contrario
     */
    @Override
    public Optional<Equipo> findById(Long id){
        return equipoRepository.findById(id);
    }

    /**
     * Busca un equipo a partir de su nombre.
     *
     * @param nombreEquipo nombre del equipo
     * @return un {@link Optional} que contiene el equipo
     *         si existe, o vacío en caso contrario
     */
    @Override
    public Optional<Equipo> findByNombreEquipo(String nombreEquipo){
        return equipoRepository.findByNombre(nombreEquipo);
    }

    /**
     * Guarda un equipo en la base de datos.
     *
     * @param equipo equipo que se desea almacenar
     * @return el equipo almacenado
     */
    @Override
    public Equipo save(Equipo equipo){
        return equipoRepository.save(equipo);
    }

    /**
     * Modifica un equipo existente.
     *
     * @param id identificador del equipo a modificar
     * @param equipoActualizado objeto con los nuevos datos
     *                          del equipo
     * @return el equipo actualizado
     * @throws RuntimeException si el equipo no existe
     */
    @Override
    public Equipo actualizarEquipo(Long id, Equipo equipoActualizado) {
        Equipo equipo = equipoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Equipo no encontrado"));

        equipo.setNombre(equipoActualizado.getNombre());
        equipo.setMaxMiembros(equipoActualizado.getMaxMiembros());
        return equipoRepository.save(equipo);
    }

    /**
     * Elimina un equipo de la base de datos
     * a partir de su identificador.
     *
     * @param id identificador del equipo a eliminar
     */
    @Override
    public void deleteById(Long id){
        equipoRepository.deleteById(id);
    }

    /**
     * Obtiene los miembros pertenecientes a un equipo.
     *
     * @param idEquipo identificador del equipo
     * @return lista de miembros del equipo
     * @throws RuntimeException si el equipo no existe
     */
    @Override
    public List<Usuario> mostrarMiembrosEquipo(Long idEquipo){
        Equipo equipo = equipoRepository.findById(idEquipo)
        .orElseThrow(() -> new RuntimeException("Equipo no encontrado"));
        
        return equipo.getMiembrosEquipo();
    }

    /**
     * Obtiene los resultados asociados a un equipo.
     *
     * @param idEquipo identificador del equipo
     * @return lista de resultados del equipo
     * @throws RuntimeException si el equipo no existe
     */
    @Override
    public List<Resultado> mostrarResultadosEquipo(Long idEquipo){
        Equipo equipo = equipoRepository.findById(idEquipo)
        .orElseThrow(() -> new RuntimeException("Equipo no encontrado"));
        
        return equipo.getResultados();
    }

    /**
     * Elimina un miembro de un equipo.
     *
     * @param idEquipo identificador del equipo
     * @param idUsuario identificador del usuario
     *                  que se desea eliminar
     * @return el equipo actualizado
     * @throws RuntimeException si el equipo o el usuario
     *                          no existen, o si el usuario
     *                          no pertenece al equipo
     */
    @Override
    public Equipo eliminarMiembro(Long idEquipo, Long idUsuario){
        Equipo equipo = equipoRepository.findById(idEquipo)
        .orElseThrow(() -> new RuntimeException("Equipo no encontrado"));

        Usuario miembroEliminar = usuarioRepository.findById(idUsuario)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!equipo.getMiembrosEquipo().contains(miembroEliminar)) {
        throw new RuntimeException("El usuario no pertenece al equipo");
    }
        equipo.getMiembrosEquipo().remove(miembroEliminar);
        return equipoRepository.save(equipo);
    }

    /**
     * Añade un miembro a un equipo.
     *
     * @param idEquipo identificador del equipo
     * @param idUsuario identificador del usuario
     *                  que se desea añadir
     * @return el equipo actualizado
     * @throws RuntimeException si el equipo o el usuario
     *                          no existen, o si el equipo
     *                          ha alcanzado el número máximo
     *                          de miembros
     */
    @Override
    public Equipo agregarMiembro(Long idEquipo, Long idUsuario) {
        Equipo equipo = equipoRepository.findById(idEquipo)
                .orElseThrow(() -> new RuntimeException("Equipo no encontrado"));
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (equipo.getMiembrosEquipo().size() >= equipo.getMaxMiembros()) {
            throw new RuntimeException("El equipo ha alcanzado el número máximo de miembros");
        }
        if (equipo.getMiembrosEquipo() == null) {
            equipo.setMiembrosEquipo(new ArrayList<>());
        }
        if (!equipo.getMiembrosEquipo().contains(usuario)) {
            equipo.getMiembrosEquipo().add(usuario);
        }
        return equipoRepository.save(equipo);
    }

    /**
     * Obtiene las clasificaciones asociadas a un equipo.
     *
     * @param idEquipo identificador del equipo
     * @return lista de clasificaciones del equipo
     * @throws RuntimeException si el equipo no existe
     */
    @Override
    public List<Clasificacion> mostrarClasificacionesEquipo(Long idEquipo) {
        Equipo equipo = equipoRepository.findById(idEquipo)
                .orElseThrow(() -> new RuntimeException("Equipo no encontrado"));
        return equipo.getRegistroClasificaciones();
}

}