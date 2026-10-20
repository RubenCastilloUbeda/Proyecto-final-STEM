package app.CrosStemFit.CrosStemFit.service;

import java.util.List;
import java.util.Optional;

import app.CrosStemFit.CrosStemFit.entity.Clasificacion;

public interface ClasificacionService {
    List<Clasificacion> findAll();
    Optional<Clasificacion> findById(Long id);
    void deleteById(Long id);
    Clasificacion anadirClasificacion(Clasificacion clasificacion);
    Clasificacion modificarClasificacion(Long id, Clasificacion clasificacion);
}
