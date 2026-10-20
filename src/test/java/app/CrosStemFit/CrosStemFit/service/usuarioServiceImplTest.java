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

import app.CrosStemFit.CrosStemFit.entity.Equipo;
import app.CrosStemFit.CrosStemFit.entity.Usuario;
import app.CrosStemFit.CrosStemFit.repository.EquipoRepository;
import app.CrosStemFit.CrosStemFit.repository.UsuarioRepository;

@SpringBootTest
@Transactional
class UsuarioServiceTest {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EquipoRepository equipoRepository;

    private Usuario usuario;

    @BeforeEach
    void iniciarTests() {
        usuario = new Usuario();
        usuario.setNombre("Rubén");
        usuario.setApellidos("Castillo");
        usuario.setDni("12345678A");
        usuario.setEdad(22);
        usuario.setEmail("ruben@email.com");

        usuario = usuarioRepository.save(usuario);
    }

    @Test
    void mostrarTodosUsuarios_devuelveUsuarios() {
        List<Usuario> usuarios = usuarioService.mostrarTodosUsuarios();

        assertFalse(usuarios.isEmpty());
    }

    @Test
    void buscarUsuarioById_devuelveUsuario() {
        Optional<Usuario> resultado = usuarioService.buscarUsuarioById(usuario.getId_usuario());

        assertTrue(resultado.isPresent());
        assertEquals("Rubén", resultado.get().getNombre());
    }

    @Test
    void buscarUsuarioPorDni_devuelveUsuario() {
        Optional<Usuario> resultado = usuarioService.buscarUsuarioPorDni("12345678A");

        assertTrue(resultado.isPresent());
        assertEquals("12345678A", resultado.get().getDni());
    }

    @Test
    void save_guardaUsuarioMayorDeEdad() {
        Usuario nuevo = new Usuario();
        nuevo.setNombre("Carlos");
        nuevo.setApellidos("García");
        nuevo.setDni("87654321B");
        nuevo.setEdad(25);
        nuevo.setEmail("carlos@email.com");

        Usuario resultado = usuarioService.save(nuevo);

        assertNotNull(resultado.getId_usuario());
        assertEquals("Carlos", resultado.getNombre());
    }

    @Test
    void save_lanzaErrorSiUsuarioMenorDeEdad() {
        Usuario menor = new Usuario();
        menor.setNombre("Menor");
        menor.setApellidos("Edad");
        menor.setDni("11111111A");
        menor.setEdad(16);
        menor.setEmail("menor@email.com");

        assertThrows(IllegalArgumentException.class, () -> {
            usuarioService.save(menor);
        });
    }

    @Test
    void actualizarUsuario_modificaDatos() {
        Usuario datosActualizados = new Usuario();
        datosActualizados.setNombre("Rubén Actualizado");
        datosActualizados.setApellidos("Castillo Nuevo");
        datosActualizados.setDni("99999999C");
        datosActualizados.setEdad(24);
        datosActualizados.setEmail("nuevo@email.com");

        Usuario resultado = usuarioService.actualizarUsuario(usuario.getId_usuario(), datosActualizados);

        assertEquals("Rubén Actualizado", resultado.getNombre());
        assertEquals("Castillo Nuevo", resultado.getApellidos());
        assertEquals("99999999C", resultado.getDni());
        assertEquals(24, resultado.getEdad());
        assertEquals("nuevo@email.com", resultado.getEmail());
    }

    @Test
    void crearEquipo_creaEquipoConUsuarioComoMiembro() {
        Equipo equipo = usuarioService.crearEquipo("Equipo Test", 2, usuario.getId_usuario());

        assertNotNull(equipo.getId());
        assertEquals("Equipo Test", equipo.getNombre());
        assertEquals(2, equipo.getMaxMiembros());
        assertTrue(equipo.getMiembrosEquipo().contains(usuario));
    }

    @Test
    void deleteByID_eliminaUsuario() {
        Long idUsuario = usuario.getId_usuario();
        usuarioService.deleteByID(idUsuario);
        Optional<Usuario> resultado = usuarioRepository.findById(idUsuario);
        assertTrue(resultado.isEmpty());
    }
}