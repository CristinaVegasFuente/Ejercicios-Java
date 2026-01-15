package com.ejemplo.perfiles_db.repository;

import com.ejemplo.perfiles_db.model.Tarea;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TareaRepository extends JpaRepository<Tarea, Long>{

    // Buscar todas las tareas de un usuario concreto
    List<Tarea> findByUsuarioId(Long usuarioId);
}
