import { equipoAPI } from '../api/equipoAPI.js';
import { usuarioAPI } from '../api/usuarioAPI.js';
import { crearBarraNavegacion } from '../components/navbar.js';

document.addEventListener('DOMContentLoaded', () => {

    document.getElementById('navbar').innerHTML = crearBarraNavegacion();
    const root = document.getElementById('root');
    let miembrosSeleccionados = [];

    root.innerHTML = `
        <div class="container py-4">
            <div class="form-hero p-4 p-md-5 mb-4 text-white">
                <span class="badge bg-warning text-dark mb-3">
                    <i class="bi bi-people-fill"></i> Nuevo equipo
                </span>
                <h1 class="display-5 fw-bold mb-2">Crear equipo</h1>

                <p class="mb-0 opacity-75"> Crea un nuevo equipo y añade sus miembros. </p>
            </div>

            <div class="row justify-content-center">
                <div class="col-lg-8">
                    <div class="card shadow-sm border-0 form-card">
                        <div class="card-body p-4 p-md-5">
                            <div id="mensaje"></div>

                            <form id="formCrearEquipo">

                                <div class="mb-4">
                                    <label class="form-label fw-bold">
                                        <i class="bi bi-people-fill text-success"></i> Nombre del equipo
                                    </label>
                                    <input type="text" id="nombre" class="form-control form-control-lg" required>
                                </div>

                                <div class="mb-4">
                                    <label class="form-label fw-bold">
                                        <i class="bi bi-person-check text-primary"></i> Máximo miembros
                                    </label>
                                    <input type="number" id="maxMiembros" class="form-control" required>
                                </div>
                                <hr>
                                <h5 class="mb-3">
                                    <i class="bi bi-person-plus text-success"></i> Añadir miembros por DNI
                                </h5>

                                <div class="d-flex gap-2 mb-3">
                                    <input type="text" id="dniBusqueda" class="form-control" placeholder="Introduce DNI">
                                    <button type="button" id="btnBuscarUsuario" class="btn btn-outline-primary">
                                        Buscar
                                    </button>
                                </div>

                                <div id="resultadoBusqueda" class="mb-3"></div>
                                <h6>Miembros añadidos:</h6>
                                <div id="listaMiembros" class="mb-4"></div>

                                <div class="d-flex justify-content-between mt-4">
                                    <a href="equiposLista.html" class="btn btn-outline-secondary">
                                        <i class="bi bi-arrow-left"></i> Volver
                                    </a>

                                    <button type="submit" class="btn btn-success btn-lg">
                                        <i class="bi bi-check-circle"></i> Crear equipo
                                    </button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    `;

    const mensaje = document.getElementById('mensaje');

    document.getElementById('btnBuscarUsuario').addEventListener('click', async () => {
    const dni = document.getElementById('dniBusqueda').value;
    const resultadoBusqueda = document.getElementById('resultadoBusqueda');
        resultadoBusqueda.innerHTML = '';

    try {
        const usuario = await usuarioAPI.mostrarUsuarioPorDni(dni);

        resultadoBusqueda.innerHTML = `
            <div class="alert alert-info d-flex justify-content-between align-items-center">
                <div>
                    <strong>${usuario.nombre} ${usuario.apellidos}</strong><br>
                    <span class="text-muted">${usuario.dni}</span>
                </div>

                <button type="button" class="btn btn-sm btn-success" id="btnAgregarUsuario">
                    Añadir
                </button>
            </div>
        `;

        document.getElementById('btnAgregarUsuario').addEventListener('click', () => {
            if (miembrosSeleccionados.find(u => u.id_usuario == usuario.id_usuario)) {
                resultadoBusqueda.innerHTML = `
                    <div class="alert alert-warning"> Este usuario ya está añadido al equipo. </div>
                `;
                return;
            }

            miembrosSeleccionados.push(usuario);
            renderMiembros();
            document.getElementById('dniBusqueda').value = '';
            resultadoBusqueda.innerHTML = `
                <div class="alert alert-success">
                    Usuario añadido correctamente.
                </div>
            `;
        });

        } catch (error) {
            resultadoBusqueda.innerHTML = `
                <div class="alert alert-danger">
                    Usuario no encontrado con ese DNI. Inténtalo de nuevo.
                </div>
            `;
        }
    });

    function renderMiembros() {
        let html = '';
        miembrosSeleccionados.forEach(usuario => {
            html += `
                <div class="miembro-card mb-2 d-flex justify-content-between align-items-center">
                    <span>${usuario.nombre} ${usuario.apellidos}</span>
                    <button class="btn btn-sm btn-danger btnEliminarMiembro" data-id="${usuario.id_usuario}">
                        X
                    </button>
                </div>
            `;
        });
        document.getElementById('listaMiembros').innerHTML = html;
    }

    document.getElementById('listaMiembros').addEventListener('click', (e) => {
        if (e.target.classList.contains('btnEliminarMiembro')) {
            const id = e.target.dataset.id;
            miembrosSeleccionados = miembrosSeleccionados.filter(u => u.id_usuario != id);
            renderMiembros();
        }
    });

    document.getElementById('formCrearEquipo').addEventListener('submit', async (e) => {
        e.preventDefault();
        const equipo = {
            nombre: document.getElementById('nombre').value,
            maxMiembros: Number(document.getElementById('maxMiembros').value)
        };

        try {
            const equipoCreado = await equipoAPI.crearEquipo(equipo);

            for (const usuario of miembrosSeleccionados) {
                await equipoAPI.agregarMiembro(equipoCreado.id, usuario.id_usuario);
            }

            mensaje.innerHTML = `
                <div class="alert alert-success"> Equipo creado correctamente </div>
            `;

        } catch {
            mensaje.innerHTML = `
                <div class="alert alert-danger"> Error al crear el equipo </div>
            `;
        }
    });
});