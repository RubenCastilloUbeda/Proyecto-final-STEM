import { usuarioAPI } from '../api/usuarioAPI.js';
import { crearBarraNavegacion } from '../components/navbar.js';

document.addEventListener('DOMContentLoaded', () => {

    document.getElementById('navbar').innerHTML = crearBarraNavegacion();

    const root = document.getElementById('root');

    root.innerHTML = `
        <div class="container py-4">
            <div class="form-hero p-4 p-md-5 mb-4 text-white">
                <span class="badge bg-warning text-dark mb-3">
                    <i class="bi bi-person-plus-fill"></i> Nuevo usuario
                </span>
                <h1 class="display-5 fw-bold mb-2">Crear usuario</h1>
                <p class="mb-0 opacity-75"> Registra un nuevo atleta en CrosStemFit. </p>
            </div>

            <div class="row justify-content-center">
                <div class="col-lg-8">
                    <div class="card shadow-sm border-0 form-card">
                        <div class="card-body p-4 p-md-5">
                            <div id="mensaje"></div>
                            <form id="formCrearUsuario">

                                <div class="row">
                                    <div class="col-md-6 mb-4">
                                        <label class="form-label fw-bold">
                                            <i class="bi bi-person-fill text-primary"></i> Nombre
                                        </label>
                                        <input type="text" id="nombre" class="form-control form-control-lg" required>
                                    </div>

                                    <div class="col-md-6 mb-4">
                                        <label class="form-label fw-bold">
                                            <i class="bi bi-person-lines-fill text-primary"></i> Apellidos
                                        </label>
                                        <input type="text" id="apellidos" class="form-control form-control-lg" required>
                                    </div>
                                </div>

                                <div class="row">
                                    <div class="col-md-4 mb-4">
                                        <label class="form-label fw-bold">
                                            <i class="bi bi-card-text text-warning"></i> DNI
                                        </label>
                                        <input type="text" id="dni" class="form-control" required>
                                    </div>

                                    <div class="col-md-4 mb-4">
                                        <label class="form-label fw-bold">
                                            <i class="bi bi-calendar text-success"></i> Edad
                                        </label>
                                        <input type="number" id="edad" class="form-control" required>
                                    </div>

                                    <div class="col-md-4 mb-4">
                                        <label class="form-label fw-bold">
                                            <i class="bi bi-envelope-fill text-danger"></i> Email
                                        </label>
                                        <input type="email" id="email" class="form-control" required>
                                    </div>
                                </div>

                                <div class="d-flex justify-content-between align-items-center flex-wrap gap-2 mt-4">
                                    <a href="usuariosLista.html" class="btn btn-outline-secondary">
                                        <i class="bi bi-arrow-left"></i> Volver
                                    </a>

                                    <button type="submit" class="btn btn-success btn-lg">
                                        <i class="bi bi-check-circle"></i> Crear usuario
                                    </button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    `;

    const formulario = document.getElementById('formCrearUsuario');
    const mensaje = document.getElementById('mensaje');

    formulario.addEventListener('submit', async (e) => {
        e.preventDefault();

        const usuario = {
            nombre: document.getElementById('nombre').value,
            apellidos: document.getElementById('apellidos').value,
            edad: Number(document.getElementById('edad').value),
            dni: document.getElementById('dni').value,
            email: document.getElementById('email').value
        };

        try {
            await usuarioAPI.crearUsuario(usuario);

            mensaje.innerHTML = `
                <div class="alert alert-success mb-4">
                    <i class="bi bi-check-circle-fill"></i>
                    Usuario creado correctamente.
                </div>
            `;

            formulario.reset();
        } catch (error) {
            mensaje.innerHTML = `
                <div class="alert alert-danger mb-4">
                    <i class="bi bi-exclamation-triangle-fill"></i>
                    Error al crear el usuario.
                </div>
            `;
        }
    });
});