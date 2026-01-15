package com.example.json.controller;

import com.example.json.service.JsonExportService;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

// controlador rest para exportar el json final reconstruido desde la tabla config
@RestController
public class JsonExportController {

    // logger del controlador
    private static final Logger log = LoggerFactory.getLogger(JsonExportController.class);

    // servicio que usa jsonservice por debajo para montar el json completo
    private final JsonExportService jsonExportService;

    // spring mete el servicio por constructor
    public JsonExportController(JsonExportService jsonExportService) {
        this.jsonExportService = jsonExportService;
    }

    // endpoint get para sacar el json final ya montado con recursividad y todo
    @GetMapping("/api/config/export")
    @Operation(summary = "exportar un json")
    public ResponseEntity<Map<String, Object>> exportJson() {

        log.info("GET /api/config/export -> reconstruyendo JSON final");

        // llamo al servicio que tira de jsonservice, que a su vez usa configRepository
        Map<String, Object> json = jsonExportService.exportJson();

        log.debug("JSON exportado con {} claves en la raíz", json.size());

        return ResponseEntity.ok(json);
    }
}


//package com.example.json.controller;
//
//import com.example.json.service.JsonExportService;
//import io.swagger.v3.oas.annotations.Operation;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.Map;
//
//// controlador rest para exportar el json final reconstruido desde la tabla config
//@RestController
//public class JsonExportController {
//
//    // servicio que usa jsonservice por debajo para montar el json completo
//    private final JsonExportService jsonExportService;
//
//    // spring mete el servicio por constructor
//    public JsonExportController(JsonExportService jsonExportService) {
//        this.jsonExportService = jsonExportService;
//    }
//
//    // endpoint get para sacar el json final ya montado con recursividad y todo
//    @GetMapping("/api/config/export")
//    @Operation(summary = "exportar un json")
//    public Map<String, Object> exportJson() {
//        // llamo al servicio que tira de jsonservice, que a su vez usa configRepository
//        return jsonExportService.exportJson();
//    }
//}
