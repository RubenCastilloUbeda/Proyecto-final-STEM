package app.CrosStemFit.CrosStemFit.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.CrosStemFit.CrosStemFit.entity.Clasificacion;
import app.CrosStemFit.CrosStemFit.repository.ClasificacionRepository;
import app.CrosStemFit.CrosStemFit.service.ClasificacionService;

/**
 * Implementación de la interfaz {@link ClasificacionService}.
 * <p>
 * Esta clase proporciona los métodos necesarios para gestionar
 * las clasificaciones de la aplicación, realizando operaciones
 * CRUD mediante el repositorio {@link ClasificacionRepository}.
 * </p>
 */
@Service
public class ClasificacionServiceImpl implements ClasificacionService {

    /**
     * Repositorio encargado de gestionar las operaciones
     * de persistencia de las clasificaciones.
     */
    @Autowired
    public ClasificacionRepository clasificacionRepository;

    /**
     * Obtiene todas las clasificaciones registradas en la base de datos.
     *
     * @return lista con todas las clasificaciones almacenadas
     */
    @Override
    public List<Clasificacion> findAll() {
        return clasificacionRepository.findAll();
    }

    /**
     * Busca una clasificación a partir de su identificador.
     *
     * @param id identificador de la clasificación
     * @return un {@link Optional} que contiene la clasificación
     *         si existe, o vacío en caso contrario
     */
    @Override
    public Optional<Clasificacion> findById(Long id) {
        return clasificacionRepository.findById(id);
    }

    /**
     * Elimina una clasificación de la base de datos
     * a partir de su identificador.
     *
     * @param id identificador de la clasificación a eliminar
     */
    @Override
    public void deleteById(Long id) {
        clasificacionRepository.deleteById(id);
    }

    /**
     * Añade una nueva clasificación a la base de datos.
     *
     * @param clasificacion clasificación que se desea registrar
     * @return la clasificación guardada con su identificador generado
     */
    @Override
    public Clasificacion anadirClasificacion(Clasificacion clasificacion) {
        return clasificacionRepository.save(clasificacion);
    }

    /**
     * Modifica una clasificación existente.
     *
     * @param id identificador de la clasificación a modificar
     * @param clasificacionActualizar objeto con los nuevos datos
     *                                de la clasificación
     * @return la clasificación actualizada
     * @throws RuntimeException si no existe una clasificación
     *                          con el identificador indicado
     */
    @Override
    public Clasificacion modificarClasificacion(Long id, Clasificacion clasificacionActualizar) {

        Clasificacion clasificacion = clasificacionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Clasificacion no encontrada"));

        clasificacion.setPosicion(clasificacionActualizar.getPosicion());
        clasificacion.setPuntosTotales(clasificacionActualizar.getPuntosTotales());
        clasificacion.setEquipo(clasificacionActualizar.getEquipo());

        return clasificacionRepository.save(clasificacion);
    }
}
