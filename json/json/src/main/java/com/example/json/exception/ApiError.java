package com.example.json.exception;

import java.time.LocalDateTime;

//Clase que representa cómo se verán los errores en la api cuando algo falle
public class ApiError {

    private int status;              // Código HTTP (404, 400, 500...)
    private String error;            // Texto corto: "Not Found", "Bad Request", etc.
    private String message;          // Mensaje más específico
    private String path;             // Ruta donde ocurrió el error
    private LocalDateTime timestamp; // Momento exacto

    // Constructor vacío (necesario para que Spring/Jackson funcione bien)
    public ApiError() {
    }

    // Constructor útil para crear un error de forma rápida
    public ApiError(int status, String error, String message, String path) {
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
        this.timestamp = LocalDateTime.now(); // Marca temporal automática
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
