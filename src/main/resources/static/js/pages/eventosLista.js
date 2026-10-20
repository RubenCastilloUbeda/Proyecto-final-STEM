import { eventoAPI } from '../api/eventoAPI.js';
import { crearBarraNavegacion } from '../components/navbar.js';

document.addEventListener('DOMContentLoaded', async () => {

    document.getElementById('navbar').innerHTML = crearBarraNavegacion();

    const root = document.getElementById('root');
    const eventos = await eventoAPI.mostrarTodosEventos();

    let htmlEventos = '';

    for (const evento of eventos) {
        htmlEventos += `
            <div class="col-md-6 col-lg-4 mb-4">
                <a href="eventoDetalle.html?id=${evento.id_evento}" class="text-decoration-none">
                    <div class="evento-list-card h-100">
                        <div class="d-flex justify-content-between align-items-start mb-3">
                            <span class="badge bg-warning text-dark">
                                <i class="bi bi-lightning-charge-fill"></i> WOD
                            </span>

                            <div class="evento-list-icon">
                                <i class="bi bi-stopwatch"></i>
                            </div>
                        </div>

                        <h4 class="fw-bold text-dark mb-3">${evento.nombre}</h4>

                        <p class="text-muted mb-2">
                            <i class="bi bi-clock"></i>
                            ${evento.duracion} minutos
                        </p>

                        <p class="text-muted mb-4">
                            <i class="bi bi-trophy"></i>
                            ${evento.competicion?.nombre || 'Sin competición'}
                        </p>

                        <div class="d-flex justify-content-end">
                            <span class="btn btn-sm btn-outline-primary">
                                Ver detalle
                            </span>
                        </div>
                    </div>
                </a>
            </div>
        `;
    }

    root.innerHTML = `
        <div class="container py-4">

            <div class="evento-list-hero p-4 p-md-5 mb-4 text-white">
                <div class="d-flex justify-content-between align-items-center flex-wrap gap-3">
                    <div>
                        <span class="badge bg-warning text-dark mb-3">
                            <i class="bi bi-calendar-event"></i> Eventos
                        </span>
                        <h1 class="display-5 fw-bold mb-2">Eventos CrossFit</h1>
                        <p class="mb-0 opacity-75">
                            Consulta todos los WODs registrados en las competiciones.
                        </p>
                    </div>

                    <a href="crearEvento.html" class="btn btn-light">
                        <i class="bi bi-plus-circle"></i> Crear evento
                    </a>
                </div>
            </div>

            <div class="d-flex justify-content-between align-items-center mb-4">
                <h2 class="h4 mb-0">
                    <i class="bi bi-list-task text-primary"></i>
                    Listado de eventos
                </h2>

                <span class="badge bg-primary">
                    ${eventos.length} eventos
                </span>
            </div>

            <div class="row">${htmlEventos}
            </div>
        </div>
    `;
});