import { usuarioAPI } from '../api/usuarioAPI.js';
import { crearBarraNavegacion } from '../components/navbar.js';

document.addEventListener('DOMContentLoaded', async () => {

    document.getElementById('navbar').innerHTML = crearBarraNavegacion();

    const root = document.getElementById('root');
    const usuarios = await usuarioAPI.mostrarTodosUsuarios();

    let htmlUsuarios = '';
    for (const usuario of usuarios) {
        htmlUsuarios += `
            <div class="col-md-6 col-lg-4 mb-4">
                <a href="usuarioDetalle.html?id=${usuario.id_usuario}" class="text-decoration-none">
                    <div class="usuario-list-card h-100">

                        <div class="d-flex justify-content-between align-items-start mb-3">
                            <span class="badge bg-primary">
                                <i class="bi bi-person-fill"></i> Usuario
                            </span>

                            <div class="usuario-list-icon">
                                <i class="bi bi-person-circle"></i>
                            </div>
                        </div>

                        <h4 class="fw-bold text-dark mb-2">
                            ${usuario.nombre} ${usuario.apellidos}
                        </h4>

                        <p class="text-muted mb-2">
                            <i class="bi bi-card-text"></i>
                            ${usuario.dni}
                        </p>

                        <p class="text-muted mb-2">
                            <i class="bi bi-calendar"></i>
                            ${usuario.edad} años
                        </p>

                        <p class="text-muted mb-4">
                            <i class="bi bi-envelope"></i>
                            ${usuario.email}
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

            <div class="usuarios-list-hero p-4 p-md-5 mb-4 text-white">
                <div class="d-flex justify-content-between align-items-center flex-wrap gap-3">
                    <div>
                        <span class="badge bg-warning text-dark mb-3">
                            <i class="bi bi-people-fill"></i> Usuarios
                        </span>

                        <h1 class="display-5 fw-bold mb-2">Usuarios</h1>

                        <p class="mb-0 opacity-75">
                            Consulta los atletas registrados en CrosStemFit.
                        </p>
                    </div>

                    <a href="crearUsuario.html" class="btn btn-light">
                        <i class="bi bi-plus-circle"></i> Crear usuario
                    </a>
                </div>
            </div>

            <div class="d-flex justify-content-between align-items-center mb-4">
                <h2 class="h4 mb-0">
                    <i class="bi bi-person-lines-fill text-primary"></i>
                    Listado de usuarios
                </h2>

                <span class="badge bg-primary">
                    ${usuarios.length} usuarios
                </span>
            </div>

            <div class="row"> ${htmlUsuarios}</div>
        </div>
    `;
});