package com.example.json.controller;

import com.example.json.model.Config;
import com.example.json.service.ConfigService;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// controlador rest para manejar los nodos de configuracion
@RestController
// ruta base para todos los endpoints de configs
@RequestMapping("/api/configs")
public class ConfigController {

    // logger del controlador para ver que pasa cuando llaman a estos endpoints
    private static final Logger log = LoggerFactory.getLogger(ConfigController.class);

    // servicio que conecta con el repo de config, el que luego usa jsonservice
    private final ConfigService configService;

    // spring mete el servicio por constructor
    public ConfigController(ConfigService configService) {
        this.configService = configService;
    }

    // endpoint get para traer todos los nodos, util para ver la tabla entera
    @GetMapping
    @Operation(summary = "listar todos los nodos")
    public ResponseEntity<List<Config>> getAll() {
        log.info("GET /api/configs -> listando todos los nodos");
        // el servicio tira del repo y devuelve la lista completa
        return ResponseEntity.ok(configService.findAll());
    }

    // endpoint get para traer solo los nodos raiz, MUY importante para jsonservice
    @GetMapping("/root")
    @Operation(summary = "listar los nodos raiz")
    public ResponseEntity<List<Config>> getRootNodes() {
        log.info("GET /api/configs/root -> listando nodos raíz");
        // aqui sacamos solo los que no tienen padre
        return ResponseEntity.ok(configService.findRootNodes());
    }

    // endpoint get para traer un nodo concreto por id
    @GetMapping("/{id}")
    @Operation(summary = "buscar nodo por id")
    public ResponseEntity<Config> getById(@PathVariable Long id) {
        log.info("GET /api/configs/{} -> buscando nodo por id", id);
        // uso el servicio que busca por id
        Config config = configService.findById(id); // si no existe → ConfigNotFoundException → handler global
        return ResponseEntity.ok(config);
    }

    // endpoint post para crear un nodo nuevo de config
    @PostMapping
    @Operation(summary = "crear nuevo nodo")
    public ResponseEntity<Config> create(@RequestBody Config config) {
        log.info("POST /api/configs -> creando nuevo nodo");
        // guardo el nodo en bbdd, enlaza con attribute y con parent
        Config saved = configService.save(config);
        return ResponseEntity.ok(saved);
    }

    // endpoint put para actualizar un nodo existente
    @PutMapping("/{id}")
    @Operation(summary = "actualizar nodo por id")
    public ResponseEntity<Config> update(@PathVariable Long id, @RequestBody Config config) {
        log.info("PUT /api/configs/{} -> actualizando nodo", id);
        // pillo el existente para asegurar que el id existe
        Config existing = configService.findById(id); // si no existe → 404 del handler
        // actualizo el parent, crucial porque define la estructura del arbol
        existing.setParent(config.getParent());
        // actualizo el atributo, que enlaza con attribute y su tipo
        existing.setAttribute(config.getAttribute());
        // actualizo el valor por defecto, luego jsonservice lo usara al montar el json
        existing.setDefaultValue(config.getDefaultValue());
        // actualizo si es nodo de aplicacion, usado en filtros futuros
        existing.setApplicationNode(config.getApplicationNode());
        // actualizo si es custom, MUY util para distinguir nodos generados por usuario
        existing.setCustom(config.getCustom());
        // guardo todo en bbdd
        Config updated = configService.save(existing);
        return ResponseEntity.ok(updated);
    }

    // endpoint delete para borrar un nodo de config
    @DeleteMapping("/{id}")
    @Operation(summary = "borrar nodo por id")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.warn("DELETE /api/configs/{} -> intentando borrar nodo", id);
        // uso el servicio que tira del repo para borrarlo
        configService.delete(id); // si no existe → 404 del handler
        return ResponseEntity.ok().build();
    }
}


//package com.example.json.controller;
//
//import com.example.json.model.Config;
//import com.example.json.service.ConfigService;
//import io.swagger.v3.oas.annotations.Operation;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//// controlador rest para manejar los nodos de configuracion
//@RestController
//// ruta base para todos los endpoints de configs
//@RequestMapping("/api/configs")
//public class ConfigController {
//
//    // servicio que conecta con el repo de config, el que luego usa jsonservice
//    private final ConfigService configService;
//
//    // spring mete el servicio por constructor
//    public ConfigController(ConfigService configService) {
//        this.configService = configService;
//    }
//
//    // endpoint get para traer todos los nodos, util para ver la tabla entera
//    @GetMapping
//    @Operation(summary = "listar todos los nodos")
//    public List<Config> getAll() {
//        // el servicio tira del repo y devuelve la lista completa
//        return configService.findAll();
//    }
//
//    // endpoint get para traer solo los nodos raiz, MUY importante para jsonservice
//    @GetMapping("/root")
//    @Operation(summary = "listar los nodos raiz")
//    public List<Config> getRootNodes() {
//        // aqui sacamos solo los que no tienen padre
//        return configService.findRootNodes();
//    }
//
//    // endpoint get para traer un nodo concreto por id
//    @GetMapping("/{id}")
//    @Operation(summary = "buscar nodo por id")
//    public Config getById(@PathVariable Long id) {
//        // uso el servicio que busca por id
//        return configService.findById(id);
//    }
//
//    // endpoint post para crear un nodo nuevo de config
//    @PostMapping
//    @Operation(summary = "crear nuevo nodo")
//    public Config create(@RequestBody Config config) {
//        // guardo el nodo en bbdd, enlaza con attribute y con parent
//        return configService.save(config);
//    }
//
//    // endpoint put para actualizar un nodo existente
//    @PutMapping("/{id}")
//    @Operation(summary = "actualizar nodo por id")
//    public Config update(@PathVariable Long id, @RequestBody Config config) {
//        // pillo el existente para asegurar que el id existe
//        Config existing = configService.findById(id);
//        // actualizo el parent, crucial porque define la estructura del arbol
//        existing.setParent(config.getParent());
//        // actualizo el atributo, que enlaza con attribute y su tipo
//        existing.setAttribute(config.getAttribute());
//        // actualizo el valor por defecto, luego jsonservice lo usara al montar el json
//        existing.setDefaultValue(config.getDefaultValue());
//        // actualizo si es nodo de aplicacion, usado en filtros futuros
//        existing.setApplicationNode(config.getApplicationNode());
//        // actualizo si es custom, MUY util para distinguir nodos generados por usuario
//        existing.setCustom(config.getCustom());
//        // guardo todo en bbdd
//        return configService.save(existing);
//    }
//
//    // endpoint delete para borrar un nodo de config
//    @DeleteMapping("/{id}")
//    @Operation(summary = "borrar nodo por id")
//    public void delete(@PathVariable Long id) {
//        // uso el servicio que tira del repo para borrarlo
//        configService.delete(id);
//    }
//}
