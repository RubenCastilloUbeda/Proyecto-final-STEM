package app.CrosStemFit.CrosStemFit.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.CrosStemFit.CrosStemFit.entity.Equipo;
import app.CrosStemFit.CrosStemFit.entity.Usuario;
import app.CrosStemFit.CrosStemFit.service.UsuarioService;


/**
 * Controlador REST encargado de gestionar las operaciones
 * relacionadas con los usuarios.
 * <p>
 * Proporciona endpoints para realizar operaciones CRUD
 * sobre los usuarios, así como para consultar los equipos
 * a los que pertenece un usuario y crear equipos asociados
 * a un usuario.
 * </p>
 *
 * <h2>Endpoints disponibles</h2>
 * <ul>
 *   <li><b>GET /api/v1/usuarios</b>: obtiene todos los usuarios.</li>
 *   <li><b>GET /api/v1/usuarios/{id}</b>: obtiene un usuario por su identificador.</li>
 *   <li><b>GET /api/v1/usuarios/dni/{dni}</b>: obtiene un usuario por su DNI.</li>
 *   <li><b>GET /api/v1/usuarios/{id}/equipos</b>: obtiene los equipos de un usuario.</li>
 *   <li><b>POST /api/v1/usuarios</b>: crea un nuevo usuario.</li>
 *   <li><b>POST /api/v1/usuarios/{id}/equipos</b>: crea un equipo asociado a un usuario.</li>
 *   <li><b>PUT /api/v1/usuarios/{id}</b>: actualiza un usuario existente.</li>
 *   <li><b>DELETE /api/v1/usuarios/{id}</b>: elimina un usuario.</li>
 * </ul>
 */
@RestController
@RequestMapping("/api/v1/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    /**
     * Endpoint para obtener todos los usuarios.
     * @return Lista de todos los usuarios.
     */
    @GetMapping
    public List<Usuario> mostrarTodosUsuarios() {
        return usuarioService.mostrarTodosUsuarios();
    }
    
    /**
     * Endpoint para obtener un usuario por su ID.
     * @param id ID del usuario a buscar.
     * @return  El usuario encontrado o una excepción si no se encuentra.
     * @throws RuntimeException si el usuario no existe.
     */
    @GetMapping("/{id}")
    public Usuario getUsuarioById(@PathVariable Long id){
        return usuarioService.buscarUsuarioById(id).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    /**
     * Endpoint para obtener un usuario por su DNI.
     * @param dni DNI del usuario a buscar.
     * @return  El usuario encontrado o una excepción si no se encuentra.
     * @throws RuntimeException si el usuario no existe.
     */
    @GetMapping("/dni/{dni}")
    public Usuario getByDni(@PathVariable String dni) {
        return usuarioService.buscarUsuarioPorDni(dni).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    /**
     * Endpoint para obtener la lista de equipos de un usuario por su ID.
     * @param id ID del usuario del cual se quieren obtener los equipos.
     * @return  Lista de equipos del usuario o una excepción si no se encuentra el usuario.
     * @throws RuntimeException si el usuario no existe.
     */
    @GetMapping("/{id}/equipos")
    public List<Equipo> mostrarEquiposUsuario(@PathVariable Long id) {
        return usuarioService.mostrarEquiposUsuario(id);
    }

    /**
     * Endpoint para crear un nuevo usuario.
     * @param usuario Objeto de tipo Usuario con los datos del nuevo usuario a crear.
     * @return El usuario creado o una excepción si ocurre un error durante la creación.
     * @throws RuntimeException si ocurre un error durante la creación del usuario.
     */
    @PostMapping
    public Usuario guardarUsuario(@RequestBody Usuario usuario) {
        return usuarioService.save(usuario);
    }

    /**
     * Endpoint para crear un nuevo equipo para un usuario por su ID.
     * @param id ID del usuario para el cual se quiere crear el equipo.
     * @param equipo Objeto de tipo Equipo con los datos del nuevo equipo a crear.
     * @return El equipo creado o una excepción si no se encuentra el usuario o si ocurre un error durante la creación del equipo.
     * @throws RuntimeException si el usuario no existe o si ocurre un error durante la creación del equipo.
     */
    @PostMapping("/{id}/equipos")
    public Equipo crearEquipo(@PathVariable Long id, @RequestBody Equipo equipo) {
        return usuarioService.crearEquipo(equipo.getNombre(), equipo.getMaxMiembros(), id);
    }

    /**
     * Endpoint para modificar un usuario existente por su ID.
     * @param id ID del usuario a modificar.
     * @param usuarioActualizado Objeto de tipo Usuario con los datos actualizados del usuario.
     * @return El usuario modificado o una excepción si no se encuentra el usuario a modificar o si ocurre un error durante la modificación.
     * @throws RuntimeException si el usuario no existe o si ocurre un error durante la modificación.
     */
    @PutMapping("/{id}")
    public Usuario actualizarUsuario(@PathVariable Long id, @RequestBody Usuario usuarioActualizado) {
        return usuarioService.actualizarUsuario(id, usuarioActualizado);
    }

    /**
     * Endpoint para eliminar un usuario por su ID.
     * @param id ID del usuario a eliminar.
     * @throws RuntimeException si el usuario no existe.
     */
    @DeleteMapping("/{id}")
    public void deleteUsuarioById(@PathVariable Long id){
            usuarioService.deleteByID(id);
    }
}
