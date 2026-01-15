package com.example.cronlimpieza.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "log_tareas")
public class LogTarea {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fechaFicticia;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;

    public LogTarea() {
    }

    public LogTarea(String fechaFicticia) {
        this.fechaFicticia = fechaFicticia;
        this.fechaCreacion = LocalDateTime.now();
    }

    public LogTarea(String fechaFicticia, LocalDateTime fechaCreacion) {
        this.fechaFicticia = fechaFicticia;
        this.fechaCreacion = fechaCreacion;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFechaFicticia() {
        return fechaFicticia;
    }

    public void setFechaFicticia(String fechaFicticia) {
        this.fechaFicticia = fechaFicticia;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
}
