package app.CrosStemFit.CrosStemFit.service;

import java.util.List;
import java.util.Optional;

import app.CrosStemFit.CrosStemFit.entity.Clasificacion;
import app.CrosStemFit.CrosStemFit.entity.Competicion;
import app.CrosStemFit.CrosStemFit.entity.Equipo;
import app.CrosStemFit.CrosStemFit.entity.Evento;

public interface CompeticionService {
    List<Competicion> findAll();
    Optional<Competicion> findByNombre(String nombre);
    Optional<Competicion> findById (Long id);
    Competicion save(Competicion competicion);
    void deleteById(Long id);
    Competicion crearCompeticion(Competicion nuevaCompeticion);
    Competicion modificarCompeticion(Long id, Competicion competicion);
    List<Clasificacion> verClasificacion(Long id);
    List<Evento> verEventos(Long id);
    List<Equipo> verEquiposApuntados(Long id);
    Competicion registrarEquipo(Long id, Long idEquipo);
    void eliminarEquipo(Long id, Long idEquipo);
    void eliminarEvento(Long id, Long idEvento);
    Competicion anadirEvento(Long id, Long idEvento);
}
