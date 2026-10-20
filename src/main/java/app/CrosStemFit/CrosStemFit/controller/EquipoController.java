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
import app.CrosStemFit.CrosStemFit.entity.Equipo;
import app.CrosStemFit.CrosStemFit.entity.Resultado;
import app.CrosStemFit.CrosStemFit.entity.Usuario;
import app.CrosStemFit.CrosStemFit.service.EquipoService;

/**
 * Controlador REST encargado de gestionar las operaciones
 * relacionadas con los equipos.
 * <p>
 * Proporciona endpoints para realizar operaciones CRUD sobre
 * los equipos, así como para administrar sus miembros,
 * resultados y clasificaciones asociadas.
 * </p>
 */
@RestController
@RequestMapping("/api/v1/equipos")
public class EquipoController {

    /**
     * Servicio encargado de la lógica de negocio
     * relacionada con los equipos.
     */
    @Autowired
    private EquipoService equipoService;

    /**
     * Obtiene todos los equipos registrados
     * en el sistema.
     *
     * @return lista con todos los equipos
     */
    @GetMapping
    public List<Equipo> mostrarTodosEquipos() {
        return equipoService.findAll();
    }
    
    /**
     * Obtiene un equipo a partir de su identificador.
     *
     * @param id identificador del equipo
     * @return el equipo encontrado
     * @throws RuntimeException si el equipo no existe
     */
    @GetMapping("/{id}")
    public Equipo getEquipoById(@PathVariable Long id){
        return equipoService.findById(id)
                .orElseThrow(() -> new RuntimeException("Equipo no encontrado"));
    }

    /**
     * Obtiene un equipo a partir de su nombre.
     *
     * @param nombreEquipo nombre del equipo
     * @return el equipo encontrado
     * @throws RuntimeException si el equipo no existe
     */
    @GetMapping("/nombre/{nombreEquipo}")
    public Equipo getEquipoByNombre(@PathVariable String nombreEquipo){
        return equipoService.findByNombreEquipo(nombreEquipo)
                .orElseThrow(() -> new RuntimeException("Equipo no encontrado"));
    }

    /**
     * Obtiene los miembros del equipo 
     * a un equipo.
     *
     * @param id identificador del equipo
     * @return lista de miembros del equipo
     * @throws RuntimeException si el equipo no existe
     */
    @GetMapping("/{id}/miembrosEquipo")
    public List<Usuario> mostrarMiembrosEquipo(@PathVariable Long id) {
        return equipoService.mostrarMiembrosEquipo(id);
    }

    /**
     * Obtiene los resultados asociados
     * a un equipo.
     *
     * @param id identificador del equipo
     * @return lista de resultados del equipo
     * @throws RuntimeException si el equipo no existe
     */
    @GetMapping("/{id}/resultados")
    public List<Resultado> mostrarResultadosEquipo(@PathVariable Long id) {
        return equipoService.mostrarResultadosEquipo(id);
    }

    /**
     * Obtiene las clasificaciones asociadas
     * a un equipo.
     *
     * @param id identificador del equipo
     * @return lista de clasificaciones del equipo
     * @throws RuntimeException si el equipo no existe
     */
    @GetMapping("/{id}/clasificaciones")
    public List<Clasificacion> mostrarClasificacionesEquipo(@PathVariable Long id) {
        return equipoService.mostrarClasificacionesEquipo(id);
    }   

    /**
     * Elimina un equipo a partir de su identificador.
     *
     * @param id identificador del equipo a eliminar
     */
    @DeleteMapping("/{id}")
    public void deleteEquipoById(@PathVariable Long id){
        equipoService.deleteById(id);
    }

    /**
     * Elimina un miembro de un equipo.
     *
     * @param idEquipo identificador del equipo
     * @param idUsuario identificador del usuario
     *                  que se desea eliminar
     * @return el equipo actualizado
     * @throws RuntimeException si el equipo o el usuario
     *                          no existen
     */
    @DeleteMapping("/{idEquipo}/miembrosEquipo/{idUsuario}")
    public Equipo eliminarMiembro(@PathVariable Long idEquipo, @PathVariable Long idUsuario){
        return equipoService.eliminarMiembro(idEquipo, idUsuario);
    }
    
    /**
     * Modifica los datos de un equipo existente.
     *
     * @param id identificador del equipo
     * @param equipoActualizado objeto con los nuevos
     *                          datos del equipo
     * @return el equipo actualizado
     * @throws RuntimeException si el equipo no existe
     */
    @PutMapping("/{id}")
    public Equipo actualizarEquipo(@PathVariable Long id, @RequestBody Equipo equipoActualizado){
        return equipoService.actualizarEquipo(id, equipoActualizado);
    }

    /**
     * Añade un usuario como miembro de un equipo.
     *
     * @param idEquipo identificador del equipo
     * @param idUsuario identificador del usuario
     *                  que se desea añadir
     * @return el equipo actualizado
     * @throws RuntimeException si el equipo o el usuario
     *                          no existen
     */
    @PostMapping("/{idEquipo}/miembros/{idUsuario}")
    public Equipo agregarMiembro(@PathVariable Long idEquipo,@PathVariable Long idUsuario) {
        return equipoService.agregarMiembro(idEquipo, idUsuario);
    }

    /**
     * Crea un nuevo equipo en el sistema.
     *
     * @param equipo objeto con los datos
     *               del nuevo equipo
     * @return el equipo creado y almacenado
     */
    @PostMapping
    public Equipo crearEquipo(@RequestBody Equipo equipo) {
        return equipoService.save(equipo);
    }
}