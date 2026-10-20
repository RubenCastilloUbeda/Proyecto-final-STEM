import { competicionAPI } from '../api/competicionAPI.js';
import { crearBarraNavegacion } from '../components/navbar.js';
import { crearElementoEvento } from '../components/eventoComponente.js';
import { crearElementoClasificacion } from '../components/crearElementoClasificacion.js';

document.addEventListener('DOMContentLoaded', async () => {

    document.getElementById('navbar').innerHTML = crearBarraNavegacion();

    const parametros = new URLSearchParams(window.location.search);
    const idCompeticion = parametros.get('id');
    const root = document.getElementById('root');

    const competicion = await competicionAPI.mostrarCompeticionId(idCompeticion);
    const eventos = await competicionAPI.obtenerEventos(idCompeticion);
    const clasificaciones = await competicionAPI.obtenerClasificaciones(idCompeticion);

    document.title = competicion.nombre;

    let htmlEventos = '';
    for (const evento of eventos) {
        htmlEventos += `
            <a href="eventoDetalle.html?id=${evento.id_evento}" 
            class="evento-card d-block text-decoration-none mb-3">
            <div class="d-flex justify-content-between align-items-center">
                <div>
                    <h6 class="mb-1 fw-bold text-dark"> ${evento.nombre} </h6>
                    <small class="text-muted"> ⏱ ${evento.duracion} min </small>
                </div>

                <div class="icono-evento">
                    <i class="bi bi-lightning-charge-fill"></i>
                </div>
            </div>
        </a>
    `;
}

    let htmlClasificacion = '';
    clasificaciones.sort((a, b) => b.puntosTotales - a.puntosTotales).forEach((clasificacion, index) => {
            htmlClasificacion += crearElementoClasificacion(clasificacion, index);
        });

root.innerHTML = `
    <div class="container py-4">

        <div class="competition-hero p-4 p-md-5 mb-4 text-white">
            <div class="d-flex justify-content-between align-items-start flex-wrap gap-3">
                <div>
                    <span class="badge bg-warning text-dark mb-3">
                        <i class="bi bi-trophy-fill"></i> Competición CrossFit
                    </span>
                    <h1 class="display-5 fw-bold mb-2">${competicion.nombre}</h1>
                    <p class="mb-0 opacity-75">
                        <i class="bi bi-geo-alt-fill"></i>
                        ${competicion.localizacion} · ${competicion.direccion}
                    </p>
                </div>

                <div class="d-flex gap-2">
                    <button id="btnEditar" class="btn btn-light">
                        <i class="bi bi-pencil-square"></i> Editar
                    </button>
                    <button id="btnEliminar" class="btn btn-outline-danger bg-white">
                        <i class="bi bi-trash"></i> Eliminar
                    </button>
                    <button id="btnAnadirEvento" class="btn btn-success">
                            <i class="bi bi-person-plus"></i> Añadir evento
                        </button>
                </div>
            </div>
        </div>

        <div id="contenedorFormularioEditar" style="display:none;" class="mb-4">
            <div class="card shadow-sm border-0">
                <div class="card-body p-4">
                    <h3 class="mb-3">
                        <i class="bi bi-pencil-square text-warning"></i>
                        Editar competición
                    </h3>

                    <form id="formEditarCompeticion">
                        <div class="row">
                            <div class="col-md-6 mb-3">
                                <label class="form-label">Nombre</label>
                                <input id="editarNombre" class="form-control" value="${competicion.nombre}">
                            </div>

                            <div class="col-md-3 mb-3">
                                <label class="form-label">Precio</label>
                                <input id="editarPrecio" class="form-control" value="${competicion.precio}">
                            </div>

                            <div class="col-md-3 mb-3">
                                <label class="form-label">Máximo equipos</label>
                                <input id="editarMaxEquipos" class="form-control" value="${competicion.maxEquipos}">
                            </div>

                            <div class="col-md-6 mb-3">
                                <label class="form-label">Fecha inicio</label>
                                <input type="date" id="editarFechaInicio" class="form-control" value="${competicion.fechaInicio}">
                            </div>

                            <div class="col-md-6 mb-3">
                                <label class="form-label">Fecha fin</label>
                                <input type="date" id="editarFechaFin" class="form-control" value="${competicion.fechaFin}">
                            </div>

                            <div class="col-md-6 mb-3">
                                <label class="form-label">Localización</label>
                                <input id="editarLocalizacion" class="form-control" value="${competicion.localizacion}">
                            </div>

                            <div class="col-md-6 mb-3">
                                <label class="form-label">Dirección</label>
                                <input id="editarDireccion" class="form-control" value="${competicion.direccion}">
                            </div>
                        </div>

                        <button class="btn btn-primary">
                            <i class="bi bi-check-circle"></i> Guardar cambios
                        </button>
                    </form>
                </div>
            </div>
        </div>

        <div id="contenedorFormAnadirEvento" class="mb-4 d-none">
                <div class="card shadow-sm border-0 form-card">
                    <div class="card-body p-4">
                        <h3 class="mb-3">
                            <i class="bi bi-person-plus text-success"></i>
                            Añadir Evento
                        </h3>

                        <form id="formAnadirEvento">
                            <div class="mb-3">
                                <label class="form-label">ID evento</label>
                                <input type="number" id="id_evento" class="form-control" required>
                            </div>

                            <button class="btn btn-success">
                                <i class="bi bi-check-circle"></i> Añadir Evento
                            </button>
                        </form>
                    </div>
                </div>
            </div>

        <div class="row g-4 mb-4">
            <div class="col-md-3">
                <div class="info-card text-center">
                    <i class="bi bi-cash-coin fs-2 text-success"></i>
                    <h6 class="mt-2 text-muted">Precio</h6>
                    <h4>${competicion.precio}€</h4>
                </div>
            </div>

            <div class="col-md-3">
                <div class="info-card text-center">
                    <i class="bi bi-people-fill fs-2 text-primary"></i>
                    <h6 class="mt-2 text-muted">Máx. equipos</h6>
                    <h4>${competicion.maxEquipos}</h4>
                </div>
            </div>

            <div class="col-md-3">
                <div class="info-card text-center">
                    <i class="bi bi-calendar-event fs-2 text-danger"></i>
                    <h6 class="mt-2 text-muted">Inicio</h6>
                    <h5>${competicion.fechaInicio}</h5>
                </div>
            </div>

            <div class="col-md-3">
                <div class="info-card text-center">
                    <i class="bi bi-flag-fill fs-2 text-warning"></i>
                    <h6 class="mt-2 text-muted">Fin</h6>
                    <h5>${competicion.fechaFin}</h5>
                </div>
            </div>
        </div>

        <div class="row g-4">
            <div class="col-lg-7">
                <div class="card shadow-sm border-0 h-100">
                    <div class="card-body p-4">
                        <div class="d-flex align-items-center justify-content-between mb-3">
                            <h2 class="h4 mb-0">
                                <i class="bi bi-lightning-charge-fill text-warning"></i>
                                Eventos
                            </h2>
                            <span class="badge bg-primary">${eventos.length} eventos</span>
                        </div>
                        <div>${htmlEventos}</div>
                    </div>
                </div>
            </div>
            <div class="col-lg-5">
                <div class="card shadow-sm border-0 clasificacion-card">
                    <div class="card-body p-4">
                        <div class="d-flex align-items-center justify-content-between mb-3">
                            <h2 class="h4 mb-0">
                                <i class="bi bi-trophy-fill text-warning"></i>
                                Clasificación
                            </h2>
                            <button class="btn btn-sm btn-outline-primary"
                                    type="button"
                                    data-bs-toggle="collapse"
                                    data-bs-target="#clasificacionCollapse">
                                Ver / ocultar
                            </button>
                        </div>
                        <div class="collapse show" id="clasificacionCollapse">
                            <div class="list-group classification-list">
                                ${htmlClasificacion}
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
`;

    const btnEditar = document.getElementById('btnEditar');
    btnEditar.addEventListener('click', () => {
        const form = document.getElementById('contenedorFormularioEditar');
        form.style.display = form.style.display == 'none' ? 'block' : 'none';
    });

    document.getElementById('btnAnadirEvento').addEventListener('click', () => {
        document.getElementById('contenedorFormAnadirEvento').classList.toggle('d-none');
    });

    const btnEliminar = document.getElementById('btnEliminar');
    btnEliminar.addEventListener('click', async () => {
        const confirmar = confirm('¿Seguro que quieres eliminar esta competición?');
        if (!confirmar) return;
        await competicionAPI.eliminarPorId(idCompeticion);

        root.innerHTML = `
            <div class="container mt-5">
                <div class="alert alert-success"> Competición eliminada correctamente </div>
                <a href="/competicionesLista.html" class="btn btn-primary mt-3"> Volver </a>
            </div>
        `;
    });

    const formularioEditar = document.getElementById('formEditarCompeticion');
    formularioEditar.addEventListener('submit', async (e) => {
        e.preventDefault();
        const competicionActualizada = {
            nombre: document.getElementById('editarNombre').value,
            precio: document.getElementById('editarPrecio').value,
            fechaInicio: document.getElementById('editarFechaInicio').value,
            fechaFin: document.getElementById('editarFechaFin').value,
            localizacion: document.getElementById('editarLocalizacion').value,
            direccion: document.getElementById('editarDireccion').value,
            maxEquipos: document.getElementById('editarMaxEquipos').value
        };

        await competicionAPI.modificarCompeticion(idCompeticion,competicionActualizada);
        location.reload();
    });

    document.getElementById('formAnadirEvento').addEventListener('submit', async (e) => {
            e.preventDefault();
    
            const id_evento = document.getElementById('id_evento').value;
    
            await competicionAPI.registrarEvento(idCompeticion, id_evento);
            location.reload();
        });
});