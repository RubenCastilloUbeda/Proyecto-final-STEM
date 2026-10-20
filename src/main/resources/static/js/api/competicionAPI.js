import { API_CONFIG } from '../config/apiConfig.js';
import { fetchApi } from '../utils/apiUtils.js';

export const competicionAPI = {
    mostrarTodasCompeticiones: () => fetchApi('GET', '/competiciones'),
    
    mostrarCompeticionId: (id) => fetchApi('GET', `/competiciones/${id}`),
    
    mostrarCompeticionPorNombre: (nombreCompeticion) => fetchApi('GET', `/competiciones/nombre/${nombreCompeticion}`),
    
    obtenerEventos: (id) => fetchApi('GET', `/competiciones/${id}/eventos`),

    obtenerEquipos: (id) => fetchApi('GET', `/competiciones/${id}/equiposRegistrados`),

    obtenerClasificaciones: (id) => fetchApi('GET', `/competiciones/${id}/clasificaciones`),
    
    crearCompeticion: (competicion) => fetchApi('POST', `/competiciones`, competicion),
    
    modificarCompeticion: (id, competicion) => fetchApi('PUT', `/competiciones/${id}`, competicion),
    
    eliminarPorId: async (id) => {await fetch(`${API_CONFIG.baseURL}/competiciones/${id}`, {method: 'DELETE'});},

    registrarEvento: (id,idEvento) => fetchApi('POST', `/competiciones/${id}/eventos/${idEvento}`),

    registrarEquipo: (id,idEquipo) => fetchApi('POST', `/competiciones/${id}/equiposRegistrados/${idEquipo}`),

    eliminarEquipo: (id,idEquipo) => fetchApi('DELETE', `/competiciones/${id}/equiposRegistrados/${idEquipo}`),
    
    eliminarEvento: (id,idEvento) => fetchApi('DELETE', `/competiciones/${id}/equiposRegistrados/${idEvento}`),

};