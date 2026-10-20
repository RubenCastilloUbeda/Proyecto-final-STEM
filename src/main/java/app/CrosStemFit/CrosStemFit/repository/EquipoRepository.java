package app.CrosStemFit.CrosStemFit.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import app.CrosStemFit.CrosStemFit.entity.Equipo;

@Repository
public interface EquipoRepository extends JpaRepository<Equipo, Long> {
    Optional<Equipo> findByNombre(String nombreEquipo);
    void deleteByNombre(String nombreEquipo);
}
