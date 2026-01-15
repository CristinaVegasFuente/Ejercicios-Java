package com.example.json.controller;

import com.example.json.service.JsonImportService;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

//controlador dedicado solo a devolver el json reconstruido
@RestController
//ruta base para todito lo relacionado con json
@RequestMapping("/api/json")
public class JsonImportController {

    // logger del controlador
    private static final Logger log = LoggerFactory.getLogger(JsonImportController.class);

   // servicio que hace la importacion, muy ligado a config y atributos
    private final JsonImportService jsonImportService;

    // spring mete ambos servicios por constructor
    public JsonImportController(JsonImportService jsonImportService) {
        this.jsonImportService = jsonImportService;
    }

    //endpoint post para importar un json y meterlo en las tablas como nodos config
    @PostMapping("/import")
    @Operation(summary = "importar un json")
    public ResponseEntity<Void> importJson(@RequestBody Map<String, Object> json) {

        log.info("POST /api/json/import -> iniciando importación");

        // jsonimportservice parsea el mapa y crea nodos config, enlazando padres e hijos
        jsonImportService.importJson(json);

        log.info("Importación JSON completada con éxito");

        // no devuelvo nada porque tu servicio tampoco devuelve nada
        return ResponseEntity.ok().build();
    }
}


//package com.example.json.controller;
//
//import com.example.json.service.JsonImportService;
//import com.example.json.service.JsonService;
//import io.swagger.v3.oas.annotations.Operation;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.Map;
//
////controlador dedicado solo a devolver el json reconstruido
//@RestController
////ruta base para todito lo relacionado con json
//@RequestMapping("/api/json")
//public class JsonImportController {
//
//    // servicio que monta el json usando configRepository y su recursividad
//    private final JsonService jsonService;
//    // servicio que hace la importacion, muy ligado a config y atributos
//    private final JsonImportService jsonImportService;
//
//    // spring mete ambos servicios por constructor
//    public JsonImportController(JsonService jsonService,
//                          JsonImportService jsonImportService) {
//        this.jsonService = jsonService;
//        this.jsonImportService = jsonImportService;
//    }
//
//    // endpoint get para devolver el json completo
//    @GetMapping
//    @Operation(summary = "Devuelve el json completo")
//    public Map<String, Object> getJson() {
//        // uso jsonservice, que reconstruye desde nodos raiz y sus hijos
//        return jsonService.buildJson();
//    }
//
//    // endpoint post para importar un json y meterlo en las tablas como nodos config
//    @PostMapping("/import")
//    @Operation(summary = "importar un json")
//    public void importJson(@RequestBody Map<String, Object> json) {
//        // jsonimportservice parsea el mapa y crea nodos config, enlazando padres e hijos
//        jsonImportService.importJson(json);
//    }
//}
