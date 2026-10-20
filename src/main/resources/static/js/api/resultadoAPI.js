import { API_CONFIG } from '../config/apiConfig.js';
import { fetchApi } from '../utils/apiUtils.js';

export const resultadoAPI = {
    mostrarTodosResultados: () => fetchApi('GET', '/resultados'),
    
    mostrarResultadoId: (id) => fetchApi('GET', `/resultados/${id}`),

    eliminarResultado: (id) => fetchApi('DELETE', `/resultados/${id}`),
}