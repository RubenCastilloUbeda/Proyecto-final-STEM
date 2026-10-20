export function crearBarraNavegacion() {
    return `
    <nav class="navbar navbar-expand-lg navbar-dark shadow-lg"
        style="background: linear-gradient(90deg, #111, #333);">

        <div class="container">
            <a class="navbar-brand fw-bold" href="index.html"> CrosStemFit </a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav ms-auto align-items-center">
                    <li class="nav-item">
                        <a class="nav-link" href="eventosLista.html">
                            <i class="bi bi-list-task"></i> Eventos
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="equiposLista.html">
                            <i class="bi bi-people"></i> Equipos
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="usuariosLista.html">
                            <i class="bi bi-people"></i> Usuarios
                        </a>
                    </li>
                </ul>
            </div>
        </div>
    </nav>
    `;
}