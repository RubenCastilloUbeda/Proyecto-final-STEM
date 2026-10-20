export function crearElementoClasificacion(clasificacion, index) {

    let estilo = "";
    let icono = "";

    if (index == 0) {
        estilo = "border-warning bg-warning-subtle";
        icono = "🥇";
    } else if (index == 1) {
        estilo = "border-secondary bg-light";
        icono = "🥈";
    } else if (index == 2) {
        estilo = "border-danger bg-warning-subtle";
        icono = "🥉";
    }

    const esTop3 = index < 3;

    return `
        <div class="card mb-2 shadow-sm ${estilo}"
            style="${esTop3 ? 'transform: scale(1.03);' : ''}">
            <div class="card-body d-flex justify-content-between align-items-center">
                <div class="fw-bold"> ${icono} ${clasificacion.equipo.nombre} </div>
                <div> 
                    <span class="badge bg-dark"> ${clasificacion.puntosTotales} pts </span>
                </div>
            </div>
        </div>
    `;
}