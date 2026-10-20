import { competicionAPI } from '../api/competicionAPI.js';
import { crearBarraNavegacion } from '../components/navbar.js';

document.addEventListener('DOMContentLoaded', async () => {
    document.getElementById('navbar').innerHTML = crearBarraNavegacion();
    const root = document.getElementById('root');
    
    const competiciones = await competicionAPI.mostrarTodasCompeticiones();
    
    let htmlCompeticiones = '';
    for (const competicion of competiciones) {
        htmlCompeticiones += `
            <div class="col-md-6 col-lg-4 mb-4">
                <a href="competicionDetalle.html?id=${competicion.idCompeticion}" 
                    class="text-decoration-none">
                    <div class="card h-100 tarjeta-competicion">
                        <div class="card-body d-flex flex-column">
                            <h5 class="card-title text-primary fw-bold"> ${competicion.nombre} </h5>
                            <p class="card-text mt-2"> <strong>💰 Precio:</strong> ${competicion.precio} € </p>
                            <p class="card-text"> <strong>👥 Equipos:</strong> ${competicion.maxEquipos} </p>
                            <p class="card-text text-muted mt-auto"> 📍 ${competicion.localizacion} </p>
                        </div>
                    </div>
                </a>
            </div>
        `;
    }

    root.innerHTML = `
    <div class="container py-5">
        <div class="home-hero p-5 text-white text-center">
            <h1 class="display-4 fw-bold mb-3"> CrosStemFit </h1>
            <p class="mb-4 opacity-75"> Gestiona competiciones, eventos y equipos de CrossFit </p>
            <div class="d-flex justify-content-center gap-3 flex-wrap">
                <a href="crearCompeticion.html" class="btn btn-warning btn-lg"> <i class="bi bi-plus-circle"></i> Crear competición</a>
            </div>
        </div>
    </div>
    
    <div class="container mt-4">
        <h1 class="mb-4 text-center">🏆 Competiciones</h1>
        <div class="row"> ${htmlCompeticiones} </div>
    </div>
    `;
});