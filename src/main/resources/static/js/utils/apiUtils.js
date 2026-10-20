import { API_CONFIG } from '../config/apiConfig.js';

export async function fetchApi(peticion = 'GET', ruta, body = null) {
    let url = `${API_CONFIG.baseURL}${ruta}`;
    let response;

    if (peticion == 'POST' || peticion == 'PUT') {
        response = await fetch(url, {
            method: peticion,
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(body)
        });
    } else {
        response = await fetch(url, {
            method: peticion
        });
    }

    const text = await response.text();

    if (!response.ok) {
        throw new Error(text || `Error ${response.status}`);
    }

    return text ? JSON.parse(text) : null;
}