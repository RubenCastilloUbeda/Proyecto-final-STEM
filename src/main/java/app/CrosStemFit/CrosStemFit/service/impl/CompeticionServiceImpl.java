package app.CrosStemFit.CrosStemFit.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.CrosStemFit.CrosStemFit.entity.Clasificacion;
import app.CrosStemFit.CrosStemFit.entity.Competicion;
import app.CrosStemFit.CrosStemFit.entity.Equipo;
import app.CrosStemFit.CrosStemFit.entity.Evento;
import app.CrosStemFit.CrosStemFit.repository.CompeticionRepository;
import app.CrosStemFit.CrosStemFit.repository.EquipoRepository;
import app.CrosStemFit.CrosStemFit.repository.EventoRepository;
import app.CrosStemFit.CrosStemFit.service.CompeticionService;

/**
 * Implementación de la interfaz {@link CompeticionService}.
 * <p>
 * Esta clase proporciona los métodos necesarios para gestionar
 * las competiciones de la aplicación, realizando operaciones
 * CRUD y gestionando los equipos registrados y los eventos
 * asociados a cada competición.
 * </p>
 */
@Service
public class CompeticionServiceImpl implements CompeticionService{

    @Autowired
    public CompeticionRepository competicionRepository;
    @Autowired
    public EquipoRepository equipoRepository;
    @Autowired
    public EventoRepository eventoRepository;

    /**
     * Obtiene todas las competiciones registradas
     * en la base de datos.
     *
     * @return lista con todas las competiciones almacenadas
     */
    @Override
    public List<Competicion> findAll() {
        return competicionRepository.findAll();
    }

    /**
     * Busca una competición a partir de su nombre.
     *
     * @param nombre nombre de la competición
     * @return un {@link Optional} que contiene la competición
     *         si existe, o vacío en caso contrario
     */
    @Override
    public Optional<Competicion> findByNombre(String nombre) {
        return competicionRepository.findByNombre(nombre);
    }

    /**
     * Busca una competición a partir de su identificador.
     *
     * @param id identificador de la competición
     * @return un {@link Optional} que contiene la competición
     *         si existe, o vacío en caso contrario
     */
    @Override
    public Optional<Competicion> findById (Long id){
        return competicionRepository.findById(id);
    }

    /**
     * Guarda una competición en la base de datos.
     *
     * @param competicion competición a guardar
     * @return la competición almacenada
     */
    @Override
    public Competicion save(Competicion competicion) {
        return competicionRepository.save(competicion);
    }

    /**
     * Elimina una competición de la base de datos
     * a partir de su identificador.
     *
     * @param id identificador de la competición a eliminar
     */
    @Override
    public void deleteById(Long id) {
        competicionRepository.deleteById(id);
    }

    /**
     * Crea una nueva competición y la almacena
     * en la base de datos.
     *
     * @param nuevaCompeticion objeto con los datos
     *                         de la nueva competición
     * @return la competición creada y almacenada
     */
    @Override
    public Competicion crearCompeticion(Competicion nuevaCompeticion) {
        Competicion competicion = new Competicion();
        competicion.setNombre(nuevaCompeticion.getNombre());
        competicion.setPrecio(nuevaCompeticion.getPrecio());
        competicion.setMaxEquipos(nuevaCompeticion.getMaxEquipos());
        competicion.setLocalizacion(nuevaCompeticion.getLocalizacion());
        competicion.setDireccion(nuevaCompeticion.getDireccion());
        competicion.setFechaInicio(nuevaCompeticion.getFechaInicio());
        competicion.setFechaFin(nuevaCompeticion.getFechaFin());
        return competicionRepository.save(competicion);
        }

    /**
     * Obtiene la clasificación de una competición.
     *
     * @param id identificador de la competición
     * @return lista de clasificaciones asociadas
     *         a la competición
     * @throws RuntimeException si la competición
     *                          no existe
     */
    @Override
    public List<Clasificacion> verClasificacion(Long id) {
        Competicion competicion = findById(id).orElseThrow(()-> new RuntimeException("Competición no encontrada"));
        return competicion.getClasificaciones();
    }

    /**
     * Obtiene los eventos asociados a una competición.
     *
     * @param id identificador de la competición
     * @return lista de eventos asociados a la competición
     * @throws RuntimeException si la competición
     *                          no existe
     */
    @Override
    public List<Evento> verEventos(Long id) {
        Competicion competicion = findById(id).orElseThrow(()-> new RuntimeException("Competición no encontrada"));
        return competicion.getEventos();
    }

    /**
     * Obtiene los equipos registrados en una competición.
     *
     * @param id identificador de la competición
     * @return lista de equipos registrados
     *         en la competición
     * @throws RuntimeException si la competición
     *                          no existe
     */
    @Override
    public List<Equipo> verEquiposApuntados(Long id) {
        Competicion competicion = findById(id).orElseThrow(()-> new RuntimeException("Competición no encontrada"));
        return competicion.getEquiposRegistrados();
    }

    /**
     * Registra un equipo en una competición.
     *
     * @param id identificador de la competición
     * @param idEquipo identificador del equipo
     *                 que se desea registrar
     * @return la competición actualizada
     * @throws RuntimeException si la competición
     *                          o el equipo no existen,
     *                          si la competición está completa
     *                          o si el equipo ya está registrado
     */
    @Override
    public Competicion registrarEquipo(Long id, Long idEquipo){
        Equipo equipoRegistrar = equipoRepository.findById(idEquipo)
        .orElseThrow(() -> new RuntimeException("Equipo no encontrado"));
        Competicion competicion = findById(id).orElseThrow(()-> new RuntimeException("Competición no encontrada"));
        
        if (competicion.getEquiposRegistrados().size() >= competicion.getMaxEquipos()) {
        throw new RuntimeException("La competición está completa");
        }

        if (competicion.getEquiposRegistrados().contains(equipoRegistrar)) {
            throw new RuntimeException("El equipo ya está registrado");
        }
        competicion.getEquiposRegistrados().add(equipoRegistrar);
        equipoRegistrar.getRegistroCompeticiones().add(competicion);
        competicionRepository.save(competicion);
        return competicion;
    }

    /**
     * Elimina un equipo registrado de una competición.
     *
     * @param id identificador de la competición
     * @param idEquipo identificador del equipo
     *                  que se desea eliminar
     * @throws RuntimeException si la competición
     *                          o el equipo no existen,
     *                          o si el equipo no está registrado
     */
    @Override
    public void eliminarEquipo(Long id, Long idEquipo){
        Equipo equipoEliminar = equipoRepository.findById(idEquipo)
        .orElseThrow(() -> new RuntimeException("Equipo no encontrado"));
        Competicion competicion = findById(id).orElseThrow(()-> new RuntimeException("Competición no encontrada"));
        
        if (!competicion.getEquiposRegistrados().contains(equipoEliminar)) {
            throw new RuntimeException("El equipo no está registrado");
        }
        competicion.getEquiposRegistrados().remove(equipoEliminar);
        competicionRepository.save(competicion);
    }

    /**
     * Elimina un evento asociado a una competición.
     *
     * @param id identificador de la competición
     * @param idEvento identificador del evento
     *                  que se desea eliminar
     * @throws RuntimeException si la competición
     *                          o el evento no existen,
     *                          o si el evento no está registrado
     */
    @Override
    public void eliminarEvento(Long id, Long idEvento){
        Competicion competicion = findById(id).orElseThrow(()-> new RuntimeException("Competición no encontrada"));
        Evento eventoEliminar = eventoRepository.findById(idEvento).orElseThrow(()-> new RuntimeException("Evento no encontrado"));
        
        if(!competicion.getEventos().contains(eventoEliminar)){
            throw new RuntimeException("El evento no está registrado");
        }
        competicion.getEventos().remove(eventoEliminar);
        competicionRepository.save(competicion);
    }

    /**
     * Añade un evento a una competición.
     *
     * @param id identificador de la competición
     * @param idEvento identificador del evento
     *                  que se desea añadir
     * @return la competición actualizada
     * @throws RuntimeException si la competición
     *                          o el evento no existen,
     *                          o si el evento ya está registrado
     */
    @Override
    public Competicion anadirEvento(Long id, Long idEvento){
        Competicion competicion = findById(id).orElseThrow(()-> new RuntimeException("Competición no encontrada"));
        Evento evento = eventoRepository.findById(idEvento).orElseThrow(()-> new RuntimeException("Evento no encontrado"));

        if(competicion.getEventos().contains(evento)){
            throw new RuntimeException("Evento ya registrado");
        }
        evento.setCompeticion(competicion);
        competicionRepository.save(competicion);
        competicion.getEventos().add(evento);
        return competicion;
    }

    /**
     * Modifica una competición existente.
     *
     * @param id identificador de la competición a modificar
     * @param competicion objeto con los nuevos datos
     *                     de la competición
     * @return la competición actualizada
     * @throws RuntimeException si la competición
     *                          no existe
     */
    @Override
    public Competicion modificarCompeticion(Long id, Competicion competicion){
        Competicion competicionExitente = competicionRepository.findById(id).orElseThrow(() -> new RuntimeException("Competicion no encontrada"));
        competicionExitente.setNombre(competicion.getNombre());
        competicionExitente.setPrecio(competicion.getPrecio());
        competicionExitente.setMaxEquipos(competicion.getMaxEquipos());
        competicionExitente.setDireccion(competicion.getDireccion());
        competicionExitente.setLocalizacion(competicion.getLocalizacion());
        competicionExitente.setFechaInicio(competicion.getFechaInicio());
        competicionExitente.setFechaFin(competicion.getFechaFin());

        return competicionRepository.save(competicionExitente);
    }
}