import { API_CONFIG } from '../config/apiConfig.js';
import { fetchApi } from '../utils/apiUtils.js';

export const equipoAPI = {
    mostrarTodosEquipos: () => fetchApi('GET', '/equipos'),
    
    mostrarEquipoId: (id) => fetchApi('GET', `/equipos/${id}`),
    
    mostrarEquipoPorNombre: (nombreEquipo) => fetchApi('GET', `/equipos/nombre/${nombreEquipo}`),

    mostrarResultadosEquipo: (idEquipo) => fetchApi('GET', `/equipos/${idEquipo}/resultados`),

    mostrarMiembrosEquipo: (idEquipo) => fetchApi('GET', `/equipos/${idEquipo}/miembrosEquipo`),

    mostrarClasificacionesEquipo: (idEquipo) => fetchApi('GET', `/equipos/${idEquipo}/clasificaciones`),

    eliminarPorId: (id) => fetchApi('DELETE', `/equipos/${id}`),

    eliminarMiembroEquipo: (idEquipo, idUsuario) => fetchApi('DELETE', `/equipos/${idEquipo}/miembrosEquipo/${idUsuario}`),

    modificarEquipo: (id, equipo) => fetchApi('PUT', `/equipos/${id}`, equipo),

    agregarMiembro: (idEquipo, idUsuario) => fetchApi('POST', `/equipos/${idEquipo}/miembros/${idUsuario}`),

    crearEquipo: (equipo) => fetchApi('POST', '/equipos', equipo)
}