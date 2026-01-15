package com.proyecto.service;

import com.proyecto.entity.Jugadores;
import com.proyecto.repository.jugadoresRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class jugadoresService {

    @Autowired
    jugadoresRepository jugadoresRepository;

    // CREATE/UPDATE mismo metodo save
    public Jugadores guardarJugador(Jugadores jugador) {
        return jugadoresRepository.save(jugador);
    }

    // READ Leer todos
    public List<Jugadores> getJugadores() {
        return jugadoresRepository.findAll();
    }

    // READ por ID
    public Optional<Jugadores> obtenerJugadorPorId(int id) {
        return jugadoresRepository.findById(id);
    }

    // DELETE Borrar
    public void eliminarJugador(int id) {
        jugadoresRepository.deleteById(id);
    }




}
