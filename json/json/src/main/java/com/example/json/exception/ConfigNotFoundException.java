package com.example.json.exception;

// Excepción para cuando no encontramos una config / atributo / lo que sea
public class ConfigNotFoundException extends RuntimeException {

    // Constructor con mensaje
    public ConfigNotFoundException(String message) {
        super(message);
    }
}
