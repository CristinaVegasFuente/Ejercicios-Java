package com.ejemplo.jugadorescsv.service;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

//funcion de devolver un resultado de una importacion como en este caso de csv
//es un resumen de la importacion, cuantas filas, inserts, updates...
@Data
public class ImportResult {

    private int totalFilas;
    private int insertados;
    private int actualizados;

    private List<String> errores = new ArrayList<>();

}
