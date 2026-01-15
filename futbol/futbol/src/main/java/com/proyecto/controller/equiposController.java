package com.proyecto.controller;

import com.proyecto.entity.Equipos;
import com.proyecto.service.equiposService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/equipos")
public class equiposController {

    // Se recomienda usar la interfaz en lugar de la clase de implementación
    private final equiposService equiposService;

    // Inyección de dependencias (se recomienda inyección por constructor)
    public equiposController(equiposService equiposService) {
        this.equiposService = equiposService;
    }

    // CREATE un nuevo equipo
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // Opcional, pero recomendado para el estándar REST
    public Equipos crearEquipo(@RequestBody Equipos equipo) {
        return equiposService.guardarEquipo(equipo);
    }

    // READ todos los equipos
    @GetMapping
    public List<Equipos> getAll() {
        return equiposService.getEquipos();
    }

    // READ un equipo por ID
    @GetMapping("/{id}")
    public ResponseEntity<Equipos> obtenerPorId(@PathVariable int id) {
        // CORREGIDO: Se debe llamar al servicio de equipos, no al de jugadores
        Optional<Equipos> equipo = equiposService.obtenerEquipoPorId(id);
        return equipo.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // UPDATE un equipo
    @PutMapping("/{id}")
    public ResponseEntity<Equipos> actualizarEquipo(@PathVariable int id, @RequestBody Equipos detallesEquipo) {
        Optional<Equipos> equipoExistente = equiposService.obtenerEquipoPorId(id);

        if (equipoExistente.isPresent()) {
            Equipos equipoActualizado = equipoExistente.get();

            // CORREGIDO: Aquí se actualizan los campos
            equipoActualizado.setNombre(detallesEquipo.getNombre());
            equipoActualizado.setPais(detallesEquipo.getPais());

            // CORREGIDO: Se llama al metodo para guardar el equipo
            Equipos resultado = equiposService.guardarEquipo(equipoActualizado);
            return ResponseEntity.ok(resultado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE un equipo
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarEquipo(@PathVariable int id) {
        Optional<Equipos> equipoExistente = equiposService.obtenerEquipoPorId(id);

        if (equipoExistente.isPresent()) {
            equiposService.eliminarEquipo(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}
