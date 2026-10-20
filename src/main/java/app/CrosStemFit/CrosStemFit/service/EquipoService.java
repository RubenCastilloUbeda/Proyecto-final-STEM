package app.CrosStemFit.CrosStemFit.service;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.PathVariable;

import app.CrosStemFit.CrosStemFit.entity.Clasificacion;
import app.CrosStemFit.CrosStemFit.entity.Equipo;
import app.CrosStemFit.CrosStemFit.entity.Resultado;
import app.CrosStemFit.CrosStemFit.entity.Usuario;

public interface EquipoService {
    List<Equipo> findAll();
    Optional<Equipo> findById(Long id);
    Optional<Equipo> findByNombreEquipo(String nombreEquipo);
    void deleteById(Long id);
    Equipo save(Equipo equipo);
    Equipo actualizarEquipo(Long id, Equipo equipoActualizado);
    List<Usuario> mostrarMiembrosEquipo(Long id);
    List<Resultado> mostrarResultadosEquipo(@PathVariable Long id);
    Equipo agregarMiembro(Long idEquipo, Long idUsuario);
    Equipo eliminarMiembro(Long idEquipo, Long idUsuario);
    List<Clasificacion> mostrarClasificacionesEquipo(Long idEquipo);
}
