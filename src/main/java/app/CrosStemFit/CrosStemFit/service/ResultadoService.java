package app.CrosStemFit.CrosStemFit.service;

import java.util.List;
import java.util.Optional;

import app.CrosStemFit.CrosStemFit.entity.Resultado;

public interface ResultadoService {
    List<Resultado> findAll();
    Optional<Resultado> findById(Long id);
    void deleteById(Long id);
    Resultado modificarPuntuacion(Long id, int nuevaPuntuacion);
}
