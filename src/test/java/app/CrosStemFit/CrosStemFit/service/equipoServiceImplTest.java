package app.CrosStemFit.CrosStemFit.service;

import app.CrosStemFit.CrosStemFit.entity.Clasificacion;
import app.CrosStemFit.CrosStemFit.entity.Equipo;
import app.CrosStemFit.CrosStemFit.entity.Resultado;
import app.CrosStemFit.CrosStemFit.entity.Usuario;
import app.CrosStemFit.CrosStemFit.repository.EquipoRepository;
import app.CrosStemFit.CrosStemFit.repository.UsuarioRepository;

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
class EquipoServiceTest {

    @Autowired
    private EquipoService equipoService;

    @Autowired
    private EquipoRepository equipoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    private Equipo equipo;
    private Usuario usuario;

    @BeforeEach
    void iniciarTests() {
        equipo = new Equipo();
        equipo.setNombre("Equipo Test");
        equipo.setMaxMiembros(2);
        equipo = equipoRepository.save(equipo);

        usuario = new Usuario();
        usuario.setNombre("Rubén");
        usuario.setApellidos("Castillo");
        usuario.setDni("12345678A");
        usuario.setEdad(22);
        usuario.setEmail("ruben@email.com");
        usuario = usuarioRepository.save(usuario);
    }

    @Test
    void findAll_devuelveEquipos() {
        List<Equipo> equipos = equipoService.findAll();

        assertFalse(equipos.isEmpty());
    }

    @Test
    void findById_devuelveEquipo() {
        Optional<Equipo> resultado = equipoService.findById(equipo.getId());

        assertTrue(resultado.isPresent());
        assertEquals("Equipo Test", resultado.get().getNombre());
    }

    @Test
    void findByNombreEquipo_devuelveEquipo() {
        Optional<Equipo> resultado = equipoService.findByNombreEquipo("Equipo Test");

        assertTrue(resultado.isPresent());
        assertEquals("Equipo Test", resultado.get().getNombre());
    }

    @Test
    void save_guardaEquipo() {
        Equipo nuevo = new Equipo();
        nuevo.setNombre("Equipo Nuevo");
        nuevo.setMaxMiembros(3);

        Equipo resultado = equipoService.save(nuevo);

        assertNotNull(resultado.getId());
        assertEquals("Equipo Nuevo", resultado.getNombre());
    }

    @Test
    void actualizarEquipo_actualizaDatos() {
        Equipo actualizado = new Equipo();
        actualizado.setNombre("Equipo Actualizado");
        actualizado.setMaxMiembros(4);

        Equipo resultado = equipoService.actualizarEquipo(equipo.getId(), actualizado);

        assertEquals("Equipo Actualizado", resultado.getNombre());
        assertEquals(4, resultado.getMaxMiembros());
    }

    @Test
    void actualizarEquipo_lanzaErrorSiNoExiste() {
        Equipo actualizado = new Equipo();
        actualizado.setNombre("No existe");
        actualizado.setMaxMiembros(2);

        assertThrows(RuntimeException.class, () -> {
            equipoService.actualizarEquipo(999L, actualizado);
        });
    }

    @Test
    void deleteById_eliminaEquipo() {
        Long id = equipo.getId();

        equipoService.deleteById(id);

        Optional<Equipo> eliminado = equipoRepository.findById(id);

        assertTrue(eliminado.isEmpty());
    }

    @Test
    void agregarMiembro_agregaUsuarioAlEquipo() {
        Equipo resultado = equipoService.agregarMiembro(equipo.getId(), usuario.getId_usuario());

        assertTrue(resultado.getMiembrosEquipo().contains(usuario));
    }

    @Test
    void agregarMiembro_lanzaErrorSiEquipoLleno() {
        Usuario usuario2 = new Usuario();
        usuario2.setNombre("Carlos");
        usuario2.setApellidos("García");
        usuario2.setDni("87654321B");
        usuario2.setEdad(25);
        usuario2.setEmail("carlos@email.com");
        usuario2 = usuarioRepository.save(usuario2);

        Usuario usuario3 = new Usuario();
        usuario3.setNombre("Ana");
        usuario3.setApellidos("López");
        usuario3.setDni("11111111C");
        usuario3.setEdad(24);
        usuario3.setEmail("ana@email.com");
        usuarioRepository.save(usuario3);

        equipoService.agregarMiembro(equipo.getId(), usuario.getId_usuario());
        equipoService.agregarMiembro(equipo.getId(), usuario2.getId_usuario());

        assertThrows(RuntimeException.class, () -> {
            equipoService.agregarMiembro(equipo.getId(), usuario3.getId_usuario());
        });
    }

    @Test
    void mostrarMiembrosEquipo_devuelveMiembros() {
        equipoService.agregarMiembro(equipo.getId(), usuario.getId_usuario());

        List<Usuario> miembros = equipoService.mostrarMiembrosEquipo(equipo.getId());

        assertEquals(1, miembros.size());
    }

    @Test
    void eliminarMiembro_eliminaUsuarioDelEquipo() {
        equipoService.agregarMiembro(equipo.getId(), usuario.getId_usuario());

        Equipo resultado = equipoService.eliminarMiembro(equipo.getId(), usuario.getId_usuario());

        assertFalse(resultado.getMiembrosEquipo().contains(usuario));
    }

    @Test
    void eliminarMiembro_lanzaErrorSiNoPerteneceAlEquipo() {
        assertThrows(RuntimeException.class, () -> {
            equipoService.eliminarMiembro(equipo.getId(), usuario.getId_usuario());
        });
    }

    @Test
    void mostrarResultadosEquipo_devuelveResultados() {
        Resultado resultado = new Resultado();
        resultado.setPuntuacion(100);

        equipo.getResultados().add(resultado);
        equipo = equipoRepository.save(equipo);

        List<Resultado> resultados = equipoService.mostrarResultadosEquipo(equipo.getId());

        assertFalse(resultados.isEmpty());
    }

    @Test
    void mostrarClasificacionesEquipo_devuelveClasificaciones() {
        Clasificacion clasificacion = new Clasificacion();
        clasificacion.setPosicion(1);
        clasificacion.setPuntosTotales(400);

        equipo.getRegistroClasificaciones().add(clasificacion);
        equipo = equipoRepository.save(equipo);

        List<Clasificacion> clasificaciones = equipoService.mostrarClasificacionesEquipo(equipo.getId());

        assertFalse(clasificaciones.isEmpty());
    }
}