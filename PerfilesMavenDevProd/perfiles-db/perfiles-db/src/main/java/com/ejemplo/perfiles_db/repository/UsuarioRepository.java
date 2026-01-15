package com.ejemplo.perfiles_db.repository;

import com.ejemplo.perfiles_db.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}
