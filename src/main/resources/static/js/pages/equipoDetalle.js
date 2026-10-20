import { equipoAPI } from '../api/equipoAPI.js';
import { crearBarraNavegacion } from '../components/navbar.js';

document.addEventListener('DOMContentLoaded', async () => {
    document.getElementById('navbar').innerHTML = crearBarraNavegacion();

    const parametros = new URLSearchParams(window.location.search);
    const idEquipo = parametros.get('id');
    const root = document.getElementById('root');

    const equipo = await equipoAPI.mostrarEquipoId(idEquipo);
    const miembrosEquipo = await equipoAPI.mostrarMiembrosEquipo(idEquipo);
    const clasificaciones = await equipoAPI.mostrarClasificacionesEquipo(idEquipo);

    document.title = `Equipo - ${equipo.nombre}`;

    let htmlMiembros = '';
    for (const usuario of miembrosEquipo) {
        htmlMiembros += `
            <div class="miembro-card mb-3">
                <div class="d-flex justify-content-between align-items-center flex-wrap gap-3">
                    <div>
                        <span class="badge bg-primary mb-2">
                            <i class="bi bi-person-fill"></i> Miembro
                        </span>

                        <h5 class="mb-1">${usuario.nombre} ${usuario.apellidos}</h5>

                        <p class="text-muted mb-0">
                            <i class="bi bi-envelope"></i> ${usuario.email}
                        </p>
                    </div>

                    <div class="d-flex gap-2">
                        <a href="usuarioDetalle.html?id=${usuario.id_usuario}" class="btn btn-sm btn-outline-primary">
                            Ver usuario
                        </a>

                        <button class="btn btn-sm btn-outline-danger btnEliminarMiembro" data-id="${usuario.id_usuario}">
                            Eliminar
                        </button>
                    </div>
                </div>
            </div>
        `;
    }

    let htmlClasificaciones = '';

    clasificaciones
        .sort((a, b) => a.posicion - b.posicion)
        .forEach((clasificacion) => {
            htmlClasificaciones += `
                <div class="clasificacion-equipo-card mb-3">
                    <div class="d-flex justify-content-between align-items-center">
                        <div>
                            <span class="badge bg-warning text-dark mb-2">
                                <i class="bi bi-trophy-fill"></i>
                                ${clasificacion.competicion ? clasificacion.competicion.nombre : 'Competición'}
                            </span>

                            <h5 class="mb-1">Puesto #${clasificacion.posicion}</h5>

                            <p class="text-muted mb-0">
                                ${clasificacion.puntosTotales} puntos totales
                            </p>
                        </div>

                        <div class="resultado-icono">
                            ${clasificacion.posicion === 1 ? '🥇' : clasificacion.posicion === 2 ? '🥈' : clasificacion.posicion === 3 ? '🥉' : '💪'}
                        </div>
                    </div>
                </div>
            `;
        });

    root.innerHTML = `
        <div class="container py-4">

            <div class="equipo-hero p-4 p-md-5 mb-4 text-white">
                <div class="d-flex justify-content-between align-items-start flex-wrap gap-3">
                    <div>
                        <span class="badge bg-warning text-dark mb-3">
                            <i class="bi bi-people-fill"></i> Equipo
                        </span>

                        <h1 class="display-5 fw-bold">${equipo.nombre}</h1>

                        <p class="mb-0 opacity-75">
                            Equipo registrado en CrosStemFit
                        </p>
                    </div>

                    <div class="d-flex gap-2 flex-wrap">
                        <a href="equiposLista.html" class="btn btn-light">
                            <i class="bi bi-arrow-left"></i> Volver
                        </a>

                        <button id="btnEditar" class="btn btn-warning">
                            <i class="bi bi-pencil-square"></i> Editar
                        </button>

                        <button id="btnAgregar" class="btn btn-success">
                            <i class="bi bi-person-plus"></i> Agregar miembro
                        </button>

                        <button id="btnEliminar" class="btn btn-outline-danger bg-white">
                            <i class="bi bi-trash"></i> Eliminar
                        </button>
                    </div>
                </div>
            </div>

            <div id="formEditar" class="mb-4 d-none">
                <div class="card shadow-sm border-0 form-card">
                    <div class="card-body p-4">
                        <h3 class="mb-3">
                            <i class="bi bi-pencil-square text-warning"></i>
                            Editar equipo
                        </h3>

                        <form id="formEditarEquipo">
                            <div class="mb-3">
                                <label class="form-label">Nombre</label>
                                <input id="editarNombre" class="form-control" value="${equipo.nombre}">
                            </div>

                            <div class="mb-3">
                                <label class="form-label">Máximo de miembros</label>
                                <input type="number" id="editarMaxMiembros" class="form-control" value="${equipo.maxMiembros}">
                            </div>

                            <button class="btn btn-primary">
                                <i class="bi bi-check-circle"></i> Guardar cambios
                            </button>
                        </form>
                    </div>
                </div>
            </div>

            <div id="formAgregar" class="mb-4 d-none">
                <div class="card shadow-sm border-0 form-card">
                    <div class="card-body p-4">
                        <h3 class="mb-3">
                            <i class="bi bi-person-plus text-success"></i>
                            Agregar miembro
                        </h3>

                        <form id="formAgregarMiembro">
                            <div class="mb-3">
                                <label class="form-label">ID usuario</label>
                                <input type="number" id="idUsuario" class="form-control" required>
                            </div>

                            <button class="btn btn-success">
                                <i class="bi bi-check-circle"></i> Agregar miembro
                            </button>
                        </form>
                    </div>
                </div>
            </div>

            <div class="row g-4 mb-4">
                <div class="col-md-4">
                    <div class="info-card text-center">
                        <i class="bi bi-people-fill fs-2 text-primary"></i>
                        <h6 class="mt-2 text-muted">Miembros</h6>
                        <h4>${miembrosEquipo.length}</h4>
                    </div>
                </div>

                <div class="col-md-4">
                    <div class="info-card text-center">
                        <i class="bi bi-person-check fs-2 text-success"></i>
                        <h6 class="mt-2 text-muted">Máximo miembros</h6>
                        <h4>${equipo.maxMiembros}</h4>
                    </div>
                </div>

                <div class="col-md-4">
                    <div class="info-card text-center">
                        <i class="bi bi-trophy-fill fs-2 text-warning"></i>
                        <h6 class="mt-2 text-muted">Clasificaciones</h6>
                        <h4>${clasificaciones.length}</h4>
                    </div>
                </div>
            </div>

            <div class="row g-4">
                <div class="col-lg-6">
                    <div class="card shadow-sm border-0 h-100">
                        <div class="card-body p-4">
                            <div class="d-flex justify-content-between align-items-center mb-3">
                                <h2 class="h4 mb-0">
                                    <i class="bi bi-people-fill text-primary"></i>
                                    Miembros
                                </h2>

                                <span class="badge bg-primary">
                                    ${miembrosEquipo.length}
                                </span>
                            </div>

                            ${htmlMiembros || '<p class="text-muted mb-0">Este equipo no tiene miembros.</p>'}
                        </div>
                    </div>
                </div>

                <div class="col-lg-6">
                    <div class="card shadow-sm border-0 h-100">
                        <div class="card-body p-4">
                            <div class="d-flex justify-content-between align-items-center mb-3">
                                <h2 class="h4 mb-0">
                                    <i class="bi bi-trophy-fill text-warning"></i>
                                    Clasificaciones
                                </h2>

                                <span class="badge bg-warning text-dark">
                                    ${clasificaciones.length}
                                </span>
                            </div>
                            ${htmlClasificaciones || '<p class="text-muted mb-0">Este equipo todavía no tiene clasificaciones.</p>'}
                        </div>
                    </div>
                </div>
            </div>
        </div>
    `;

    document.getElementById('btnEditar').addEventListener('click', () => {
        document.getElementById('formEditar').classList.toggle('d-none');
    });

    document.getElementById('btnAgregar').addEventListener('click', () => {
        document.getElementById('formAgregar').classList.toggle('d-none');
    });

    document.getElementById('btnEliminar').addEventListener('click', async () => {
        const confirmar = confirm('¿Seguro que quieres eliminar este equipo?');
        if (!confirmar) return;

        await equipoAPI.eliminarPorId(idEquipo);
        root.innerHTML = `
            <div class="container mt-5">
                <div class="alert alert-success">
                    Equipo eliminado correctamente.
                </div>
                <a href="usuariosLista.html" class="btn btn-primary"> Volver </a>
            </div>
        `;
    });

    document.getElementById('formEditarEquipo').addEventListener('submit', async (e) => {
        e.preventDefault();

        const equipoActualizado = {
            nombre: document.getElementById('editarNombre').value,
            maxMiembros: Number(document.getElementById('editarMaxMiembros').value)
        };

        await equipoAPI.modificarEquipo(idEquipo, equipoActualizado);
        location.reload();
    });

    document.getElementById('formAgregarMiembro').addEventListener('submit', async (e) => {
        e.preventDefault();

        const idUsuario = document.getElementById('idUsuario').value;

        await equipoAPI.agregarMiembro(idEquipo, idUsuario);
        location.reload();
    });

    root.addEventListener('click', async (e) => {
        if (e.target.classList.contains('btnEliminarMiembro')) {
            const idUsuario = e.target.dataset.id;
            const confirmar = confirm('¿Eliminar este miembro?');

            if (!confirmar) return;

            await equipoAPI.eliminarMiembroEquipo(idEquipo, idUsuario);
            location.reload();
        }
    });
});