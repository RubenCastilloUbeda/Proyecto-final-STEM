export function crearElementoClasificacion(clasificacion) {
    return `
        <a href="clasificacionDetalle.html?id=${clasificacion.idClasificacion}" class="card text-decoration-none text-dark mb-3">
            <div class="card-body">
                <h5 class="card-title">Posicion: ${clasificacion.posicion} - ${clasificacion.puntosTotales} puntos</h5>
                <h5 class="card-title"> ${clasificacion.equipo.nombre}</h5>
            </div>
        </a>`;
}