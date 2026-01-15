package com.ejemplo.jugadorescsv.repository;

import com.ejemplo.jugadorescsv.model.Contrato;
import com.ejemplo.jugadorescsv.model.ContratoId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

//clave compuesta (@EmbeddedId)
//Esta interfaz le dice a Spring Boot cómo acceder a los datos de la tabla contrato en la base de datos
@Repository

//metodo (findById_IdEquipo) para buscar por una parte de la clave compuesta
//Cuando una tabla tiene más de una columna como clave primaria, se usa una clase adicional llamada ContratoId en este caso
public interface ContratoRepository extends JpaRepository<Contrato, ContratoId> {
    List<Contrato> findById_IdEquipo(int idEquipo);
}
/*
findBy es la búsqueda
Id se refiere al campo id de la clase Contrato
_IdJugador dentro de id, el campo idJugador
es lo mismo que: SELECT * FROM contrato WHERE id_jugador = ?;
 */