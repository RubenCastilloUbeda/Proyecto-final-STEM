package app.CrosStemFit.CrosStemFit.service;

import java.util.List;
import java.util.Optional;

import app.CrosStemFit.CrosStemFit.entity.Evento;
import app.CrosStemFit.CrosStemFit.entity.Resultado;

public interface EventoService {
    List<Evento> findAll();
    Optional<Evento> findById(Long id);
    Optional<Evento> findByNombreEvento(String nombreEvento);
    void deleteById(Long id);
    Evento save(Evento evento);
    Evento modificarEvento(Long id, Evento eventoActualizado);
    List<Resultado> mostrarResultadosEventoOrdenados(Long id);
    Evento anadirResultado(Long id, Resultado resultado);
    Resultado modificarResultado(Long id, Resultado nuevoResultado);
}
