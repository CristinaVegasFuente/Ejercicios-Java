package com.proyecto.repository;

import com.proyecto.entity.Equipos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface equiposRepository extends JpaRepository<Equipos, Integer> {

}