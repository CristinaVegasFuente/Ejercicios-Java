package com.ejemplo.jugadorescsv.repository;

import com.ejemplo.jugadorescsv.model.Jugador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//Esta interfaz ya trae los métodos CRUD listos (Create, Read, Update, Delete)

//@Repository indica que es un componente de acceso a datos
@Repository
public interface JugadorRepository extends JpaRepository<Jugador, Long> {
    //extends JpaRepository<Equipo, Integer>
        //Equipo es el tipo de la entidad.
        //Integer es el tipo del campo @Id.
}

/*
hereda:
equipoRepository.findAll();      // Lista todos los equipos
equipoRepository.findById(1);    // Busca el equipo con id=1
equipoRepository.save(equipo);   // Inserta o actualiza un equipo
equipoRepository.deleteById(3);  // Elimina el equipo con id=3
 */