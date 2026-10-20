package app.CrosStemFit.CrosStemFit.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import app.CrosStemFit.CrosStemFit.entity.Evento;

public interface EventoRepository extends JpaRepository<Evento, Long>{
    Optional<Evento> findByNombre(String nombreEvento);
    void deleteByNombre(String nombreEvento);
}
