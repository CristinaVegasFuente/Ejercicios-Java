package com.ejemplo.jugadorescsv.model;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
//Esta clase representa la tabla contratos, que actuará como una tabla intermedia entre jugador y equipo
@Table(name = "contratos")
public class Contrato {

    //@EmbeddedId indica que la clave primaria de esta entidad es un objeto (no un simple número)
    @EmbeddedId
    private ContratoId id;  //no tiene id por si mismo y en la salida por localhost tiene que salir null o vacio

    @Column(name = "dorsal")
    private int dorsal;

    public Contrato() {}

}

