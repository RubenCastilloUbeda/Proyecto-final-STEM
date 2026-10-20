package app.CrosStemFit.CrosStemFit.config;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.javafaker.Faker;

import app.CrosStemFit.CrosStemFit.entity.Clasificacion;
import app.CrosStemFit.CrosStemFit.entity.Competicion;
import app.CrosStemFit.CrosStemFit.entity.Equipo;
import app.CrosStemFit.CrosStemFit.entity.Evento;
import app.CrosStemFit.CrosStemFit.entity.Resultado;
import app.CrosStemFit.CrosStemFit.entity.Usuario;
import app.CrosStemFit.CrosStemFit.repository.ClasificacionRepository;
import app.CrosStemFit.CrosStemFit.repository.CompeticionRepository;
import app.CrosStemFit.CrosStemFit.repository.EquipoRepository;
import app.CrosStemFit.CrosStemFit.repository.EventoRepository;
import app.CrosStemFit.CrosStemFit.repository.ResultadoRepository;
import app.CrosStemFit.CrosStemFit.repository.UsuarioRepository;

@Configuration
public class DataLoader {

        @Bean
        CommandLineRunner cargarDatos(
                        UsuarioRepository usuarioRepository,
                        EquipoRepository equipoRepository,
                        CompeticionRepository competicionRepository,
                        EventoRepository eventoRepository,
                        ResultadoRepository resultadoRepository,
                        ClasificacionRepository clasificacionRepository) {
                return args -> {

                        Faker faker = new Faker();
                        Random random = new Random();

                        List<Usuario> usuarios = new ArrayList<>();

                        // 1. Crear 50 usuarios
                        for (int i = 1; i <= 50; i++) {
                                Usuario usuario = new Usuario();

                                usuario.setNombre(faker.name().firstName());
                                usuario.setApellidos(faker.name().lastName());
                                usuario.setDni("000000" + i + "A");
                                usuario.setEdad(18 + random.nextInt(20));
                                usuario.setEmail("usuario" + i + "@gmail.com");

                                usuarios.add(usuarioRepository.save(usuario));
                        }

                        List<Equipo> equipos = new ArrayList<>();

                        // 2. Crear 60 equipos de 2 personas
                        int indiceUsuario = 0;

                        for (int i = 1; i <= 60; i++) {
                                Equipo equipo = new Equipo();

                                equipo.setNombre("Equipo " + i);
                                equipo.setMaxMiembros(2);

                                equipo.getMiembrosEquipo().add(usuarios.get(indiceUsuario % usuarios.size()));
                                equipo.getMiembrosEquipo().add(usuarios.get((indiceUsuario + 1) % usuarios.size()));

                                indiceUsuario += 2;

                                equipos.add(equipoRepository.save(equipo));
                        }

                        String[] nombresEventos = {
                                        "Fran", "Murph", "Grace", "Helen", "Cindy",
                                        "Diane", "Annie", "Jackie", "Karen", "Nancy",
                                        "Fight Gone Bad", "Open 24.1", "Open 24.2", "Open 24.3",
                                        "Clean Ladder", "Snatch Complex", "Deadlift Sprint",
                                        "Burpee Challenge", "Row and Run", "Box Jump Madness",
                                        "Thruster Test", "Pull Up Battle", "Double Under Race",
                                        "Wall Ball Test", "Final WOD"
                        };

                        // 3. Crear 3 competiciones
                        for (int i = 1; i <= 3; i++) {

                                Competicion competicion = new Competicion();

                                competicion.setNombre("Competicion CrossFit " + i);
                                competicion.setPrecio(25.0);
                                competicion.setMaxEquipos(20);
                                competicion.setFechaInicio(LocalDate.now().plusDays(i * 10));
                                competicion.setFechaFin(LocalDate.now().plusDays(i * 10));
                                competicion.setLocalizacion("Granada");
                                competicion.setDireccion("Calle CrossFit " + i);

                                // 4. Crear 5 eventos para esta competición
                                List<Evento> eventosCompeticion = new ArrayList<>();

                                for (int j = 0; j < 5; j++) {
                                        Evento evento = new Evento();

                                        evento.setNombre(nombresEventos[((i - 1) * 5) + j]);
                                        evento.setDuracion(10 + random.nextInt(20));
                                        evento.setCompeticion(competicion);

                                        eventosCompeticion.add(evento);
                                }

                                competicion.setEventos(eventosCompeticion);

                                // Guardamos competición con sus eventos
                                competicionRepository.save(competicion);

                                // 5. Asignar 20 equipos a esta competición
                                List<Equipo> equiposCompeticion = new ArrayList<>();

                                for (int j = 0; j < 15; j++) {
                                        Equipo equipo = equipos.get(((i - 1) * 15) + j);

                                        equipo.getRegistroCompeticiones().add(competicion);
                                        equipoRepository.save(equipo);

                                        equiposCompeticion.add(equipo);
                                }

                                // 6. Crear resultados de cada equipo en cada evento
                                for (Evento evento : eventosCompeticion) {
                                        for (Equipo equipo : equiposCompeticion) {

                                                Resultado resultado = new Resultado();

                                                resultado.setEvento(evento);
                                                resultado.setEquipo(equipo);
                                                resultado.setPuntuacion(50 + random.nextInt(51));

                                                resultadoRepository.save(resultado);
                                        }
                                }

                                // 7. Crear clasificación de esta competición
                                List<Clasificacion> clasificaciones = new ArrayList<>();

                                for (Equipo equipo : equiposCompeticion) {

                                        int puntosTotales = 0;

                                        for (Evento evento : eventosCompeticion) {

                                                Resultado resultado = new Resultado();

                                                resultado.setEvento(evento);
                                                resultado.setEquipo(equipo);

                                                int puntuacion = 50 + random.nextInt(51);
                                                resultado.setPuntuacion(puntuacion);

                                                resultadoRepository.save(resultado);

                                                puntosTotales += puntuacion;
                                        }

                                        Clasificacion clasificacion = new Clasificacion();

                                        clasificacion.setEquipo(equipo);
                                        clasificacion.setCompeticion(competicion);
                                        clasificacion.setPuntosTotales(puntosTotales);

                                        clasificaciones.add(clasificacion);
                                }

                                clasificaciones.sort((c1, c2) -> Integer.compare(c2.getPuntosTotales(),
                                                c1.getPuntosTotales()));

                                int posicion = 1;

                                for (Clasificacion clasificacion : clasificaciones) {
                                        clasificacion.setPosicion(posicion);
                                        posicion++;
                                }

                                clasificacionRepository.saveAll(clasificaciones);
                        }

                        System.out.println("================ DATOS CARGADOS CORRECTAMENTE ===============");
                };
        }
}