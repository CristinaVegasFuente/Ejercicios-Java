package com.example.json.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

//esta clase va a "cazar" las excepciones de TODOS los controladores
@ControllerAdvice
public class GlobalExceptionHandler {

    // Logger de la propia clase de handler
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    // 404 - Config no encontrada
    @ExceptionHandler(ConfigNotFoundException.class)
    public ResponseEntity<ApiError> handleConfigNotFound(ConfigNotFoundException ex,
                                                         HttpServletRequest request) {

        log.warn("Config no encontrada en path {}: {}", request.getRequestURI(), ex.getMessage());

        ApiError apiError = new ApiError(
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                ex.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);
    }
    // 404 - Attribute no encontrado
    @ExceptionHandler(AttributeNotFoundException.class)
    public ResponseEntity<ApiError> handleAttributeNotFound(AttributeNotFoundException ex,
                                                            HttpServletRequest request) {

        log.warn("Attribute no encontrado en path {}: {}", request.getRequestURI(), ex.getMessage());

        ApiError apiError = new ApiError(
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                ex.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);
    }
    // 404 - AttributeType no encontrado
    @ExceptionHandler(AttributeTypeNotFoundException.class)
    public ResponseEntity<ApiError> handleAttributeTypeNotFound(AttributeTypeNotFoundException ex,
                                                                HttpServletRequest request) {

        log.warn("AttributeType no encontrado en path {}: {}", request.getRequestURI(), ex.getMessage());

        ApiError apiError = new ApiError(
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                ex.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);
    }
    // 404 - AttributeTypeValue no encontrado
    @ExceptionHandler(AttributeTypeValueNotFoundException.class)
    public ResponseEntity<ApiError> handleAttributeTypeValueNotFound(AttributeTypeValueNotFoundException ex,
                                                                     HttpServletRequest request) {

        log.warn("AttributeTypeValue no encontrado en path {}: {}", request.getRequestURI(), ex.getMessage());

        ApiError apiError = new ApiError(
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                ex.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);
    }
    // 400 - JSON de entrada inválido
    @ExceptionHandler(InvalidJsonException.class)
    public ResponseEntity<ApiError> handleInvalidJson(InvalidJsonException ex,
                                                      HttpServletRequest request) {

        log.warn("JSON inválido en path {}: {}", request.getRequestURI(), ex.getMessage());

        ApiError apiError = new ApiError(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                ex.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }
    // 500 - Cualquier otra cosa que pete
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGenericException(Exception ex,
                                                           HttpServletRequest request) {

        log.error("Error no controlado en path {}:", request.getRequestURI(), ex);

        ApiError apiError = new ApiError(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                "Ha ocurrido un error interno en el servidor",
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiError);
    }
}


//package com.example.json.exception;
//
//import jakarta.servlet.http.HttpServletRequest;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//
////esta clase va a "cazar" las excepciones de TODOS los controladores
//@ControllerAdvice
//public class GlobalExceptionHandler {
//
//    // Logger de la propia clase de handler
//    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);
//
//    // 404 - Config no encontrada
//    @ExceptionHandler(ConfigNotFoundException.class)
//    public ResponseEntity<ApiError> handleConfigNotFound(ConfigNotFoundException ex,
//                                                         HttpServletRequest request) {
//        // Log a nivel WARN porque es algo "esperable": no existe el recurso
//        log.warn("Config no encontrada en path {}: {}", request.getRequestURI(), ex.getMessage());
//
//        ApiError apiError = new ApiError(
//                HttpStatus.NOT_FOUND.value(),               // 404
//                HttpStatus.NOT_FOUND.getReasonPhrase(),     // "Not Found"
//                ex.getMessage(),                            // mensaje que tú mandaste
//                request.getRequestURI()                     // /api/loquesea
//        );
//
//        return ResponseEntity
//                .status(HttpStatus.NOT_FOUND)
//                .body(apiError);
//    }
//
//    // 400 - JSON de entrada inválido
//    @ExceptionHandler(InvalidJsonException.class)
//    public ResponseEntity<ApiError> handleInvalidJson(InvalidJsonException ex,
//                                                      HttpServletRequest request) {
//        // Log a nivel WARN también, es culpa de la request
//        log.warn("JSON inválido en path {}: {}", request.getRequestURI(), ex.getMessage());
//
//        ApiError apiError = new ApiError(
//                HttpStatus.BAD_REQUEST.value(),             // 400
//                HttpStatus.BAD_REQUEST.getReasonPhrase(),   // "Bad Request"
//                ex.getMessage(),
//                request.getRequestURI()
//        );
//
//        return ResponseEntity
//                .status(HttpStatus.BAD_REQUEST)
//                .body(apiError);
//    }
//
//    // 500 - Cualquier otra cosa que pete
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<ApiError> handleGenericException(Exception ex,
//                                                           HttpServletRequest request) {
//        // Aquí sí logeamos como ERROR y con stacktrace completo
//        log.error("Error no controlado en path {}:", request.getRequestURI(), ex);
//
//        ApiError apiError = new ApiError(
//                HttpStatus.INTERNAL_SERVER_ERROR.value(),            // 500
//                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),  // "Internal Server Error"
//                "Ha ocurrido un error interno en el servidor",       // mensaje genérico para el cliente
//                request.getRequestURI()
//        );
//
//        return ResponseEntity
//                .status(HttpStatus.INTERNAL_SERVER_ERROR)
//                .body(apiError);
//    }
//}
