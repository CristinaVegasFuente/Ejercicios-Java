package com.example.cronlimpieza.repository;

import com.example.cronlimpieza.model.EjecucionTarea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EjecucionTareaRepository extends JpaRepository<EjecucionTarea, Long> {
}
