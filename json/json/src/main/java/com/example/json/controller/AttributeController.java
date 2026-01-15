package com.example.json.controller;

import com.example.json.model.Attribute;
import com.example.json.service.AttributeService;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// controlador rest que expone los endpoints para manejar atributos
@RestController
// ruta base para lo que sea atributos
@RequestMapping("/api/attributes")
public class AttributeController {

    // logger para ver qué pasa cuando llaman a estos endpoints
    private static final Logger log = LoggerFactory.getLogger(AttributeController.class);

    // inyecto el servicio que hace la chamba con el repositorio
    private final AttributeService attributeService;

    // constructor para meter el servicio, spring lo gestiona
    public AttributeController(AttributeService attributeService) {
        this.attributeService = attributeService;
    }

    // endpoint get para sacar todos los atributos
    @GetMapping
    @Operation(summary = "todos los atributos")
    public ResponseEntity<List<Attribute>> getAll() {
        log.info("GET /api/attributes -> listando todos los atributos");
        // llamo al servicio que tira del repo y devuelve todo
        return ResponseEntity.ok(attributeService.findAll());
    }

    // endpoint get para sacar un atributo por id
    @GetMapping("/{id}")
    @Operation(summary = "buscar atributo por id")
    public ResponseEntity<Attribute> getById(@PathVariable Long id) {
        log.info("GET /api/attributes/{} -> buscando atributo por id", id);
        // uso el servicio para buscar el atributo, si no existe explotara
        Attribute attribute = attributeService.findById(id); // aquí debería lanzar tu excepción custom
        return ResponseEntity.ok(attribute);
    }

    // endpoint post para crear un atributo nuevo
    @PostMapping
    @Operation(summary = "crear un atributo")
    public ResponseEntity<Attribute> create(@RequestBody Attribute attribute) {
        log.info("POST /api/attributes -> creando atributo");
        // guardo el atributo usando el servicio, que a su vez usa el repo
        Attribute saved = attributeService.save(attribute);
        return ResponseEntity.ok(saved);
    }

    // endpoint put para actualizar un atributo ya existente
    @PutMapping("/{id}")
    @Operation(summary = "actualizar atributo por id")
    public ResponseEntity<Attribute> update(@PathVariable Long id, @RequestBody Attribute attribute) {
        log.info("PUT /api/attributes/{} -> actualizando atributo", id);
        // pillo el atributo que ya existe en bbdd
        Attribute existing = attributeService.findById(id); // si no existe → 404 con tu handler global
        // actualizo el nombre, esto luego lo usa el repositorio al guardar
        existing.setName(attribute.getName());
        // actualizo el tipo, enlazado con attributeType y su repo
        existing.setAttributeType(attribute.getAttributeType());
        // guardo los cambios en la bbdd
        Attribute updated = attributeService.save(existing);
        return ResponseEntity.ok(updated);
    }

    // endpoint delete para borrar un atributo segun su id
    @DeleteMapping("/{id}")
    @Operation(summary = "borrar atributo por id")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.warn("DELETE /api/attributes/{} -> borrando atributo", id);
        // llamo al servicio que usa el repo para borrar el registro
        attributeService.delete(id); // si no existe, que lance tu excepción y la pille el handler
        return ResponseEntity.ok().build();
    }
}


//package com.example.json.controller;
//
//import com.example.json.model.Attribute;
//import com.example.json.service.AttributeService;
//import io.swagger.v3.oas.annotations.Operation;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//// controlador rest que expone los endpoints para manejar atributos
//@RestController
//// ruta base para lo que sea atributos
//@RequestMapping("/api/attributes")
//public class AttributeController {
//
//    // inyecto el servicio que hace la chamba con el repositorio
//    private final AttributeService attributeService;
//
//    // constructor para meter el servicio, spring lo gestiona
//    public AttributeController(AttributeService attributeService) {
//        this.attributeService = attributeService;
//    }
//
//    // endpoint get para sacar todos los atributos
//    @GetMapping
//    @Operation(summary = "todos los atributos")
//    public List<Attribute> getAll() {
//        // llamo al servicio que tira del repo y devuelve todo
//        return attributeService.findAll();
//    }
//
//    // endpoint get para sacar un atributo por id
//    @GetMapping("/{id}")
//    @Operation(summary = "buscar atributo por id")
//    public Attribute getById(@PathVariable Long id) {
//        // uso el servicio para buscar el atributo, si no existe explotara
//        return attributeService.findById(id);
//    }
//
//    // endpoint post para crear un atributo nuevo
//    @PostMapping
//    @Operation(summary = "crear un atributo")
//    public Attribute create(@RequestBody Attribute attribute) {
//        // guardo el atributo usando el servicio, que a su vez usa el repo
//        return attributeService.save(attribute);
//    }
//
//    // endpoint put para actualizar un atributo ya existente
//    @PutMapping("/{id}")
//    @Operation(summary = "actualizar atributo por id")
//    public Attribute update(@PathVariable Long id, @RequestBody Attribute attribute) {
//        // pillo el atributo que ya existe en bbdd
//        Attribute existing = attributeService.findById(id);
//        // actualizo el nombre, esto luego lo usa el repositorio al guardar
//        existing.setName(attribute.getName());
//        // actualizo el tipo, enlazado con attributeType y su repo
//        existing.setAttributeType(attribute.getAttributeType());
//        // guardo los cambios en la bbdd
//        return attributeService.save(existing);
//    }
//
//    // endpoint delete para borrar un atributo segun su id
//    @DeleteMapping("/{id}")
//    @Operation(summary = "borrar atributo por id")
//    public void delete(@PathVariable Long id) {
//        // llamo al servicio que usa el repo para borrar el registro
//        attributeService.delete(id);
//    }
//}
