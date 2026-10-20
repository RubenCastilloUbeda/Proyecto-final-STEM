package app.CrosStemFit.CrosStemFit.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import app.CrosStemFit.CrosStemFit.entity.Competicion;

public interface CompeticionRepository extends JpaRepository<Competicion, Long>{
    public Optional<Competicion> findByNombre(String nombre);
    public Competicion deleteByNombre(String nombre);
}
