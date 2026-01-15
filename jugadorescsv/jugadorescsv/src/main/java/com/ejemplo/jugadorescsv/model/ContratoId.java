package com.ejemplo.jugadorescsv.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

//clave primaria compuesta
//esta clase no es una entidad por sí misma sino un objeto que se puede incrustar dentro de otra entidad (Contrato en este caso)
@Embeddable
@Data
//debe implementar Serializable
//debe sobrescribir equals() y hashCode(), para que JPA pueda comparar correctamente las claves
public class ContratoId implements Serializable {

    @Column(name = "id_jugador")
    private int idJugador;

    @Column(name = "id_equipo")
    private int idEquipo;

    @Column(name = "fecha_inicio")
    private LocalDate fechaInicio;

    @Column(name = "fecha_fin")
    private LocalDate fechaFin;

    public ContratoId() {
    }

    public ContratoId(int idJugador, int idEquipo, LocalDate fechaInicio, LocalDate fechaFin) {
        this.idJugador = idJugador;
        this.idEquipo = idEquipo;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    //Esta parte del código es fundamental cuando se usan claves compuestas (@EmbeddedId) en JPA
    //Si no tuvieras equals() y hashCode() entonces JPA no sabría reconocer si dos objetos ContratoId son la misma clave o no
        @Override
    //equals() compara si tienen los mismos valores
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ContratoId that = (ContratoId) o;
        return idJugador == that.idJugador && idEquipo == that.idEquipo && Objects.equals(fechaInicio, that.fechaInicio) && Objects.equals(fechaFin, that.fechaFin);
    }

    //hashCode() es necesario para que los objetos también funcionen correctamente en estructuras de
    //datos como HashSet o HashMap, que usan “códigos de hash” para comparar objetos más rápido
    //ademas permite que las claves se usen en estructuras tipo HashMap o por JPA internamente
    @Override
    public int hashCode() {
        return Objects.hash(idJugador, idEquipo, fechaInicio, fechaFin);
    }

}
