import { API_CONFIG } from '../config/apiConfig.js';
import { fetchApi } from '../utils/apiUtils.js';

export const usuarioAPI = {
    mostrarTodosUsuarios: () => fetchApi('GET', '/usuarios'),
    
    mostrarUsuarioId: (id) => fetchApi('GET', `/usuarios/${id}`),

    mostrarUsuarioPorDni: (dni) => fetchApi('GET', `/usuarios/dni/${dni}`),

    mostrarEquiposUsuario: (id) => fetchApi('GET', `/usuarios/${id}/equipos`),

    crearUsuario: (usuario) => fetchApi('POST', `/usuarios`, usuario),

    crearEquipo: (id, equipo) => fetchApi('POST', `/usuarios/${id}/equipos`, equipo),

    modificarUsuario: (id, usuario) => fetchApi('PUT', `/usuarios/${id}`, usuario),

    eliminarUsuario: (id)  => fetchApi('DELETE', `/usuarios/${id}`),
}