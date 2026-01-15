package com.ejemplo.jugadorescsv.controller;

import com.ejemplo.jugadorescsv.model.Equipo;
import com.ejemplo.jugadorescsv.repository.EquipoRepository;
import com.ejemplo.jugadorescsv.service.EquipoService;
import com.ejemplo.jugadorescsv.service.ImportResult;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/equipos")
public class EquipoController {

    @Autowired
    private EquipoRepository equipoRepository;

    @Autowired
    private EquipoService equipoService;

    //lista todos los equipos
    //GET http://localhost:8080/equipos
    @GetMapping
    @Operation(summary = "Listar todos los equipos")
    public List<Equipo> listar() {
        //findAll() hace internamente un SELECT * FROM equipos;
        return equipoRepository.findAll();
    }

    //busca un equipo por su ID
    @GetMapping("/{id}")
    @Operation(summary = "Listar equipos por id")
    public ResponseEntity<Equipo> buscarPorId(@PathVariable int id) {
        //findById(id) busca en la base de datos (SELECT * FROM equipos WHERE id = ?)
        Optional<Equipo> equipo = equipoRepository.findById(id);
        //si encuentra el equipo lo envuelve en un ResponseEntity.ok(equipo) 200 OK.
        return equipo.map(ResponseEntity::ok)
                //Si no lo encuentra devuelve ResponseEntity.notFound() 404 Not Found
                .orElse(ResponseEntity.notFound().build());
    }

    //Crear nuevo equipo
    //Crea un nuevo registro en la base de datos a partir del JSON recibido
    //POST http://localhost:8080/equipos
    @PostMapping
    @Operation(summary = "Crear equipo")
    //recibe el JSON del cuerpo (@RequestBody) y lo convierte en un objeto Equipo
    public ResponseEntity<Equipo> crear(@RequestBody Equipo equipo) {
        //si el id viene distinto de 0 y ya existe devuelve 400
        //si no existe, ejecuta save(equipo) hace un INSERT INTO equipos
        if (equipo.getId() != 0 && equipoRepository.existsById(equipo.getId())) {
            return ResponseEntity.badRequest().body(null);
        }
        Equipo nuevo = equipoRepository.save(equipo);
        return ResponseEntity.ok(nuevo);
    }

    //Actualizar equipo existente
    //PUT /equipos/{id}
    //PUT http://localhost:8080/equipos/3
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar equipo por id")
    public ResponseEntity<Equipo> actualizar(@PathVariable int id, @RequestBody Equipo datos) {
        //se busca el equipo (findById(id))
        return equipoRepository.findById(id)
                .map(equipo -> {
                    //se copian los valores nuevos
                    equipo.setNombre(datos.getNombre());
                    equipo.setPais(datos.getPais());
                    equipo.setFundacion(datos.getFundacion());
                    equipo.setCif(datos.getCif());
                    //se devuelve el equipo actualizado y 200 OK
                    return ResponseEntity.ok(equipoRepository.save(equipo));
                })
                //si no existe 404
                .orElse(ResponseEntity.notFound().build());
    }

    //Eliminar equipo
    //DELETE http://localhost:8080/equipos/{id}
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar equipo por id")
    public ResponseEntity<Void> eliminar(@PathVariable int id) {
        //se comprueba si el ID existe (existsById → SELECT 1 FROM equipos WHERE id = ?)
        if (equipoRepository.existsById(id)) {
            //Si existe deleteById() DELETE FROM equipos WHERE id = ?
            equipoRepository.deleteById(id);
            //devuelve 204 No Content (operación correcta, sin cuerpo de respuesta)
            return ResponseEntity.noContent().build();
        }
        //si no existe 404 Not Found
        return ResponseEntity.notFound().build();
    }

    //importa el csv de equipos desde postman
    //POST http://localhost:8080/equipos/import
    //Body (form-data): file=<archivo.csv>
    @PostMapping("/import")
    @Operation(summary = "Importar CSV")
    public ResponseEntity<ImportResult> importarCsv(@RequestParam("file") MultipartFile file) {
        ImportResult resultado = equipoService.importarCsv(file);
        //si no procesó ninguna fila y hubo errores, devuelve 400
        if (resultado.getTotalFilas() == 0 && !resultado.getErrores().isEmpty()) {
            return ResponseEntity.badRequest().body(resultado);
        }
        return ResponseEntity.ok(resultado);
    }
}
