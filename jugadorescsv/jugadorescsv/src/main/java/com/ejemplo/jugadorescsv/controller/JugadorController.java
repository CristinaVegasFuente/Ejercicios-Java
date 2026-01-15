package com.ejemplo.jugadorescsv.controller;

import com.ejemplo.jugadorescsv.model.Jugador;
import com.ejemplo.jugadorescsv.repository.JugadorRepository;
import com.ejemplo.jugadorescsv.service.ImportResult;
import com.ejemplo.jugadorescsv.service.JugadorService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/jugadores")
public class JugadorController {

    @Autowired
    private JugadorRepository jugadorRepository;

    @Autowired
    private JugadorService jugadorService;

    //listar todos
    //GET /jugadores
    @GetMapping
    @Operation(summary = "Listar todos los jugadores")
    public List<Jugador> listar() {
        return jugadorRepository.findAll();
    }

    //buscar por id
    //GET /jugadores/{id}
    @GetMapping("/{id}")
    @Operation(summary = "Listar jugador por id")
    public ResponseEntity<Jugador> buscarPorId(@PathVariable Long id) {
        Optional<Jugador> jugador = jugadorRepository.findById(id);
        return jugador.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //crear un jugador nuevo
    //POST /jugadores
    @PostMapping
    @Operation(summary = "Crear jugador")
    public Jugador crear(@RequestBody Jugador jugador) {
        return jugadorRepository.save(jugador);
    }

    //actualizar un jugador por id
    //PUT /jugadores/{id}
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar jugador por id")
    public ResponseEntity<Jugador> actualizar(@PathVariable Long id, @RequestBody Jugador datos) {
        return jugadorRepository.findById(id)
                .map(jugador -> {
                    jugador.setNombre(datos.getNombre());
                    jugador.setDni(datos.getDni());
                    jugador.setNacionalidad(datos.getNacionalidad());
                    jugador.setFechaNacimiento(datos.getFechaNacimiento());
                    return ResponseEntity.ok(jugadorRepository.save(jugador));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    //eliminar un jugador por id
    //DELETE /jugadores/{id}
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar jugador por id")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (jugadorRepository.existsById(id)) {
            jugadorRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    //importa el CSV desde postman
    //POST /jugadores/import (form-data: file=<archivo.csv>)
    @PostMapping("/import")
    @Operation(summary = "Importar CSV")
    public ResponseEntity<ImportResult> importarCsv(@RequestParam("file") MultipartFile file) {
        ImportResult result = jugadorService.importarCsv(file);
        // 200 OK o 400 para errorres
        if (result.getTotalFilas() == 0 && !result.getErrores().isEmpty()) {
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.ok(result);
    }
}
