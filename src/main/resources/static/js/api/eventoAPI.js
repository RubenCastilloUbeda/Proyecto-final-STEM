import { API_CONFIG } from '../config/apiConfig.js';
import { fetchApi } from '../utils/apiUtils.js';

export const eventoAPI = {
    mostrarTodosEventos: () => fetchApi('GET', '/eventos'),
    
    mostrarEventoId: (id) => fetchApi('GET', `/eventos/${id}`),

    mostrarResultadosEventoOrdenados: (id) => fetchApi('GET', `/eventos/${id}/resultados`),

    mostrarEventoPorNombre: (nombre) => fetchApi('GET', `/eventos/nombre/${nombre}`),

    mostrarResultadosEvento: (id) => fetchApi('GET', `/eventos/${id}/resultados`),

    crearEvento: (evento) => fetchApi('POST', `/eventos`,evento),

    modificarEvento: (id, evento) => fetchApi('PUT', `/eventos/${id}`,evento),

    anadirResultadoEvento: (id, resultado) => fetchApi('POST', `/eventos/${id}/resultados`, resultado),

    modificarResultadoEvento: (id, resultado) => fetchApi('PUT', `/eventos/${id}/resultados`, resultado),

    eliminarEvento: (id) => fetchApi('DELETE', `/eventos/${id}`),
}