package com.proyecto.controller;

import com.proyecto.entity.Jugadores;
import com.proyecto.service.jugadoresService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

//listo para recibir pedidos HTTP y devolver respuestas JSON
@RestController
@RequestMapping("/jugadores")
public class jugadoresController {

    private final jugadoresService jugadoresService;

    //Inyección de Dependencias
    //conecta con el controller con el service
    @Autowired
    public jugadoresController(jugadoresService jugadoresService) {
        this.jugadoresService = jugadoresService;
    }

    //CREATE un nuevo jugador
    @PostMapping
    public Jugadores crearJugador(@RequestBody Jugadores jugador) {
        return jugadoresService.guardarJugador(jugador);
    }

    //READ todos los jugadores
    @GetMapping
    public List<Jugadores> getAll() {
        return jugadoresService.getJugadores();
    }

    //READ un jugador por ID
    @GetMapping("/{id}")
    public ResponseEntity<Jugadores> obtenerPorId(@PathVariable int id) {
        Optional<Jugadores> jugador = jugadoresService.obtenerJugadorPorId(id);
        return jugador.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    //UPDATE un jugador
    @PutMapping("/{id}")
    public ResponseEntity<Jugadores> actualizarJugador(@PathVariable int id, @RequestBody Jugadores detallesJugador) {
        Optional<Jugadores> jugadorExistente = jugadoresService.obtenerJugadorPorId(id);
        //busca el jugador existente por ID
        //si lo encuentra, actualiza sus campos con los nuevos datos
        if (jugadorExistente.isPresent()) {
            //código para copiar los nuevos datos al objeto existente
            Jugadores jugadorActualizado = jugadorExistente.get();
            jugadorActualizado.setNombre(detallesJugador.getNombre());
            jugadorActualizado.setNacionalidad(detallesJugador.getNacionalidad());

            final Jugadores resultado = jugadoresService.guardarJugador(jugadorActualizado);
            return ResponseEntity.ok(resultado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //DELETE un jugador
    @DeleteMapping("/{id}")
    //llama al servicio (jugadoresService.eliminarJugador(id);) para ejecutar el borrado en la base de datos
    public ResponseEntity<Void> eliminarJugador(@PathVariable int id) {
        Optional<Jugadores> jugadorExistente = jugadoresService.obtenerJugadorPorId(id);
        if (jugadorExistente.isPresent()) {
            jugadoresService.eliminarJugador(id);
            //Si el borrado es ok devuelve un el estado HTTP 204
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}


