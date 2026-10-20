package app.CrosStemFit.CrosStemFit.service;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import app.CrosStemFit.CrosStemFit.entity.Evento;
import app.CrosStemFit.CrosStemFit.entity.Resultado;
import app.CrosStemFit.CrosStemFit.repository.EventoRepository;
import app.CrosStemFit.CrosStemFit.repository.ResultadoRepository;

@SpringBootTest
@Transactional
class EventoServiceTest {

    @Autowired
    private EventoService eventoService;

    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private ResultadoRepository resultadoRepository;

    private Evento evento;

    @BeforeEach
    void iniciarTests() {
        evento = new Evento();
        evento.setNombre("WOD Test");
        evento.setDuracion(10);

        evento = eventoRepository.save(evento);
    }

    @Test
    void findAll_devuelveEventos() {
        List<Evento> eventos = eventoService.findAll();

        assertFalse(eventos.isEmpty());
    }

    @Test
    void findById_devuelveEvento() {
        Optional<Evento> resultado = eventoService.findById(evento.getId_evento());

        assertTrue(resultado.isPresent());
        assertEquals("WOD Test", resultado.get().getNombre());
    }

    @Test
    void findByNombreEvento_devuelveEvento() {
        Optional<Evento> resultado = eventoService.findByNombreEvento("WOD Test");

        assertTrue(resultado.isPresent());
        assertEquals("WOD Test", resultado.get().getNombre());
    }

    @Test
    void save_guardaEvento() {
        Evento nuevo = new Evento();
        nuevo.setNombre("Nuevo Evento");
        nuevo.setDuracion(20);
        Evento resultado = eventoService.save(nuevo);
        assertNotNull(resultado.getId_evento());
        assertEquals("Nuevo Evento", resultado.getNombre());
    }

    @Test
    void deleteById_eliminaEvento() {
        Long id = evento.getId_evento();
        eventoService.deleteById(id);
        Optional<Evento> eliminado = eventoRepository.findById(id);

        assertTrue(eliminado.isEmpty());
    }

    @Test
    void anadirResultado_agregaResultado() {
        Resultado resultado = new Resultado();
        resultado.setPuntuacion(100);
        Evento actualizado = eventoService.anadirResultado(evento.getId_evento(), resultado);
        assertFalse(actualizado.getResultados().isEmpty());
    }

    @Test
    void modificarResultado_actualizaPuntuacion() {
        Resultado resultado = new Resultado();
        resultado.setPuntuacion(100);
        resultado.setEvento(evento);

        resultado = resultadoRepository.save(resultado);

        Resultado nuevo = new Resultado();
        nuevo.setPuntuacion(200);

        Resultado actualizado = eventoService.modificarResultado(resultado.getIdResultado(), nuevo);

        assertEquals(200, actualizado.getPuntuacion());
    }

    @Test
    void modificarResultado_lanzaErrorSiNoExiste() {
        Resultado nuevo = new Resultado();
        nuevo.setPuntuacion(200);

        assertThrows(RuntimeException.class, () -> {
            eventoService.modificarResultado(999L, nuevo);
        });
    }

    @Test
    void modificarEvento_actualizaDatos() {
        Evento actualizado = new Evento();
        actualizado.setNombre("Evento Actualizado");
        actualizado.setDuracion(30);

        Evento resultado = eventoService.modificarEvento(evento.getId_evento(), actualizado);
        assertEquals("Evento Actualizado", resultado.getNombre());
        assertEquals(30, resultado.getDuracion());
    }

    @Test
    void mostrarResultadosEventoOrdenados_funciona() {
        Resultado r1 = new Resultado();
        r1.setPuntuacion(100);

        Resultado resultado2 = new Resultado();
        resultado2.setPuntuacion(200);
        eventoService.anadirResultado(evento.getId_evento(), r1);
        eventoService.anadirResultado(evento.getId_evento(), resultado2);
        List<Resultado> resultados = eventoService.mostrarResultadosEventoOrdenados(evento.getId_evento());
        assertEquals(200, resultados.get(0).getPuntuacion());
    }
}