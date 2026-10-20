package app.CrosStemFit.CrosStemFit.service;

import app.CrosStemFit.CrosStemFit.entity.Clasificacion;
import app.CrosStemFit.CrosStemFit.entity.Competicion;
import app.CrosStemFit.CrosStemFit.entity.Equipo;
import app.CrosStemFit.CrosStemFit.entity.Evento;
import app.CrosStemFit.CrosStemFit.repository.CompeticionRepository;
import app.CrosStemFit.CrosStemFit.repository.EquipoRepository;
import app.CrosStemFit.CrosStemFit.repository.EventoRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CompeticionServiceTest {

    @Autowired
    private CompeticionService competicionService;

    @Autowired
    private CompeticionRepository competicionRepository;

    @Autowired
    private EquipoRepository equipoRepository;

    @Autowired
    private EventoRepository eventoRepository;

    private Competicion competicion;
    private Equipo equipo;
    private Evento evento;

    @BeforeEach
    void iniciarTests() {
        competicion = new Competicion();
        competicion.setNombre("Competicion Test");
        competicion.setPrecio(25.0);
        competicion.setMaxEquipos(2);
        competicion.setFechaInicio(LocalDate.of(2026, 5, 1));
        competicion.setFechaFin(LocalDate.of(2026, 5, 2));
        competicion.setLocalizacion("Granada");
        competicion.setDireccion("Calle Test");
        competicion = competicionRepository.save(competicion);

        equipo = new Equipo();
        equipo.setNombre("Equipo Test");
        equipo.setMaxMiembros(2);
        equipo = equipoRepository.save(equipo);

        evento = new Evento();
        evento.setNombre("Evento Test");
        evento.setDuracion(10);
        evento = eventoRepository.save(evento);
    }

    @Test
    void findAll_devuelveCompeticiones() {
        List<Competicion> competiciones = competicionService.findAll();

        assertFalse(competiciones.isEmpty());
    }

    @Test
    void findById_devuelveCompeticion() {
        Optional<Competicion> resultado = competicionService.findById(competicion.getIdCompeticion());

        assertTrue(resultado.isPresent());
        assertEquals("Competicion Test", resultado.get().getNombre());
    }

    @Test
    void findByNombre_devuelveCompeticion() {
        Optional<Competicion> resultado = competicionService.findByNombre("Competicion Test");

        assertTrue(resultado.isPresent());
        assertEquals("Competicion Test", resultado.get().getNombre());
    }

    @Test
    void save_guardaCompeticion() {
        Competicion nueva = new Competicion();
        nueva.setNombre("Competicion Nueva");
        nueva.setPrecio(30.0);
        nueva.setMaxEquipos(5);
        nueva.setFechaInicio(LocalDate.of(2026, 6, 1));
        nueva.setFechaFin(LocalDate.of(2026, 6, 2));
        nueva.setLocalizacion("Sevilla");
        nueva.setDireccion("Calle Nueva");

        Competicion resultado = competicionService.save(nueva);

        assertNotNull(resultado.getIdCompeticion());
        assertEquals("Competicion Nueva", resultado.getNombre());
    }

    @Test
    void crearCompeticion_creaCompeticion() {
        Competicion nueva = new Competicion();
        nueva.setNombre("Competicion Creada");
        nueva.setPrecio(35.0);
        nueva.setMaxEquipos(10);
        nueva.setFechaInicio(LocalDate.of(2026, 7, 1));
        nueva.setFechaFin(LocalDate.of(2026, 7, 2));
        nueva.setLocalizacion("Málaga");
        nueva.setDireccion("Calle Creada");

        Competicion resultado = competicionService.crearCompeticion(nueva);

        assertNotNull(resultado.getIdCompeticion());
        assertEquals("Competicion Creada", resultado.getNombre());
        assertEquals(35.0, resultado.getPrecio());
    }

    @Test
    void deleteById_eliminaCompeticion() {
        Long id = competicion.getIdCompeticion();

        competicionService.deleteById(id);

        Optional<Competicion> eliminada = competicionRepository.findById(id);

        assertTrue(eliminada.isEmpty());
    }

    @Test
    void registrarEquipo_agregaEquipoACompeticion() {
        Competicion resultado = competicionService.registrarEquipo(
                competicion.getIdCompeticion(),
                equipo.getId()
        );

        assertTrue(resultado.getEquiposRegistrados().contains(equipo));
    }

    @Test
    void registrarEquipo_lanzaErrorSiEquipoYaRegistrado() {
        competicionService.registrarEquipo(competicion.getIdCompeticion(), equipo.getId());

        assertThrows(RuntimeException.class, () -> {
            competicionService.registrarEquipo(competicion.getIdCompeticion(), equipo.getId());
        });
    }

    @Test
    void registrarEquipo_lanzaErrorSiCompeticionCompleta() {
        Equipo equipo2 = new Equipo();
        equipo2.setNombre("Equipo 2");
        equipo2.setMaxMiembros(2);
        equipo2 = equipoRepository.save(equipo2);

        Equipo equipo3 = new Equipo();
        equipo3.setNombre("Equipo 3");
        equipo3.setMaxMiembros(2);
        equipo3 = equipoRepository.save(equipo3);

        competicionService.registrarEquipo(competicion.getIdCompeticion(), equipo.getId());
        competicionService.registrarEquipo(competicion.getIdCompeticion(), equipo2.getId());

        Long idEquipo3 = equipo3.getId();

        assertThrows(RuntimeException.class, () -> {
            competicionService.registrarEquipo(competicion.getIdCompeticion(), idEquipo3);
        });
    }

    @Test
    void verEquiposApuntados_devuelveEquipos() {
        competicionService.registrarEquipo(competicion.getIdCompeticion(), equipo.getId());

        List<Equipo> equipos = competicionService.verEquiposApuntados(competicion.getIdCompeticion());

        assertFalse(equipos.isEmpty());
    }

    @Test
    void eliminarEquipo_eliminaEquipoDeCompeticion() {
        competicionService.registrarEquipo(competicion.getIdCompeticion(), equipo.getId());

        competicionService.eliminarEquipo(competicion.getIdCompeticion(), equipo.getId());

        List<Equipo> equipos = competicionService.verEquiposApuntados(competicion.getIdCompeticion());

        assertFalse(equipos.contains(equipo));
    }

    @Test
    void eliminarEquipo_lanzaErrorSiEquipoNoRegistrado() {
        assertThrows(RuntimeException.class, () -> {
            competicionService.eliminarEquipo(competicion.getIdCompeticion(), equipo.getId());
        });
    }

    @Test
    void anadirEvento_agregaEventoACompeticion() {
        Competicion resultado = competicionService.anadirEvento(
                competicion.getIdCompeticion(),
                evento.getId_evento()
        );

        assertEquals(competicion.getIdCompeticion(), resultado.getIdCompeticion());
        assertEquals(competicion, evento.getCompeticion());
    }

    @Test
    void anadirEvento_lanzaErrorSiEventoYaRegistrado() {
        competicionService.anadirEvento(competicion.getIdCompeticion(), evento.getId_evento());

        assertThrows(RuntimeException.class, () -> {
            competicionService.anadirEvento(competicion.getIdCompeticion(), evento.getId_evento());
        });
    }

    @Test
    void verEventos_devuelveEventos() {
        competicionService.anadirEvento(competicion.getIdCompeticion(), evento.getId_evento());

        List<Evento> eventos = competicionService.verEventos(competicion.getIdCompeticion());

        assertFalse(eventos.isEmpty());
    }

    @Test
    void eliminarEvento_eliminaEventoDeCompeticion() {
        competicionService.anadirEvento(competicion.getIdCompeticion(), evento.getId_evento());

        competicionService.eliminarEvento(competicion.getIdCompeticion(), evento.getId_evento());

        List<Evento> eventos = competicionService.verEventos(competicion.getIdCompeticion());

        assertFalse(eventos.contains(evento));
    }

    @Test
    void verClasificacion_devuelveClasificaciones() {
        Clasificacion clasificacion = new Clasificacion();
        clasificacion.setPosicion(1);
        clasificacion.setPuntosTotales(400);
        clasificacion.setCompeticion(competicion);

        competicion.getClasificaciones().add(clasificacion);
        competicion = competicionRepository.save(competicion);

        List<Clasificacion> clasificaciones = competicionService.verClasificacion(competicion.getIdCompeticion());

        assertFalse(clasificaciones.isEmpty());
    }

    @Test
    void modificarCompeticion_actualizaDatos() {
        Competicion actualizada = new Competicion();
        actualizada.setNombre("Competicion Actualizada");
        actualizada.setPrecio(50.0);
        actualizada.setMaxEquipos(8);
        actualizada.setFechaInicio(LocalDate.of(2026, 8, 1));
        actualizada.setFechaFin(LocalDate.of(2026, 8, 2));
        actualizada.setLocalizacion("Córdoba");
        actualizada.setDireccion("Calle Actualizada");

        Competicion resultado = competicionService.modificarCompeticion(
                competicion.getIdCompeticion(),
                actualizada
        );

        assertEquals("Competicion Actualizada", resultado.getNombre());
        assertEquals(50.0, resultado.getPrecio());
        assertEquals("Córdoba", resultado.getLocalizacion());
    }

    @Test
    void modificarCompeticion_lanzaErrorSiNoExiste() {
        Competicion actualizada = new Competicion();
        actualizada.setNombre("No existe");

        assertThrows(RuntimeException.class, () -> {
            competicionService.modificarCompeticion(999L, actualizada);
        });
    }
}