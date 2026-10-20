package app.CrosStemFit.CrosStemFit.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import app.CrosStemFit.CrosStemFit.entity.Usuario;


public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
    Optional<Usuario> findByDni(String dni);
    Optional<Usuario> findByEmail(String email);
    Optional<Usuario> deleteByEmail(String email);
}
