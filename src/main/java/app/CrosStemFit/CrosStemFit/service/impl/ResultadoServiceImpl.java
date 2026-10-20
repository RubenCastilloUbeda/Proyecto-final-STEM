package app.CrosStemFit.CrosStemFit.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.CrosStemFit.CrosStemFit.entity.Resultado;
import app.CrosStemFit.CrosStemFit.repository.EquipoRepository;
import app.CrosStemFit.CrosStemFit.repository.EventoRepository;
import app.CrosStemFit.CrosStemFit.repository.ResultadoRepository;
import app.CrosStemFit.CrosStemFit.service.ResultadoService;

/**
 * Implementación de la interfaz {@link ResultadoService}.
 * <p>
 * Esta clase proporciona los métodos necesarios para gestionar
 * los resultados de la aplicación, realizando operaciones CRUD
 * y administrando su relación con eventos y equipos.
 * </p>
 */
@Service
public class ResultadoServiceImpl implements ResultadoService {

    @Autowired
    public ResultadoRepository resultadoRepository;
    @Autowired
    public EventoRepository eventoRepository;
    @Autowired
    public EquipoRepository equipoRepository;

    /**
     * Obtiene todos los resultados registrados
     * en la base de datos.
     *
     * @return lista con todos los resultados almacenados
     */
    @Override
    public List<Resultado> findAll(){
        return resultadoRepository.findAll();
    }

    /**
     * Busca un resultado a partir de su identificador.
     *
     * @param id identificador del resultado
     * @return un {@link Optional} que contiene el resultado
     *         si existe, o vacío en caso contrario
     */
    @Override
    public Optional<Resultado> findById(Long id){
        return resultadoRepository.findById(id);
    }

    /**
     * Modifica la puntuación de un resultado existente.
     *
     * @param id identificador del resultado
     * @param nuevaPuntuacion nueva puntuación que se desea asignar
     * @return el resultado actualizado
     * @throws RuntimeException si el resultado no existe
     */
    @Override
    public Resultado modificarPuntuacion(Long id, int nuevaPuntuacion) {
        Resultado resultado = resultadoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Resultado no encontrado"));

        resultado.setPuntuacion(nuevaPuntuacion);
        return resultadoRepository.save(resultado);
    }

    /**
     * Elimina un resultado de la base de datos
     * a partir de su identificador.
     *
     * @param id identificador del resultado a eliminar
     */
    @Override
    public void deleteById(Long id){
        resultadoRepository.deleteById(id);
    }
}