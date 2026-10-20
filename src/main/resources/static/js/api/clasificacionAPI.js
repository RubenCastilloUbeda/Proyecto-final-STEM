import { API_CONFIG } from '../config/apiConfig.js';
import { fetchApi } from '../utils/apiUtils.js';

export const clasificacionAPI = {
    
    mostrarClasificacionId: (id) => fetchApi('GET', `/clasificaciones/${id}`),

    crearClasificacion: (clasificacion) => fetchApi('POST', `/clasificaciones`,clasificacion),

    modificarClasificacion: (id, clasificacion) => fetchApi('PUT', `/clasificaciones/${id}`,clasificacion),

    eliminarClasificacion: (id) => fetchApi('DELETE', `/clasificaciones/${id}`),
}