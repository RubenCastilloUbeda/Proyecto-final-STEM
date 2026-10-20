export function crearElementoUsuario(usuario) {
    return `
        <a href="usuarioDetalle.html?id=${usuario.id_usuario}" class="card text-decoration-none text-dark mb-3">
            <div class="card-body">
                <h5 class="card-title">${usuario.nombre} ${usuario.apellidos} </h5>
                <p class="card-text text-muted">Documento identificativo: ${usuario.dni} </p>
                <p class="card-text text-muted">Edad: ${usuario.edad} </p>
                <p class="card-text text-muted">Email: ${usuario.email} </p>
            </div>
        </a>`;
}