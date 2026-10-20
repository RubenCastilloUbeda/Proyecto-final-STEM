export function crearElementoResultado(resultado, evento) {
    return `
        <div class="card text-decoration-none text-dark mb-3">
            <div class="card-body">
                <h5 class="card-title">${resultado.equipo.nombre}</h5>
                <p class="card-text">Puntuación: ${resultado.puntuacion}</p>
                <p class="card-text text-muted"> ${evento.competicion.nombre} </p>
            </div>
        </div>`;
}