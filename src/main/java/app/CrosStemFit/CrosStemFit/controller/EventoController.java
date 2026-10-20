package app.CrosStemFit.CrosStemFit.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.CrosStemFit.CrosStemFit.entity.Evento;
import app.CrosStemFit.CrosStemFit.entity.Resultado;
import app.CrosStemFit.CrosStemFit.service.EventoService;

/**
 * Controlador REST encargado de gestionar las operaciones
 * relacionadas con los eventos.
 * <p>
 * Proporciona endpoints para realizar operaciones CRUD
 * sobre los eventos, así como para gestionar los resultados
 * asociados a cada evento.
 * </p>
 *
 * <h2>Endpoints disponibles</h2>
 * <ul>
 *   <li><b>GET /api/v1/eventos</b>: obtiene todos los eventos.</li>
 *   <li><b>GET /api/v1/eventos/{id}</b>: obtiene un evento por su identificador.</li>
 *   <li><b>GET /api/v1/eventos/nombre/{nombre}</b>: obtiene un evento por su nombre.</li>
 *   <li><b>POST /api/v1/eventos</b>: crea un nuevo evento.</li>
 *   <li><b>PUT /api/v1/eventos/{id}</b>: actualiza un evento existente.</li>
 *   <li><b>DELETE /api/v1/eventos/{id}</b>: elimina un evento.</li>
 *   <li><b>GET /api/v1/eventos/{id}/resultados</b>: obtiene los resultados de un evento.</li>
 *   <li><b>POST /api/v1/eventos/{id}/resultados</b>: añade un resultado a un evento.</li>
 *   <li><b>PUT /api/v1/eventos/resultados/{id}</b>: modifica un resultado existente.</li>
 * </ul>
 */
@RestController
@RequestMapping("/api/v1/eventos")
public class EventoController {

    @Autowired
    private EventoService eventoService;

    /**
     * Endpoint para obtener todos los eventos.
     * @return Lista de todos los eventos.
     */
    @GetMapping
    public List<Evento> mostrarTodosEventos() {
        return eventoService.findAll();
    }
    
    /**
     * Obtiene un evento a partir de su identificador.
     *
     * @param id identificador del evento
     * @return el evento encontrado
     * @throws RuntimeException si el evento no existe
     */
    @GetMapping("/{id}")
    public Evento getEventoById(@PathVariable Long id){
        return eventoService.findById(id).orElseThrow(() -> new RuntimeException("Evento no encontrado"));
    }

    /**
     * Obtiene un evento a partir de su nombre.
     *
     * @param nombre nombre del evento
     * @return el evento encontrado
     * @throws RuntimeException si el evento no existe
     */
    @GetMapping("/nombre/{nombre}")
    public Evento getByNombre(@PathVariable String nombre) {
        return eventoService.findByNombreEvento(nombre).orElseThrow(() -> new RuntimeException("Evento no encontrado"));
    }

    /**
     * Crea un nuevo evento.
     *
     * @param evento objeto con los datos del evento a crear
     * @return el evento creado y almacenado
     */
    @PostMapping
    public Evento guardarEvento(@RequestBody Evento evento) {
        return eventoService.save(evento);
    }

    /**
     * Modifica un evento existente.
     *
     * @param id identificador del evento a modificar
     * @param evento objeto con los nuevos datos del evento
     * @return el evento actualizado
     * @throws RuntimeException si el evento no existe
     */
    @PutMapping("/{id}")
    public Evento modificarEvento(@PathVariable Long id, @RequestBody Evento evento) {
        return eventoService.modificarEvento(id, evento);
    }

    /**
     * Obtiene los resultados asociados a un evento.
     *
     * @param id identificador del evento
     * @return lista de resultados del evento ordenados por puntuación
     * @throws RuntimeException si el evento no existe
     */
    @GetMapping("/{id}/resultados")
    public List<Resultado> mostrarResultadosEventoOrdenados(@PathVariable Long id){
        return eventoService.mostrarResultadosEventoOrdenados(id);
    }

    /**
     * Añade un resultado a un evento.
     *
     * @param id identificador del evento
     * @param resultado objeto con los datos del resultado
     * @return el evento actualizado
     * @throws RuntimeException si el evento no existe
     */
    @PostMapping("/{id}/resultados")
    public Evento anadirResultado(@PathVariable Long id, @RequestBody Resultado resultado){
        return eventoService.anadirResultado(id, resultado);
    }

    /**
     * Modifica un resultado existente.
     *
     * @param id identificador del resultado
     * @param nuevoResultado objeto con los nuevos datos del resultado
     * @return el resultado actualizado
     * @throws RuntimeException si el resultado no existe
     */
    @PutMapping("/resultados/{id}")
    public Resultado modificarResultado(@PathVariable Long id, @RequestBody Resultado nuevoResultado){
        return eventoService.modificarResultado(id, nuevoResultado);
    }

    /**
     * Endpoint para eliminar un evento por su ID.
     * @param id ID del evento a eliminar.
     * @throws RuntimeException si el evento no existe.
     */
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id){
        eventoService.deleteById(id);
    }
}
