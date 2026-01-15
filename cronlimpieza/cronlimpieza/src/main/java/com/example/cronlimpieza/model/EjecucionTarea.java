package com.example.cronlimpieza.model;

import jakarta.persistence.*;
import org.hibernate.annotations.AnyDiscriminatorImplicitValues;

import java.time.LocalDateTime;

@Entity
@Table(name = ("ejecuciones_tareas"))
public class EjecucionTarea {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private Long registrosBorrados;
    private String estado;
    private String mensaje;

    public EjecucionTarea() {
    }

    //solo este constructor, porque cuando el cron comienza solo sabemos la fecha de inicio, el resto lo rellenamos despues
    public EjecucionTarea(LocalDateTime fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDateTime fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDateTime getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDateTime fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Long getRegistrosBorrados() {
        return registrosBorrados;
    }

    public void setRegistrosBorrados(Long registrosBorrados) {
        this.registrosBorrados = registrosBorrados;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
