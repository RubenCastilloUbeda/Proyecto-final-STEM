package app.CrosStemFit.CrosStemFit.service;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import app.CrosStemFit.CrosStemFit.entity.Resultado;
import app.CrosStemFit.CrosStemFit.repository.ResultadoRepository;

@SpringBootTest
@Transactional
class ResultadoServiceTest {

    @Autowired
    private ResultadoService resultadoService;

    @Autowired
    private ResultadoRepository resultadoRepository;

    private Resultado resultado;

    @BeforeEach
    void iniciarTests() {
        resultado = new Resultado();
        resultado.setPuntuacion(100);
        resultado = resultadoRepository.save(resultado);
    }

    @Test
    void findAll_devuelveResultados() {
        List<Resultado> resultados = resultadoService.findAll();
        assertFalse(resultados.isEmpty());
    }

    @Test
    void findById_devuelveResultado() {
        Optional<Resultado> resultadoEncontrado = resultadoService.findById(resultado.getIdResultado());
        assertTrue(resultadoEncontrado.isPresent());
        assertEquals(100, resultadoEncontrado.get().getPuntuacion());
    }

    @Test
    void modificarPuntuacion_actualizaResultado() {
        Resultado resultadoActualizado = resultadoService.modificarPuntuacion(resultado.getIdResultado(), 250);
        assertEquals(250, resultadoActualizado.getPuntuacion());
    }

    @Test
    void modificarPuntuacion_lanzaErrorSiNoExiste() {
        assertThrows(RuntimeException.class, () -> {
            resultadoService.modificarPuntuacion(999L, 200);
        });
    }

    @Test
    void deleteById_eliminaResultado() {
        Long id = resultado.getIdResultado();
        resultadoService.deleteById(id);
        Optional<Resultado> resultadoEliminado = resultadoRepository.findById(id);
        assertTrue(resultadoEliminado.isEmpty());
    }
}