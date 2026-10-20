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

import app.CrosStemFit.CrosStemFit.entity.Clasificacion;
import app.CrosStemFit.CrosStemFit.entity.Competicion;
import app.CrosStemFit.CrosStemFit.entity.Equipo;
import app.CrosStemFit.CrosStemFit.entity.Evento;
import app.CrosStemFit.CrosStemFit.service.CompeticionService;

/**
 * Controlador REST encargado de gestionar las operaciones
 * relacionadas con las competiciones.
 * <p>
 * Proporciona endpoints para realizar operaciones CRUD
 * sobre las competiciones, así como gestionar sus
 * clasificaciones, eventos y equipos registrados.
 * </p>
 *
 * <h2>Endpoints disponibles</h2>
 * <ul>
 *   <li><b>GET /api/v1/competiciones</b>:
 *       obtiene todas las competiciones.</li>
 *   <li><b>GET /api/v1/competiciones/{id}</b>:
 *       obtiene una competición por su identificador.</li>
 *   <li><b>GET /api/v1/competiciones/nombre/{nombreCompeticion}</b>:
 *       obtiene una competición por su nombre.</li>
 *   <li><b>POST /api/v1/competiciones</b>:
 *       crea una nueva competición.</li>
 *   <li><b>PUT /api/v1/competiciones/{id}</b>:
 *       actualiza una competición existente.</li>
 *   <li><b>DELETE /api/v1/competiciones/{id}</b>:
 *       elimina una competición.</li>
 *   <li><b>GET /api/v1/competiciones/{id}/clasificaciones</b>:
 *       obtiene las clasificaciones de una competición.</li>
 *   <li><b>GET /api/v1/competiciones/{id}/eventos</b>:
 *       obtiene los eventos de una competición.</li>
 *   <li><b>GET /api/v1/competiciones/{id}/equiposRegistrados</b>:
 *       obtiene los equipos registrados en una competición.</li>
 *   <li><b>POST /api/v1/competiciones/{id}/equiposRegistrados/{idEquipo}</b>:
 *       registra un equipo en una competición.</li>
 *   <li><b>POST /api/v1/competiciones/{id}/eventos/{idEvento}</b>:
 *       registra un evento en una competición.</li>
 *   <li><b>DELETE /api/v1/competiciones/{id}/equiposRegistrados/{idEquipo}</b>:
 *       elimina un equipo registrado.</li>
 *   <li><b>DELETE /api/v1/competiciones/{id}/eventos/{idEvento}</b>:
 *       elimina un evento registrado.</li>
 * </ul>
 */
@RestController
@RequestMapping("/api/v1/competiciones")
public class CompeticionController {

    /**
     * Servicio encargado de gestionar la lógica
     * de negocio de las competiciones.
     */
    @Autowired
    private CompeticionService competicionService;

    /**
     * Obtiene todas las competiciones registradas.
     *
     * @return lista con todas las competiciones almacenadas
     */
    @GetMapping
    public List<Competicion> mostrarTodasCompeticiones() {
        return competicionService.findAll();
    }

    /**
     * Obtiene una competición a partir de su identificador.
     *
     * @param id identificador de la competición
     * @return la competición encontrada
     * @throws RuntimeException si la competición no existe
     */
    @GetMapping("/{id}")
    public Competicion getCompeticionById (@PathVariable Long id){
        return competicionService.findById(id)
                .orElseThrow(() -> new RuntimeException("Competicion no encontrada"));
    }

    /**
     * Obtiene una competición a partir de su nombre.
     *
     * @param nombreCompeticion nombre de la competición
     * @return la competición encontrada
     * @throws RuntimeException si la competición no existe
     */
    @GetMapping("/nombre/{nombreCompeticion}")
    public Competicion getCompeticionByNombre(@PathVariable String nombreCompeticion){
        return competicionService.findByNombre(nombreCompeticion)
                .orElseThrow(() -> new RuntimeException("Competicion no encontrada"));
    }

    /**
     * Crea una nueva competición.
     *
     * @param competicion objeto con los datos
     *                    de la competición a crear
     * @return la competición creada y almacenada
     */
    @PostMapping
    public Competicion crearCompeticion(@RequestBody Competicion competicion){
        return competicionService.crearCompeticion(competicion);
    }

    /**
     * Elimina una competición a partir de su identificador.
     *
     * @param id identificador de la competición a eliminar
     */
    @DeleteMapping("/{id}")
    public void deleteCompeticionById(@PathVariable Long id){
        competicionService.deleteById(id);
    }

    /**
     * Obtiene la clasificación de una competición.
     *
     * @param id identificador de la competición
     * @return lista de clasificaciones asociadas
     *         a la competición
     * @throws RuntimeException si la competición no existe
     */
    @GetMapping("/{id}/clasificaciones")
    public List<Clasificacion> verClasificacion(@PathVariable Long id){
        return competicionService.verClasificacion(id);
    }

    /**
     * Obtiene los eventos asociados a una competición.
     *
     * @param id identificador de la competición
     * @return lista de eventos asociados a la competición
     * @throws RuntimeException si la competición no existe
     */
    @GetMapping("/{id}/eventos")
    public List<Evento> verEventos(@PathVariable Long id){
        return competicionService.verEventos(id);
    }

    /**
     * Obtiene los equipos registrados en una competición.
     *
     * @param id identificador de la competición
     * @return lista de equipos registrados
     * @throws RuntimeException si la competición no existe
     */
    @GetMapping("/{id}/equiposRegistrados")
    public List<Equipo> verEquipos(@PathVariable Long id){
        return competicionService.verEquiposApuntados(id);
    }

    /**
     * Modifica una competición existente.
     *
     * @param id identificador de la competición a modificar
     * @param competicion objeto con los nuevos datos
     *                    de la competición
     * @return la competición actualizada
     * @throws RuntimeException si la competición no existe
     */
    @PutMapping("/{id}")
    public Competicion modificarCompeticion(@PathVariable Long id, @RequestBody Competicion competicion) {
        return competicionService.modificarCompeticion(id, competicion);
    }

    /**
     * Registra un equipo en una competición.
     *
     * @param id identificador de la competición
     * @param idEquipo identificador del equipo
     * @return la competición actualizada
     * @throws RuntimeException si la competición
     *                          o el equipo no existen
     */
    @PostMapping("/{id}/equiposRegistrados/{idEquipo}")
    public Competicion registrarEquipo(@PathVariable Long id, @PathVariable Long idEquipo){
        return competicionService.registrarEquipo(id, idEquipo);
    }

    /**
     * Registra un evento en una competición.
     *
     * @param id identificador de la competición
     * @param idEvento identificador del evento
     * @return la competición actualizada
     * @throws RuntimeException si la competición
     *                          o el evento no existen
     */
    @PostMapping("/{id}/eventos/{idEvento}")
    public Competicion registrarEvento(@PathVariable Long id, @PathVariable Long idEvento){
        return competicionService.anadirEvento(id, idEvento);
    }

    /**
     * Elimina un equipo registrado de una competición.
     *
     * @param id identificador de la competición
     * @param idEquipo identificador del equipo
     */
    @DeleteMapping("/{id}/equiposRegistrados/{idEquipo}")
    public void eliminarEquipo(@PathVariable Long id,@PathVariable Long idEquipo){
        competicionService.eliminarEquipo(id, idEquipo);
    }

    /**
     * Elimina un evento registrado de una competición.
     *
     * @param id identificador de la competición
     * @param idEvento identificador del evento
     */
    @DeleteMapping("/{id}/eventos/{idEvento}")
    public void eliminarEvento(@PathVariable Long id,@PathVariable Long idEvento){
        competicionService.eliminarEvento(id, idEvento);
    }
}