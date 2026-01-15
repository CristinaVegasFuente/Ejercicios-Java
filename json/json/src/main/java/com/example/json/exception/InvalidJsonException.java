package com.example.json.exception;

// Excepción para cuando el JSON que llega está roto o no tiene lo que debería
public class InvalidJsonException extends RuntimeException {

    public InvalidJsonException(String message) {
        super(message);
    }
}
