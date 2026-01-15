package com.proyecto.repository;

import com.proyecto.entity.Jugadores;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface jugadoresRepository extends JpaRepository<Jugadores, Integer> {

}

