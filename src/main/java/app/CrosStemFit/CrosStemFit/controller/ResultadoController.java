package app.CrosStemFit.CrosStemFit.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import app.CrosStemFit.CrosStemFit.entity.Resultado;
import app.CrosStemFit.CrosStemFit.service.ResultadoService;

/**
 * Controlador REST encargado de gestionar las operaciones
 * relacionadas con los resultados.
 * <p>
 * Proporciona endpoints para consultar, modificar y eliminar
 * resultados registrados en la aplicación.
 * </p>
 *
 * <h2>Endpoints disponibles</h2>
 * <ul>
 *   <li><b>GET /api/v1/resultados</b>: obtiene todos los resultados.</li>
 *   <li><b>GET /api/v1/resultados/{id}</b>: obtiene un resultado por su identificador.</li>
 *   <li><b>PUT /api/v1/resultados/{id}/puntuacion</b>: modifica la puntuación de un resultado.</li>
 *   <li><b>DELETE /api/v1/resultados/{id}</b>: elimina un resultado.</li>
 * </ul>
 */
@RestController
@RequestMapping("/api/v1/resultados")
public class ResultadoController {

    @Autowired
    public ResultadoService resultadoService;

    /**
     * Endpoint para obtener todos los resultados.
     * @return Lista de todos los resultados.
     */
    @GetMapping
    public List<Resultado> mostrarTodosResultados() {
        return resultadoService.findAll();
    }
    
    /**
     * Endpoint para obtener un resultado por su ID.
     * @param id ID del resultado a buscar.
     * @return  El resultado encontrado o una excepción si no se encuentra.
     * @throws RuntimeException si el resultado no existe.
     */
    @GetMapping("/{id}")
    public Resultado getResultadoById(@PathVariable Long id){
        return resultadoService.findById(id).orElseThrow(() -> new RuntimeException("Resultado no encontrado"));
    }

    /**
     * Modifica la puntuación de un resultado existente.
     *
     * @param id identificador del resultado
     * @param puntuacion nueva puntuación del resultado
     * @return el resultado actualizado
     * @throws RuntimeException si el resultado no existe
     */
    @PutMapping("/{id}/puntuacion")
    public Resultado modificarPuntuacion(@PathVariable Long id, @RequestParam int puntuacion) {
        return resultadoService.modificarPuntuacion(id, puntuacion);
    }

    /**
     * Endpoint para eliminar un resultado por su ID.
     * @param id ID del resultado a eliminar.
     * @throws RuntimeException si el resultado no existe.
     */
    @DeleteMapping("/{id}")
    public void deleteResultadoById(@PathVariable Long id){
            resultadoService.deleteById(id);
    }
}
