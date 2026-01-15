package com.proyecto.service;

import com.proyecto.entity.Equipos;
import com.proyecto.repository.equiposRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class equiposService { // Corregido: Se usa la convención PascalCase

    @Autowired
    // Corregido: Se usa la convención PascalCase
    equiposRepository equiposRepository;

    // CREATE/UPDATE
    public Equipos guardarEquipo(Equipos equipos) {
        return equiposRepository.save(equipos);
    }

    // READ todos
    public List<Equipos> getEquipos() {
        return equiposRepository.findAll();
    }

    // READ por ID
    public Optional<Equipos> obtenerEquipoPorId(int id) {
        return equiposRepository.findById(id);
    }

    // DELETE Borrar
    public void eliminarEquipo(int id) {
        equiposRepository.deleteById(id);
    }


}
