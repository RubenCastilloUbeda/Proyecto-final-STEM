export function crearElementoEquipo(equipo) {
    return `
        <a href="equipoDetalle.html?id=${equipo.id}" class="card text-decoration-none text-dark mb-3">
            <div class="card-body">
                <h5 class="card-title"> ${equipo.nombre}</h5>
                ${equipo.competicion ? `
                    <div class="mt-2 border-top pt-2">
                        <strong>${equipo.competicion.nombre}</strong>
                    </div>
                ` : ''}
            </div>
        </a>`;
}