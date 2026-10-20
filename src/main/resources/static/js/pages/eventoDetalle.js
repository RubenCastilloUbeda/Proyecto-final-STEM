import { eventoAPI } from "../api/eventoAPI.js";
import { crearBarraNavegacion } from "../components/navbar.js";

document.addEventListener("DOMContentLoaded", async () => {
    document.getElementById("navbar").innerHTML = crearBarraNavegacion();

    const parametros = new URLSearchParams(window.location.search);
    const idEvento = parametros.get("id");
    const root = document.getElementById("root");

    const evento = await eventoAPI.mostrarEventoId(idEvento);
    const resultados = await eventoAPI.mostrarResultadosEventoOrdenados(idEvento);

    document.title = `Evento - ${evento.nombre}`;

    let htmlResultados = "";

    resultados.forEach((resultado, index) => {
        htmlResultados += `
            <div class="resultado-card mb-3">
                <div class="d-flex justify-content-between align-items-center">
                    <div>
                        <span class="badge bg-dark mb-2">#${index + 1}</span>
                        <h5 class="mb-1">${resultado.equipo.nombre}</h5>
                        <p class="text-muted mb-0">
                            <i class="bi bi-trophy"></i> ${resultado.puntuacion} puntos
                        </p>
                    </div>

                    <div class="resultado-icono">
                        ${index === 0 ? "🥇" : index === 1 ? "🥈" : index === 2 ? "🥉" : "💪"}
                    </div>
                </div>
            </div>
        `;
    });

    root.innerHTML = `
        <div class="container py-4">

            <div class="evento-hero p-4 p-md-5 mb-4 text-white">
                <div class="d-flex justify-content-between align-items-start flex-wrap gap-3">
                    <div>
                        <span class="badge bg-warning text-dark mb-3">
                            <i class="bi bi-lightning-charge-fill"></i> Evento CrossFit
                        </span>

                        <h1 class="display-5 fw-bold">${evento.nombre}</h1>

                        <p class="mb-0 opacity-75">
                            <i class="bi bi-trophy-fill"></i>
                            ${evento.competicion ? evento.competicion.nombre : "Sin competición"}
                        </p>
                    </div>

                    <div class="d-flex gap-2 flex-wrap">
                        <a href="eventosLista.html" class="btn btn-light">
                            <i class="bi bi-arrow-left"></i> Volver
                        </a>

                        <button id="btnEditarEvento" class="btn btn-warning">
                            <i class="bi bi-pencil-square"></i> Editar
                        </button>

                        <button id="btnAnadirResultado" class="btn btn-success">
                            <i class="bi bi-plus-circle"></i> Añadir resultado
                        </button>

                        <button id="btnEliminarEvento" class="btn btn-outline-danger bg-white">
                            <i class="bi bi-trash"></i> Eliminar
                        </button>
                    </div>
                </div>
            </div>

            <div id="contenedorEditarEvento" class="mb-4 d-none">
                <div class="card shadow-sm border-0 form-card">
                    <div class="card-body p-4">
                        <h3 class="mb-3">
                            <i class="bi bi-pencil-square text-warning"></i>
                            Editar evento
                        </h3>

                        <form id="formEditarEvento">
                            <div class="mb-3">
                                <label class="form-label">Nombre</label>
                                <input id="editarNombre" class="form-control" value="${evento.nombre}">
                            </div>

                            <div class="mb-3">
                                <label class="form-label">Duración</label>
                                <input type="number" id="editarDuracion" class="form-control" value="${evento.duracion}">
                            </div>

                            <button class="btn btn-primary">
                                <i class="bi bi-check-circle"></i> Guardar cambios
                            </button>
                        </form>
                    </div>
                </div>
            </div>

            <div id="contenedorAnadirResultado" class="mb-4 d-none">
                <div class="card shadow-sm border-0 form-card">
                    <div class="card-body p-4">
                        <h3 class="mb-3">
                            <i class="bi bi-plus-circle text-success"></i>
                            Añadir resultado
                        </h3>

                        <form id="formAnadirResultado">
                            <div class="mb-3">
                                <label class="form-label">ID del equipo</label>
                                <input type="number" id="idEquipoResultado" class="form-control" required>
                            </div>

                            <div class="mb-3">
                                <label class="form-label">Puntuación</label>
                                <input type="number" id="puntuacionResultado" class="form-control" required>
                            </div>

                            <button class="btn btn-success">
                                <i class="bi bi-check-circle"></i> Añadir resultado
                            </button>
                        </form>
                    </div>
                </div>
            </div>

            <div class="row g-4 mb-4">
                <div class="col-md-4">
                    <div class="info-card text-center">
                        <i class="bi bi-stopwatch fs-2 text-primary"></i>
                        <h6 class="mt-2 text-muted">Duración</h6>
                        <h4>${evento.duracion} min</h4>
                    </div>
                </div>

                <div class="col-md-4">
                    <div class="info-card text-center">
                        <i class="bi bi-people-fill fs-2 text-success"></i>
                        <h6 class="mt-2 text-muted">Participantes</h6>
                        <h4>${resultados.length} equipos</h4>
                    </div>
                </div>

                <div class="col-md-4">
                    <div class="info-card text-center">
                        <i class="bi bi-award-fill fs-2 text-warning"></i>
                        <h6 class="mt-2 text-muted">Mejor puntuación</h6>
                        <h4>${resultados.length > 0 ? resultados[0].puntuacion : 0}</h4>
                    </div>
                </div>
            </div>

            <div class="card shadow-sm border-0">
                <div class="card-body p-4">
                    <div class="d-flex justify-content-between align-items-center mb-3">
                        <h2 class="h4 mb-0">
                            <i class="bi bi-list-ol text-primary"></i>
                            Resultados del evento
                        </h2>

                        <button class="btn btn-outline-primary btn-sm"
                                type="button"
                                data-bs-toggle="collapse"
                                data-bs-target="#resultadosCollapse"
                                aria-expanded="false">
                            Ver resultados
                        </button>
                    </div>

                    <div class="collapse" id="resultadosCollapse">
                        <div class="mt-3">
                            ${htmlResultados || '<p class="text-muted mb-0">Este evento todavía no tiene resultados.</p>'}
                        </div>
                    </div>
                </div>
            </div>
        </div>
    `;

    document.getElementById("btnEditarEvento").addEventListener("click", () => {
        document.getElementById("contenedorEditarEvento").classList.toggle("d-none");
    });

    document.getElementById("btnAnadirResultado").addEventListener("click", () => {
        document.getElementById("contenedorAnadirResultado").classList.toggle("d-none");
    });

    document.getElementById("btnEliminarEvento").addEventListener("click", async () => {
        const confirmar = confirm("¿Seguro que quieres eliminar este evento?");
        if (!confirmar) return;

        await eventoAPI.eliminarEvento(idEvento);
        root.innerHTML = `
            <div class="container mt-5">
                <div class="alert alert-success"> Evento eliminado correctamente. </div>
                <a href="eventosLista.html" class="btn btn-primary"> Volver a eventos </a>
            </div>
        `;
    });

    document.getElementById("formEditarEvento").addEventListener("submit", async (e) => {
        e.preventDefault();

        const eventoActualizado = {
            nombre: document.getElementById("editarNombre").value,
            duracion: Number(document.getElementById("editarDuracion").value)
        };

        await eventoAPI.modificarEvento(idEvento, eventoActualizado);
        location.reload();
    });

    document.getElementById("formAnadirResultado").addEventListener("submit", async (e) => {
        e.preventDefault();

        const resultado = {
            puntuacion: Number(document.getElementById("puntuacionResultado").value),
            equipo: {
                id: Number(document.getElementById("idEquipoResultado").value)
            }
        };

        await eventoAPI.anadirResultadoEvento(idEvento, resultado);
        location.reload();
    });

    const btnResultados = document.querySelector('[data-bs-target="#resultadosCollapse"]');
    const collapse = document.getElementById("resultadosCollapse");

    collapse.addEventListener("show.bs.collapse", () => {
        btnResultados.textContent = "Ocultar resultados";
    });

    collapse.addEventListener("hide.bs.collapse", () => {
        btnResultados.textContent = "Ver resultados";
    });
});