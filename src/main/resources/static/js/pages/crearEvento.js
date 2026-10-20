import { eventoAPI } from '../api/eventoAPI.js';
import { crearBarraNavegacion } from '../components/navbar.js';

document.addEventListener('DOMContentLoaded', () => {

    document.getElementById('navbar').innerHTML = crearBarraNavegacion();

    const root = document.getElementById('root');

    root.innerHTML = `
        <div class="container py-4">

            <div class="form-hero p-4 p-md-5 mb-4 text-white">
                <span class="badge bg-warning text-dark mb-3">
                    <i class="bi bi-lightning-charge-fill"></i> Nuevo evento
                </span>

                <h1 class="display-5 fw-bold mb-2">Crear evento</h1>

                <p class="mb-0 opacity-75">
                    Añade un nuevo WOD o prueba a CrosStemFit.
                </p>
            </div>

            <div class="row justify-content-center">
                <div class="col-lg-8">

                    <div class="card shadow-sm border-0 form-card">
                        <div class="card-body p-4 p-md-5">

                            <div id="mensaje"></div>

                            <form id="formCrearEvento">

                                <div class="mb-4">
                                    <label class="form-label fw-bold">
                                        <i class="bi bi-lightning-fill text-warning"></i> Nombre del evento
                                    </label>
                                    <input type="text" id="nombre" class="form-control form-control-lg" required>
                                </div>

                                <div class="mb-4">
                                    <label class="form-label fw-bold">
                                        <i class="bi bi-stopwatch-fill text-primary"></i> Duración
                                    </label>
                                    <input type="number" id="duracion" class="form-control" required>
                                </div>

                                <div class="d-flex justify-content-between align-items-center flex-wrap gap-2 mt-4">
                                    <a href="eventosLista.html" class="btn btn-outline-secondary">
                                        <i class="bi bi-arrow-left"></i> Volver
                                    </a>

                                    <button type="submit" class="btn btn-success btn-lg">
                                        <i class="bi bi-check-circle"></i> Crear evento
                                    </button>
                                </div>

                            </form>

                        </div>
                    </div>

                </div>
            </div>

        </div>
    `;

    const formulario = document.getElementById('formCrearEvento');
    const mensaje = document.getElementById('mensaje');

    formulario.addEventListener('submit', async (e) => {
        e.preventDefault();

        const evento = {
            nombre: document.getElementById('nombre').value,
            duracion: Number(document.getElementById('duracion').value)
        };

        try {
            await eventoAPI.crearEvento(evento);

            mensaje.innerHTML = `
                <div class="alert alert-success mb-4">
                    <i class="bi bi-check-circle-fill"></i>
                    Evento creado correctamente.
                </div>
            `;

            formulario.reset();

        } catch (error) {
            mensaje.innerHTML = `
                <div class="alert alert-danger mb-4">
                    <i class="bi bi-exclamation-triangle-fill"></i>
                    Error al crear el evento.
                </div>
            `;
        }
    });
});