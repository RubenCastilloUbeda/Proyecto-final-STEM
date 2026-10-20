import { equipoAPI } from '../api/equipoAPI.js';
import { crearBarraNavegacion } from '../components/navbar.js';

document.addEventListener('DOMContentLoaded', async () => {

    document.getElementById('navbar').innerHTML = crearBarraNavegacion();

    const root = document.getElementById('root');
    const equipos = await equipoAPI.mostrarTodosEquipos();

    let htmlEquipos = '';

    for (const equipo of equipos) {
        htmlEquipos += `
            <div class="col-md-6 col-lg-4 mb-4">
                <a href="equipoDetalle.html?id=${equipo.id}" class="text-decoration-none">
                    <div class="equipo-list-card h-100">
                        <div class="d-flex justify-content-between align-items-start mb-3">
                            <span class="badge bg-success">
                                <i class="bi bi-people-fill"></i> Equipo
                            </span>

                            <div class="equipo-list-icon">
                                <i class="bi bi-trophy-fill"></i>
                            </div>

                        </div>

                        <h4 class="fw-bold text-dark mb-3">
                            ${equipo.nombre}
                        </h4>

                        <p class="text-muted mb-2">
                            <i class="bi bi-person-check"></i>
                            Máx. miembros: ${equipo.maxMiembros}
                        </p>

                        <div class="d-flex justify-content-end mt-3">
                            <span class="btn btn-sm btn-outline-success">
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
            <div class="equipos-list-hero p-4 p-md-5 mb-4 text-white">
                <div class="d-flex justify-content-between align-items-center flex-wrap gap-3">
                    <div>
                        <span class="badge bg-warning text-dark mb-3">
                            <i class="bi bi-people"></i> Equipos
                        </span>

                        <h1 class="display-5 fw-bold mb-2">
                            Equipos CrossFit
                        </h1>

                        <p class="mb-0 opacity-75">
                            Gestiona los equipos participantes en competiciones.
                        </p>
                    </div>

                    <a href="crearEquipo.html" class="btn btn-light">
                        <i class="bi bi-plus-circle"></i> Crear equipo
                    </a>

                </div>
            </div>

            <div class="d-flex justify-content-between align-items-center mb-4">
                <h2 class="h4 mb-0">
                    <i class="bi bi-people-fill text-success"></i>
                    Listado de equipos
                </h2>

                <span class="badge bg-success">
                    ${equipos.length} equipos
                </span>
            </div>

            <div class="row"> ${htmlEquipos} </div>
        </div>
    `;
});