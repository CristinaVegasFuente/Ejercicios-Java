package com.proyecto.controller;

import com.proyecto.entity.Contratos;
import com.proyecto.service.contratosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/contratos")
public class contratosController {

    private final contratosService contratosService;

    @Autowired
    public contratosController(contratosService contratosService) {
        this.contratosService = contratosService;
    }

    //READ todos
    @GetMapping
    public List<Contratos> getAll() {
        return contratosService.getContratos();
    }

    //READ por ID
    @GetMapping("/{id}")
    public ResponseEntity<Contratos> getById(@PathVariable("id") int id) {
        Optional<Contratos> contrato = contratosService.getContratoById(id);
        return contrato.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    //CREATE
    @PostMapping
    public ResponseEntity<Contratos> createContrato(@RequestBody Contratos contrato) {
        try {
            Contratos nuevoContrato = contratosService.saveContrato(contrato);
            return new ResponseEntity<>(nuevoContrato, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //UPDATE actualiza basado en su ID.
    @PutMapping("/{id}")
    public ResponseEntity<Contratos> updateContrato(@PathVariable("id") int id, @RequestBody Contratos contrato) {
        if (!contratosService.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        //el ID del objeto coincida con el ID de la URL
        contrato.setId_jugador(id);
        Contratos updatedContrato = contratosService.saveContrato(contrato);
        return new ResponseEntity<>(updatedContrato, HttpStatus.OK);
    }

    //DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteContrato(@PathVariable("id") int id) {
        try {
            if (!contratosService.existsById(id)) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            contratosService.deleteContrato(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204 No Content
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
