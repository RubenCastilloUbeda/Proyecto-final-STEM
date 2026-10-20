package app.CrosStemFit.CrosStemFit.service;

import app.CrosStemFit.CrosStemFit.entity.Clasificacion;
import app.CrosStemFit.CrosStemFit.entity.Equipo;
import app.CrosStemFit.CrosStemFit.repository.ClasificacionRepository;
import app.CrosStemFit.CrosStemFit.repository.EquipoRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ClasificacionServiceTest {

    @Autowired
    private ClasificacionService clasificacionService;

    @Autowired
    private ClasificacionRepository clasificacionRepository;

    @Autowired
    private EquipoRepository equipoRepository;

    private Clasificacion clasificacion;

    @BeforeEach
    void iniciarTests() {
        clasificacion = new Clasificacion();
        clasificacion.setPosicion(1);
        clasificacion.setPuntosTotales(400);

        clasificacion = clasificacionRepository.save(clasificacion);
    }

    @Test
    void findAll_devuelveClasificaciones() {
        List<Clasificacion> clasificaciones = clasificacionService.findAll();

        assertFalse(clasificaciones.isEmpty());
    }

    @Test
    void findById_devuelveClasificacion() {
        Optional<Clasificacion> resultado = clasificacionService.findById(clasificacion.getidClasificacion());

        assertTrue(resultado.isPresent());
        assertEquals(1, resultado.get().getPosicion());
    }

    @Test
    void anadirClasificacion_guardaClasificacion() {
        Clasificacion nueva = new Clasificacion();
        nueva.setPosicion(2);
        nueva.setPuntosTotales(350);

        Clasificacion resultado = clasificacionService.anadirClasificacion(nueva);

        assertNotNull(resultado.getidClasificacion());
        assertEquals(2, resultado.getPosicion());
        assertEquals(350, resultado.getPuntosTotales());
    }

    @Test
    void modificarClasificacion_actualizaDatos() {
        Equipo equipo = new Equipo();
        equipo.setNombre("Equipo Test");
        equipo.setMaxMiembros(2);
        equipo = equipoRepository.save(equipo);

        Clasificacion actualizada = new Clasificacion();
        actualizada.setPosicion(3);
        actualizada.setPuntosTotales(250);
        actualizada.setEquipo(equipo);

        Clasificacion resultado = clasificacionService.modificarClasificacion(
                clasificacion.getidClasificacion(),
                actualizada
        );

        assertEquals(3, resultado.getPosicion());
        assertEquals(250, resultado.getPuntosTotales());
        assertEquals(equipo.getId(), resultado.getEquipo().getId());
    }

    @Test
    void modificarClasificacion_lanzaErrorSiNoExiste() {
        Clasificacion actualizada = new Clasificacion();
        actualizada.setPosicion(2);
        actualizada.setPuntosTotales(300);

        assertThrows(RuntimeException.class, () -> {
            clasificacionService.modificarClasificacion(999L, actualizada);
        });
    }

    @Test
    void deleteById_eliminaClasificacion() {
        Long id = clasificacion.getidClasificacion();

        clasificacionService.deleteById(id);

        Optional<Clasificacion> resultado = clasificacionRepository.findById(id);

        assertTrue(resultado.isEmpty());
    }
}