package app.CrosStemFit.CrosStemFit.service.impl;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.CrosStemFit.CrosStemFit.entity.Evento;
import app.CrosStemFit.CrosStemFit.entity.Resultado;
import app.CrosStemFit.CrosStemFit.repository.EventoRepository;
import app.CrosStemFit.CrosStemFit.repository.ResultadoRepository;
import app.CrosStemFit.CrosStemFit.service.EventoService;

/**
 * Implementación de la interfaz {@link EventoService}.
 * <p>
 * Esta clase proporciona los métodos necesarios para gestionar
 * los eventos de la aplicación, realizando operaciones CRUD
 * y administrando los resultados asociados a cada evento.
 * </p>
 */
@Service
public class EventoServiceImpl implements EventoService {

    @Autowired
    public EventoRepository eventoRepository;
    @Autowired
    public ResultadoRepository resultadoRepository;

    /**
     * Obtiene todos los eventos registrados
     * en la base de datos.
     *
     * @return lista con todos los eventos almacenados
     */
    @Override
    public List<Evento> findAll(){
        return eventoRepository.findAll();
    }

    /**
     * Busca un evento a partir de su identificador.
     *
     * @param id identificador del evento
     * @return un {@link Optional} que contiene el evento
     *         si existe, o vacío en caso contrario
     */
    @Override
    public Optional<Evento> findById(Long id){
        return eventoRepository.findById(id);
    }

    /**
     * Busca un evento a partir de su nombre.
     *
     * @param nombreEvento nombre del evento
     * @return un {@link Optional} que contiene el evento
     *         si existe, o vacío en caso contrario
     */
    @Override
    public Optional<Evento> findByNombreEvento(String nombreEvento){
        return eventoRepository.findByNombre(nombreEvento);
    }

    /**
     * Guarda un evento en la base de datos.
     *
     * @param evento evento que se desea almacenar
     * @return el evento almacenado
     */
    @Override
    public Evento save(Evento evento){
        return eventoRepository.save(evento);
    }

    /**
     * Elimina un evento de la base de datos
     * a partir de su identificador.
     *
     * @param id identificador del evento a eliminar
     */
    @Override
    public void deleteById(Long id){
        eventoRepository.deleteById(id);
    }

    /**
     * Obtiene los resultados asociados a un evento,
     * ordenados de mayor a menor puntuación.
     *
     * @param id identificador del evento
     * @return lista de resultados ordenados por puntuación
     * @throws RuntimeException si el evento no existe
     */
    @Override
    public List<Resultado> mostrarResultadosEventoOrdenados(Long id){
        Evento evento = findById(id)
                .orElseThrow(() -> new RuntimeException("Evento no encontrado"));
        
        List<Resultado> resultados = evento.getResultados();
        resultados.sort(Comparator.comparing(Resultado::getPuntuacion).reversed());

        return resultados;
    }

    /**
     * Añade un resultado a un evento.
     *
     * @param id identificador del evento
     * @param resultado resultado que se desea añadir
     * @return el evento actualizado
     * @throws RuntimeException si el evento no existe
     */
    @Override
    public Evento anadirResultado(Long id, Resultado resultado) {
        Evento evento = findById(id)
                .orElseThrow(() -> new RuntimeException("Evento no encontrado"));

        resultado.setEvento(evento);
        evento.getResultados().add(resultado);
        return eventoRepository.save(evento);
    }

    /**
     * Modifica un resultado existente.
     *
     * @param id identificador del resultado
     * @param nuevoResultado objeto con los nuevos datos
     *                        del resultado
     * @return el resultado actualizado
     * @throws RuntimeException si el resultado no existe
     */
    @Override
    public Resultado modificarResultado(Long id, Resultado nuevoResultado){
        Resultado resultado = resultadoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Resultado no encontrado"));

        resultado.setPuntuacion(nuevoResultado.getPuntuacion());
        return resultadoRepository.save(resultado);
    }

    /**
     * Modifica un evento existente.
     *
     * @param id identificador del evento a modificar
     * @param eventoActualizado objeto con los nuevos datos
     *                          del evento
     * @return el evento actualizado
     * @throws RuntimeException si el evento no existe
     */
    @Override
    public Evento modificarEvento(Long id, Evento eventoActualizado){
        Evento eventoExistente = eventoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Evento no encontrado"));

        eventoExistente.setNombre(eventoActualizado.getNombre());
        eventoExistente.setDuracion(eventoActualizado.getDuracion());

        return eventoRepository.save(eventoExistente);
    }
}