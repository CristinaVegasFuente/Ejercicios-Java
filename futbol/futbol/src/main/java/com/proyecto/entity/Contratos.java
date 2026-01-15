package com.proyecto.entity;

import jakarta.persistence.*;

import java.time.LocalDate;


@Entity
@Table(name = "contratos1")
public class Contratos{
    
    @Id
    private int id_jugador;

    private int id_equipo;

    private LocalDate fecha_inicio;

    private LocalDate fecha_fin;

    public Contratos() {
    }

    public Contratos(int id_jugador, int id_equipo, LocalDate fecha_inicio, LocalDate fecha_fin) {
        this.id_jugador = id_jugador;
        this.id_equipo = id_equipo;
        this.fecha_inicio = fecha_inicio;
        this.fecha_fin = fecha_fin;
    }

    public int getId_jugador() {
        return id_jugador;
    }

    public void setId_jugador(int id_jugador) {
        this.id_jugador = id_jugador;
    }

    public int getId_equipo() {
        return id_equipo;
    }

    public void setId_equipo(int id_equipo) {
        this.id_equipo = id_equipo;
    }

    public LocalDate getFecha_inicio() {
        return fecha_inicio;
    }

    public void setFecha_inicio(LocalDate fecha_inicio) {
        this.fecha_inicio = fecha_inicio;
    }

    public LocalDate getFecha_fin() {
        return fecha_fin;
    }

    public void setFecha_fin(LocalDate fecha_fin) {
        this.fecha_fin = fecha_fin;
    }

    @Override
    public String toString() {
        return "Contratos{" +
                "id_jugador=" + id_jugador +
                ", id_equipo=" + id_equipo +
                ", fecha_inicio=" + fecha_inicio +
                ", fecha_fin=" + fecha_fin +
                '}';
    }
}

