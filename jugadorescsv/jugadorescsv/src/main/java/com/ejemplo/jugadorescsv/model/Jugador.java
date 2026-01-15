package com.ejemplo.jugadorescsv.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDate;

//anotaciones de JPA (@Entity, @Id, etc.) sirven para conectar la clase con la base de datos
//@Entity: convierte la clase en una tabla de base de datos
@Entity
@Data
public class Jugador {

    //@Id indica que este campo es la clave primaria
    @Id
    private Long id;
    //Resto de atributos
    private String nombre;
    private LocalDate fechaNacimiento;
    private String nacionalidad;
    private String dni;

}
