package com.example.json.controller;

import com.example.json.model.AttributeType;
import com.example.json.service.AttributeTypeService;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// controlador rest para manejar los tipos de atributo
@RestController
// ruta base para todos los endpoints de tipos
@RequestMapping("/api/attribute-types")
public class AttributeTypeController {

    // logger para saber qué pasa por aquí
    private static final Logger log = LoggerFactory.getLogger(AttributeTypeController.class);

    // servicio que conecta con el repositorio de tipos
    private final AttributeTypeService attributeTypeService;

    // spring mete el servicio por constructor
    public AttributeTypeController(AttributeTypeService attributeTypeService) {
        this.attributeTypeService = attributeTypeService;
    }

    // endpoint get para traer todos los tipos
    @GetMapping
    @Operation(summary = "todos los tipos")
    public ResponseEntity<List<AttributeType>> getAll() {
        log.info("GET /api/attribute-types -> listando todos los tipos");
        // el servicio tira del repo y devuelve todo
        return ResponseEntity.ok(attributeTypeService.findAll());
    }

    // endpoint get para traer un tipo concreto por id
    @GetMapping("/{id}")
    @Operation(summary = "buscar tipo por id")
    public ResponseEntity<AttributeType> getById(@PathVariable Long id) {
        log.info("GET /api/attribute-types/{} -> buscando tipo", id);
        // uso el servicio que busca el tipo, y si no existe explota
        AttributeType type = attributeTypeService.findById(id); // lanzarás tu custom exception aquí
        return ResponseEntity.ok(type);
    }

    // endpoint post para crear un nuevo tipo
    @PostMapping
    @Operation(summary = "crear nuevo tipo")
    public ResponseEntity<AttributeType> create(@RequestBody AttributeType attributeType) {
        log.info("POST /api/attribute-types -> creando nuevo tipo");
        // aqui podriamos validar, pero lo dejamos simple para el ejercicio
        // el servicio guarda usando el repo de attributeType
        AttributeType saved = attributeTypeService.save(attributeType);
        return ResponseEntity.ok(saved);
    }

    // endpoint put para editar un tipo por id
    @PutMapping("/{id}")
    @Operation(summary = "actualizar tipo por id")
    public ResponseEntity<AttributeType> update(@PathVariable Long id, @RequestBody AttributeType attributeType) {
        log.info("PUT /api/attribute-types/{} -> actualizando tipo", id);
        // pillo el existente para asegurarme que el id existe
        AttributeType existing = attributeTypeService.findById(id);  // si no existe → handler global

        // actualizo el string de tipo, que se usa luego en las configs y atributos
        existing.setType(attributeType.getType());
        // actualizo si es lista, usado en la logica del jsonservice
        existing.setList(attributeType.isList());
        // actualizo si es enum, enlazado con attributeTypeValue
        existing.setEnum(attributeType.isEnum());

        // guardo los cambios en bbdd
        AttributeType updated = attributeTypeService.save(existing);
        return ResponseEntity.ok(updated);
    }

    // endpoint delete para borrar un tipo por id
    @DeleteMapping("/{id}")
    @Operation(summary = "borrar tipo por id")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.warn("DELETE /api/attribute-types/{} -> borrando tipo", id);
        // tiro del servicio que usa el repo y hace el delete
        attributeTypeService.delete(id);
        return ResponseEntity.ok().build();
    }
}


//package com.example.json.controller;
//
//import com.example.json.model.AttributeType;
//import com.example.json.service.AttributeTypeService;
//import io.swagger.v3.oas.annotations.Operation;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//// controlador rest para manejar los tipos de atributo
//@RestController
//// ruta base para todos los endpoints de tipos
//@RequestMapping("/api/attribute-types")
//public class AttributeTypeController {
//
//    // servicio que conecta con el repositorio de tipos
//    private final AttributeTypeService attributeTypeService;
//
//    // spring mete el servicio por constructor
//    public AttributeTypeController(AttributeTypeService attributeTypeService) {
//        this.attributeTypeService = attributeTypeService;
//    }
//
//    // endpoint get para traer todos los tipos
//    @GetMapping
//    @Operation(summary = "todos los tipos")
//    public List<AttributeType> getAll() {
//        // el servicio tira del repo y devuelve todo
//        return attributeTypeService.findAll();
//    }
//
//    // endpoint get para traer un tipo concreto por id
//    @GetMapping("/{id}")
//    @Operation(summary = "buscar tipo por id")
//    public AttributeType getById(@PathVariable Long id) {
//        // uso el servicio que busca el tipo, y si no existe explota
//        return attributeTypeService.findById(id);
//    }
//
//    // endpoint post para crear un nuevo tipo
//    @PostMapping
//    @Operation(summary = "crear nuevo tipo")
//    public AttributeType create(@RequestBody AttributeType attributeType) {
//        // aqui podriamos validar, pero lo dejamos simple para el ejercicio
//        // el servicio guarda usando el repo de attributeType
//        return attributeTypeService.save(attributeType);
//    }
//
//    // endpoint put para editar un tipo por id
//    @PutMapping("/{id}")
//    @Operation(summary = "actualizar tipo por id")
//    public AttributeType update(@PathVariable Long id, @RequestBody AttributeType attributeType) {
//        // pillo el existente para asegurarme que el id existe
//        AttributeType existing = attributeTypeService.findById(id);
//        // actualizo el string de tipo, que se usa luego en las configs y atributos
//        existing.setType(attributeType.getType());
//        // actualizo si es lista, usado en la logica del jsonservice
//        existing.setList(attributeType.isList());
//        // actualizo si es enum, enlazado con attributeTypeValue
//        existing.setEnum(attributeType.isEnum());
//        // guardo los cambios en bbdd
//        return attributeTypeService.save(existing);
//    }
//
//    // endpoint delete para borrar un tipo por id
//    @DeleteMapping("/{id}")
//    @Operation(summary = "borrar tipo por id")
//    public void delete(@PathVariable Long id) {
//        // tiro del servicio que usa el repo y hace el delete
//        attributeTypeService.delete(id);
//    }
//}

