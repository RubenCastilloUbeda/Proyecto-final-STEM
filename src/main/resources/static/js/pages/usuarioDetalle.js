import { usuarioAPI } from '../api/usuarioAPI.js';
import { equipoAPI } from '../api/equipoAPI.js';
import { crearBarraNavegacion } from '../components/navbar.js';
import { crearElementoEquipo } from '../components/equipoComponente.js';

document.addEventListener('DOMContentLoaded', async () => {
    document.getElementById('navbar').innerHTML = crearBarraNavegacion();

    const parametros = new URLSearchParams(window.location.search);
    const idUsuario = parametros.get('id');
    const root = document.getElementById('root');

    const usuario = await usuarioAPI.mostrarUsuarioId(idUsuario);
    const equipos = await usuarioAPI.mostrarEquiposUsuario(idUsuario);

    document.title = `${usuario.nombre} ${usuario.apellidos}`;

let htmlEquipos = '';

for (const equipo of equipos) {

    const clasificaciones = await equipoAPI.mostrarClasificacionesEquipo(equipo.id);

    let mejorPuesto = '—';
    let puntos = '—';
    if (clasificaciones.length > 0) {
        mejorPuesto = Math.min(...clasificaciones.map(c => c.posicion));
        puntos = clasificaciones.reduce((acc, c) => acc + c.puntosTotales, 0);
    }
    htmlEquipos += `
        <div class="col-md-6 col-lg-4 mb-3">
            <a href="equipoDetalle.html?id=${equipo.id}" class="text-decoration-none">
                <div class="usuario-equipo-card h-100">

                    <div class="d-flex justify-content-between align-items-start mb-2">
                        <span class="badge bg-primary">
                            <i class="bi bi-people-fill"></i> Equipo
                        </span>

                        <i class="bi bi-trophy fs-4 text-warning"></i>
                    </div>
                    <h5 class="fw-bold text-dark mb-3"> ${equipo.nombre} </h5>
                    <div class="d-flex justify-content-between text-muted">
                        <div>
                            <small>Puesto</small>
                            <div class="fw-bold text-dark">#${mejorPuesto}</div>
                        </div>

                        <div>
                            <small>Puntos</small>
                            <div class="fw-bold text-dark">${puntos}</div>
                        </div>
                    </div>
                </div>
            </a>
        </div>
    `;
}

    root.innerHTML = `
        <div class="container py-4">

            <div class="usuario-hero p-4 p-md-5 mb-4 text-white">
                <div class="d-flex justify-content-between align-items-start flex-wrap gap-3">
                    <div>
                        <span class="badge bg-warning text-dark mb-3">
                            <i class="bi bi-person-fill"></i> Atleta
                        </span>

                        <h1 class="display-5 fw-bold mb-2">
                            ${usuario.nombre} ${usuario.apellidos}
                        </h1>

                        <p class="mb-0 opacity-75">
                            <i class="bi bi-envelope-fill"></i> ${usuario.email}
                        </p>
                    </div>

                    <div class="d-flex gap-2 flex-wrap">
                        <a href="usuariosLista.html" class="btn btn-light">
                            <i class="bi bi-arrow-left"></i> Volver
                        </a>

                        <button id="btnEditar" class="btn btn-warning">
                            <i class="bi bi-pencil-square"></i> Editar
                        </button>

                        <button id="btnEliminar" class="btn btn-outline-danger bg-white">
                            <i class="bi bi-trash"></i> Eliminar
                        </button>
                    </div>
                </div>
            </div>

            <div class="row g-4 mb-4">
                <div class="col-md-4">
                    <div class="info-card text-center">
                        <i class="bi bi-card-text fs-2 text-primary"></i>
                        <h6 class="mt-2 text-muted">DNI</h6>
                        <h4>${usuario.dni}</h4>
                    </div>
                </div>

                <div class="col-md-4">
                    <div class="info-card text-center">
                        <i class="bi bi-calendar fs-2 text-success"></i>
                        <h6 class="mt-2 text-muted">Edad</h6>
                        <h4>${usuario.edad} años</h4>
                    </div>
                </div>

                <div class="col-md-4">
                    <div class="info-card text-center">
                        <i class="bi bi-people-fill fs-2 text-warning"></i>
                        <h6 class="mt-2 text-muted">Equipos</h6>
                        <h4>${equipos.length}</h4>
                    </div>
                </div>
            </div>

            <div id="contenedorFormularioEditar" class="mb-4 d-none">
                <div class="card shadow-sm border-0 form-card">
                    <div class="card-body p-4">
                        <h3 class="mb-3">
                            <i class="bi bi-pencil-square text-warning"></i>
                            Editar usuario
                        </h3>

                        <form id="formEditarUsuario">
                            <div class="row">
                                <div class="col-md-6 mb-3">
                                    <label class="form-label">Nombre</label>
                                    <input id="editarNombre" class="form-control" value="${usuario.nombre}">
                                </div>

                                <div class="col-md-6 mb-3">
                                    <label class="form-label">Apellidos</label>
                                    <input id="editarApellidos" class="form-control" value="${usuario.apellidos}">
                                </div>

                                <div class="col-md-4 mb-3">
                                    <label class="form-label">DNI</label>
                                    <input id="editarDni" class="form-control" value="${usuario.dni}">
                                </div>

                                <div class="col-md-4 mb-3">
                                    <label class="form-label">Edad</label>
                                    <input type="number" id="editarEdad" class="form-control" value="${usuario.edad}">
                                </div>

                                <div class="col-md-4 mb-3">
                                    <label class="form-label">Email</label>
                                    <input id="editarEmail" class="form-control" value="${usuario.email}">
                                </div>
                            </div>

                            <button class="btn btn-primary">
                                <i class="bi bi-check-circle"></i> Guardar cambios
                            </button>
                        </form>
                    </div>
                </div>
            </div>

            <div class="card shadow-sm border-0">
                <div class="card-body p-4">
                    <div class="d-flex justify-content-between align-items-center mb-3">
                        <h2 class="h4 mb-0">
                            <i class="bi bi-people-fill text-primary"></i>
                            Equipos del usuario
                        </h2>
                        <span class="badge bg-primary">
                            ${equipos.length} equipos
                        </span>
                    </div>
            <div class="row"> ${htmlEquipos || '<p class="text-muted mb-0">Este usuario no pertenece a ningún equipo.</p>'} </div>
            </div>
            </div>
        </div>
    `;

    document.getElementById('btnEditar').addEventListener('click', () => {
        document.getElementById('contenedorFormularioEditar').classList.toggle('d-none');
    });

    document.getElementById('btnEliminar').addEventListener('click', async () => {
        const confirmar = confirm('¿Seguro que quieres eliminar este usuario?');
        if (!confirmar) return;

        await usuarioAPI.eliminarUsuario(idUsuario);

        root.innerHTML = `
            <div class="container mt-5">
                <div class="alert alert-success">
                    Usuario eliminado correctamente.
                </div>
                <a href="usuariosLista.html" class="btn btn-primary">
                    Volver a usuarios
                </a>
            </div>
        `;
    });

    document.getElementById('formEditarUsuario').addEventListener('submit', async (e) => {
        e.preventDefault();

        const usuarioActualizado = {
            nombre: document.getElementById('editarNombre').value,
            apellidos: document.getElementById('editarApellidos').value,
            dni: document.getElementById('editarDni').value,
            edad: Number(document.getElementById('editarEdad').value),
            email: document.getElementById('editarEmail').value
        };

        await usuarioAPI.modificarUsuario(idUsuario, usuarioActualizado);
        location.reload();
    });
});