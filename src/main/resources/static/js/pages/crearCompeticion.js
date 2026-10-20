import { competicionAPI } from '../api/competicionAPI.js';
import { crearBarraNavegacion } from '../components/navbar.js';

document.addEventListener('DOMContentLoaded', () => {

    document.getElementById('navbar').innerHTML = crearBarraNavegacion();

    const root = document.getElementById('root');

    root.innerHTML = `
        <div class="container py-4">
            <div class="form-hero p-4 p-md-5 mb-4 text-white">
                <span class="badge bg-warning text-dark mb-3">
                    <i class="bi bi-plus-circle"></i> Nueva competición
                </span>
                <h1 class="display-5 fw-bold mb-2">Crear competición</h1>
                <p class="mb-0 opacity-75">
                    Añade una nueva competición CrossFit al sistema.
                </p>
            </div>

            <div class="row justify-content-center">
                <div class="col-lg-8">
                    <div class="card shadow-sm border-0 form-card">
                        <div class="card-body p-4 p-md-5">
                            <div id="mensaje"></div>
                            <form id="formCrearCompeticion">
                                <div class="mb-4">
                                    <label class="form-label fw-bold">
                                        <i class="bi bi-trophy text-warning"></i> Nombre
                                    </label>
                                    <input type="text" id="nombre" class="form-control form-control-lg" required>
                                </div>

                                <div class="row">
                                    <div class="col-md-6 mb-4">
                                        <label class="form-label fw-bold">
                                            <i class="bi bi-cash-coin text-success"></i> Precio
                                        </label>
                                        <input type="number" id="precio" class="form-control" required>
                                    </div>

                                    <div class="col-md-6 mb-4">
                                        <label class="form-label fw-bold">
                                            <i class="bi bi-people text-primary"></i> Máximo equipos
                                        </label>
                                        <input type="number" id="maxEquipos" class="form-control" required>
                                    </div>
                                </div>

                                <div class="row">
                                    <div class="col-md-6 mb-4">
                                        <label class="form-label fw-bold">
                                            <i class="bi bi-calendar-event text-danger"></i> Fecha inicio
                                        </label>
                                        <input type="date" id="fechaInicio" class="form-control" required>
                                    </div>

                                    <div class="col-md-6 mb-4">
                                        <label class="form-label fw-bold">
                                            <i class="bi bi-flag text-warning"></i> Fecha fin
                                        </label>
                                        <input type="date" id="fechaFin" class="form-control" required>
                                    </div>
                                </div>

                                <div class="mb-4">
                                    <label class="form-label fw-bold">
                                        <i class="bi bi-geo-alt text-danger"></i> Localización
                                    </label>
                                    <input type="text" id="localizacion" class="form-control" required>
                                </div>

                                <div class="mb-4">
                                    <label class="form-label fw-bold">
                                        <i class="bi bi-signpost text-secondary"></i> Dirección
                                    </label>
                                    <input type="text" id="direccion" class="form-control" required>
                                </div>

                                <div class="d-flex justify-content-between align-items-center flex-wrap gap-2 mt-4">
                                    <a href="index.html" class="btn btn-outline-secondary">
                                        <i class="bi bi-arrow-left"></i> Volver
                                    </a>

                                    <button type="submit" class="btn btn-success btn-lg">
                                        <i class="bi bi-check-circle"></i> Crear competición
                                    </button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    `;

    const formulario = document.getElementById('formCrearCompeticion');
    const mensaje = document.getElementById('mensaje');

    formulario.addEventListener('submit', async (e) => {
        e.preventDefault();
        const competicion = {
            nombre: document.getElementById('nombre').value,
            precio: Number(document.getElementById('precio').value),
            maxEquipos: Number(document.getElementById('maxEquipos').value),
            fechaInicio: document.getElementById('fechaInicio').value,
            fechaFin: document.getElementById('fechaFin').value,
            localizacion: document.getElementById('localizacion').value,
            direccion: document.getElementById('direccion').value
        };

        try {
            await competicionAPI.crearCompeticion(competicion);
            mensaje.innerHTML = `
                <div class="alert alert-success mb-4">
                    <i class="bi bi-check-circle-fill"></i>
                    Competición creada correctamente.
                </div>
            `;
            formulario.reset();
        } catch (error) {
            mensaje.innerHTML = `
                <div class="alert alert-danger mb-4">
                    <i class="bi bi-exclamation-triangle-fill"></i>
                    Error al crear la competición.
                </div>
            `;
        }
    });
});