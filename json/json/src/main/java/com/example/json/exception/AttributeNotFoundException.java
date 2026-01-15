package com.example.json.exception;

// Excepción específica para cuando no encontramos un Attribute
public class AttributeNotFoundException extends RuntimeException {

    public AttributeNotFoundException(String message) {
        super(message);
    }
}
