package com.example.cronlimpieza.repository;

import com.example.cronlimpieza.model.LogTarea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface LogTareaRepository extends JpaRepository<LogTarea, Long> {

    List<LogTarea> findByFechaCreacionBefore(LocalDateTime fecha);
}

