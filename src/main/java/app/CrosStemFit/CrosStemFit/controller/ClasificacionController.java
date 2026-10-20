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
import app.CrosStemFit.CrosStemFit.service.ClasificacionService;

/**
 * Controlador REST encargado de gestionar las operaciones
 * relacionadas con las clasificaciones.
 * <p>
 * Proporciona endpoints para realizar operaciones CRUD
 * sobre las clasificaciones de la aplicación.
 * </p>
 *
 * <h2>Endpoints disponibles</h2>
 * <ul>
 *   <li><b>GET /api/v1/clasificaciones</b>:
 *       obtiene todas las clasificaciones.</li>
 *   <li><b>GET /api/v1/clasificaciones/{id}</b>:
 *       obtiene una clasificación por su identificador.</li>
 *   <li><b>POST /api/v1/clasificaciones</b>:
 *       crea una nueva clasificación.</li>
 *   <li><b>PUT /api/v1/clasificaciones/{id}</b>:
 *       actualiza una clasificación existente.</li>
 *   <li><b>DELETE /api/v1/clasificaciones/{id}</b>:
 *       elimina una clasificación por su identificador.</li>
 * </ul>
 */
@RestController
@RequestMapping("/api/v1/clasificaciones")
public class ClasificacionController {

    /**
     * Servicio encargado de gestionar la lógica
     * de negocio de las clasificaciones.
     */
    @Autowired
    private ClasificacionService clasificacionService;

    /**
     * Obtiene todas las clasificaciones registradas.
     *
     * @return lista con todas las clasificaciones almacenadas
     */
    @GetMapping
    public List<Clasificacion> mostrarTodosClasificaciones() {
        return clasificacionService.findAll();
    }

    /**
     * Obtiene una clasificación a partir de su identificador.
     *
     * @param id identificador de la clasificación
     * @return la clasificación encontrada
     * @throws RuntimeException si la clasificación no existe
     */
    @GetMapping("/{id}")
    public Clasificacion getClasificacionById(@PathVariable Long id){
        return clasificacionService.findById(id)
                .orElseThrow(() -> new RuntimeException("Clasificacion no encontrado"));
    }

    /**
     * Elimina una clasificación a partir de su identificador.
     *
     * @param id identificador de la clasificación a eliminar
     */
    @DeleteMapping("/{id}")
    public void deleteClasificacionById(@PathVariable Long id){
        clasificacionService.deleteById(id);
    }

    /**
     * Crea una nueva clasificación.
     *
     * @param clasificacion objeto con los datos
     *                      de la clasificación a crear
     * @return la clasificación creada y almacenada
     */
    @PostMapping
    public Clasificacion crearClasificacion(@RequestBody Clasificacion clasificacion){
        return clasificacionService.anadirClasificacion(clasificacion);
    }

    /**
     * Modifica una clasificación existente.
     *
     * @param id identificador de la clasificación a modificar
     * @param clasificacion objeto con los nuevos datos
     *                      de la clasificación
     * @return la clasificación actualizada
     * @throws RuntimeException si la clasificación no existe
     */
    @PutMapping("/{id}")
    public Clasificacion modificarClasificacion(@PathVariable Long id, @RequestBody Clasificacion clasificacion) {
        return clasificacionService.modificarClasificacion(id, clasificacion);
    }
}