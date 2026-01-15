package com.example.json.controller;

import com.example.json.model.AttributeTypeValue;
import com.example.json.service.AttributeTypeValueService;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// controlador rest para manejar los valores permitidos de los tipos de atributo
@RestController
// ruta base para todos los endpoints de los type-values
@RequestMapping("/api/attribute-type-values")
public class AttributeTypeValueController {

    // logger para ver que pasa por estos endpoints
    private static final Logger log = LoggerFactory.getLogger(AttributeTypeValueController.class);

    // servicio que conecta con el repo de los valores enum
    private final AttributeTypeValueService attributeTypeValueService;

    // spring mete el servicio por constructor
    public AttributeTypeValueController(AttributeTypeValueService attributeTypeValueService) {
        this.attributeTypeValueService = attributeTypeValueService;
    }

    // endpoint get para traer todos los valores posibles
    @GetMapping
    @Operation(summary = "listar todos los valores")
    public ResponseEntity<List<AttributeTypeValue>> getAll() {
        log.info("GET /api/attribute-type-values -> listando todos los valores");
        // el servicio tira del repo y devuelve la lista entera
        return ResponseEntity.ok(attributeTypeValueService.findAll());
    }

    // endpoint get para traer un valor concreto por id
    @GetMapping("/{id}")
    @Operation(summary = "listar valor por id")
    public ResponseEntity<AttributeTypeValue> getById(@PathVariable Long id) {
        log.info("GET /api/attribute-type-values/{} -> buscando valor", id);
        // uso el servicio para buscar por id
        AttributeTypeValue value = attributeTypeValueService.findById(id); // si no existe -> custom exception -> handler global
        return ResponseEntity.ok(value);
    }

    // endpoint post para crear un nuevo valor enum
    @PostMapping
    @Operation(summary = "crear nuevo valor")
    public ResponseEntity<AttributeTypeValue> create(@RequestBody AttributeTypeValue value) {
        log.info("POST /api/attribute-type-values -> creando nuevo valor");
        // guardo el valor, enlazado con attributeType, que luego usa config al montar el json
        AttributeTypeValue saved = attributeTypeValueService.save(value);
        return ResponseEntity.ok(saved);
    }

    // endpoint put para actualizar un valor existente
    @PutMapping("/{id}")
    @Operation(summary = "actualizar valor por id")
    public ResponseEntity<AttributeTypeValue> update(@PathVariable Long id, @RequestBody AttributeTypeValue value) {
        log.info("PUT /api/attribute-type-values/{} -> actualizando valor", id);
        // pillo el existente para asegurar que existe
        AttributeTypeValue existing = attributeTypeValueService.findById(id);
        // actualizo el string del valor, muy importante en los tipos enum
        existing.setValue(value.getValue());
        // actualizo el tipo al que pertenece, aqui hay que mandar el objeto correcto desde el json
        existing.setAttributeType(value.getAttributeType());
        // guardo los cambios en bbdd
        AttributeTypeValue updated = attributeTypeValueService.save(existing);

        return ResponseEntity.ok(updated);
    }

    // endpoint delete para borrar un valor permitido del tipo
    @DeleteMapping("/{id}")
    @Operation(summary = "borrar valor por id")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.warn("DELETE /api/attribute-type-values/{} -> borrando valor permitido", id);
        // llamo al servicio que usa el repo para borrar
        attributeTypeValueService.delete(id);
        return ResponseEntity.ok().build();
    }
}


//package com.example.json.controller;
//
//import com.example.json.model.AttributeTypeValue;
//import com.example.json.service.AttributeTypeValueService;
//import io.swagger.v3.oas.annotations.Operation;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//// controlador rest para manejar los valores permitidos de los tipos de atributo
//@RestController
//// ruta base para todos los endpoints de los type-values
//@RequestMapping("/api/attribute-type-values")
//public class AttributeTypeValueController {
//
//    // servicio que conecta con el repo de los valores enum
//    private final AttributeTypeValueService attributeTypeValueService;
//
//    // spring mete el servicio por constructor
//    public AttributeTypeValueController(AttributeTypeValueService attributeTypeValueService) {
//        this.attributeTypeValueService = attributeTypeValueService;
//    }
//
//    // endpoint get para traer todos los valores posibles
//    @GetMapping
//    @Operation(summary = "listar todos los valores")
//    public List<AttributeTypeValue> getAll() {
//        // el servicio tira del repo y devuelve la lista entera
//        return attributeTypeValueService.findAll();
//    }
//
//    // endpoint get para traer un valor concreto por id
//    @GetMapping("/{id}")
//    @Operation(summary = "listar valor por id")
//    public AttributeTypeValue getById(@PathVariable Long id) {
//        // uso el servicio para buscar por id
//        return attributeTypeValueService.findById(id);
//    }
//
//    // endpoint post para crear un nuevo valor enum
//    @PostMapping
//    @Operation(summary = "crear nuevo valor")
//    public AttributeTypeValue create(@RequestBody AttributeTypeValue value) {
//        // guardo el valor, enlazado con attributeType, que luego usa config al montar el json
//        return attributeTypeValueService.save(value);
//    }
//
//    // endpoint put para actualizar un valor existente
//    @PutMapping("/{id}")
//    @Operation(summary = "actualizar valor por id")
//    public AttributeTypeValue update(@PathVariable Long id, @RequestBody AttributeTypeValue value) {
//        // pillo el existente para asegurar que existe
//        AttributeTypeValue existing = attributeTypeValueService.findById(id);
//        // actualizo el string del valor, muy importante en los tipos enum
//        existing.setValue(value.getValue());
//        // actualizo el tipo al que pertenece, aqui hay que mandar el objeto correcto desde el json
//        existing.setAttributeType(value.getAttributeType());
//        // guardo los cambios en bbdd
//        return attributeTypeValueService.save(existing);
//    }
//
//    // endpoint delete para borrar un valor permitido del tipo
//    @DeleteMapping("/{id}")
//    @Operation(summary = "borrar valor por id")
//    public void delete(@PathVariable Long id) {
//        // llamo al servicio que usa el repo para borrar
//        attributeTypeValueService.delete(id);
//    }
//}
