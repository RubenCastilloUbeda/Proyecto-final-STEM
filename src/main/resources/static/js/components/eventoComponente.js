export function crearElementoEvento(evento) {
    return `
        <a href="eventoDetalle.html?id=${evento.id_evento}" class="card text-decoration-none text-dark mb-3">
            <div class="card-body">
                <h5 class="card-title">${evento.nombre}</h5>
                <p class="card-text text-muted">Duracion: ${evento.duracion} minutos</p>
            </div>
        </a>`;
}
