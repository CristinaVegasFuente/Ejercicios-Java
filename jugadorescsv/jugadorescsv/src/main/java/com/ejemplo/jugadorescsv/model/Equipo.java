package com.ejemplo.jugadorescsv.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

//indica que esta clase es una entidad JPA, es decir, representa una tabla en la base de datos
@Entity

@Data
public class Equipo {
    //@Id marca este atributo como clave primaria de la tabla
    //Aquí no se usa @GeneratedValue porque el identificador viene directamente del CSV
    @Id
    private int id;

    //Resto de atributos
    private String nombre;
    private String pais;
    private int fundacion;
    private String cif;

    //Java genera automáticamente un constructor vacío, que es necesario para que JPA pueda
    //crear objetos de esta clase al recuperar datos de la base de datos o importar el CSV

}



