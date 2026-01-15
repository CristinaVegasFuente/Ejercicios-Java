package com.proyecto.entity;

import jakarta.persistence.*;

import java.time.LocalDate;


@Entity
@Table(name = "jugadores1")
public class Jugadores {

    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nombre;

    private LocalDate fecha_nacimiento;

    private String nacionalidad;

    private String dni;

    private String pais;


    public Jugadores() {
    }

    public Jugadores(int id, String nombre, LocalDate fecha_nacimiento, String pais, String dni) {
        this.id = id;
        this.nombre = nombre;
        this.fecha_nacimiento = fecha_nacimiento;
        this.nacionalidad = nacionalidad;
        this.dni = dni;
        this.pais = pais;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalDate getFecha_nacimiento() {
        return fecha_nacimiento;
    }

    public void setFecha_nacimiento(LocalDate fecha_nacimiento) {
        this.fecha_nacimiento = fecha_nacimiento;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    @Override
    public String toString() {
        return "Jugadores{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", fecha_nacimiento=" + fecha_nacimiento +
                ", nacionalidad='" + nacionalidad + '\'' +
                ", dni='" + dni + '\'' +
                ", pais='" + pais + '\'' +
                '}';
    }
}
