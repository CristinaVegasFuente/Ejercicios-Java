package com.example.json.exception;

// excepción específica para cuando no encontramos un AttributeType
public class AttributeTypeNotFoundException extends RuntimeException {

    public AttributeTypeNotFoundException(String message) {
        super(message);
    }
}
