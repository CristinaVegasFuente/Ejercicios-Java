package com.ejemplo.jugadorescsv.controller;

import com.ejemplo.jugadorescsv.model.Contrato;
import com.ejemplo.jugadorescsv.model.ContratoId;
import com.ejemplo.jugadorescsv.repository.ContratoRepository;
import com.ejemplo.jugadorescsv.service.ContratoService;
import com.ejemplo.jugadorescsv.service.ImportResult;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@RestController

@RequestMapping("/contratos")
public class ContratoController {

    //inyeccion de dependencias del repositorio JPA
    @Autowired
    private ContratoRepository contratoRepository;
    //inyección del servicio para lógica de negocio
    @Autowired
    private ContratoService contratoService;

    //lista todos los contratos
    //GET http://localhost:8080/contratos
    @GetMapping
    @Operation(summary = "Listar todos contratos")
    public List<Contrato> listar() {
        //devuelve todos los contratos de la tabla
        return contratoRepository.findAll();
    }

    //busca por id_equipo
    //GET http://localhost:8080/contratos/equipo/3
    @GetMapping("/equipo/{idEquipo}")
    @Operation(summary = "Listar contratos por id de equipo")
    public List<Contrato> buscarPorEquipo(@PathVariable int idEquipo) {
        //usa el id compuesto (campo id.idEquipo) para filtrar
        return contratoRepository.findById_IdEquipo(idEquipo);
    }

    //crea un contrato nuevo
    @PostMapping
    @Operation(summary = "Crear contrato")
    public ResponseEntity<Contrato> crear(@RequestBody Contrato contrato) {
        //validación el contrato debe traer su id compuesto
        if (contrato.getId() == null) {
            return ResponseEntity.badRequest().build();
        }
        //guarda y devuelve el contrato creado
        return ResponseEntity.ok(contratoRepository.save(contrato));
    }

    // actualiza (solo dorsal)
    @PutMapping("/{idJugador}/{idEquipo}/{fechaInicio}/{fechaFin}")
    @Operation(summary = "Actualizar contrato")
    public ResponseEntity<Contrato> actualizar(
            @PathVariable int idJugador,
            @PathVariable int idEquipo,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin,
            @RequestBody Contrato datos) {

        ContratoId id = new ContratoId(idJugador, idEquipo, fechaInicio, fechaFin);

        return contratoRepository.findById(id)
                .map(contrato -> {
                    contrato.setDorsal(datos.getDorsal());
                    return ResponseEntity.ok(contratoRepository.save(contrato));
                })
                .orElse(ResponseEntity.notFound().build());
    }


    // elimina contrato
    @DeleteMapping("/{idJugador}/{idEquipo}/{fechaInicio}/{fechaFin}")
    @Operation(summary = "Eliminar contrato")
    public ResponseEntity<Void> eliminar(
            @PathVariable int idJugador,
            @PathVariable int idEquipo,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin) {

        ContratoId id = new ContratoId(idJugador, idEquipo, fechaInicio, fechaFin);

        if (contratoRepository.existsById(id)) {
            contratoRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }


    //importa el csv de contratos desde postman
    //POST http://localhost:8080/contratos/import
    //Body (form-data): file=<archivo.csv>
    @PostMapping("/import")
    @Operation(summary = "Importar CSV")
    public ResponseEntity<ImportResult> importarCsv(@RequestParam("file") MultipartFile file) {
        ImportResult res = contratoService.importarCsv(file);
        // Si no se procesó ninguna fila y hubo errores, devolvemos 400
        if (res.getTotalFilas() == 0 && !res.getErrores().isEmpty()) {
            return ResponseEntity.badRequest().body(res);
        }
        return ResponseEntity.ok(res);
    }
}
