package com.ejemplo.jugadorescsv.service;

import com.ejemplo.jugadorescsv.model.Jugador;
import com.ejemplo.jugadorescsv.repository.JugadorRepository;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;

@Service
public class JugadorService {

    private final JugadorRepository jugadorRepository;

    //formato para fechas año, mes y dia
    private final DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    //constructor
    public JugadorService(JugadorRepository jugadorRepository) {
        this.jugadorRepository = jugadorRepository;
    }

    @Transactional
    public ImportResult importarCsv(MultipartFile archivo) {
        //importresult es una clase que guarda informacion sobre la importacion del csv
        ImportResult resultado = new ImportResult();

        //comprueba que el archivo no este vacio ni sea null
        if (archivo == null || archivo.isEmpty()) {
            //si esta vacio devuelve error y sale
            resultado.getErrores().add("csv vacío");
            return resultado;
        }

        try {
            //Lee el contenido linea a linea para construir un String con el contenido
            //y asi poder probar con el punto y coma y con la coma
            String contenido;
            try (BufferedReader lector = new BufferedReader(
                    new InputStreamReader(archivo.getInputStream(), StandardCharsets.UTF_8))) {

                StringBuilder acumulador = new StringBuilder();
                String linea;
                while ((linea = lector.readLine()) != null) {
                    acumulador.append(linea).append('\n');
                }
                contenido = acumulador.toString();
            }

            //prueba con delimitador de punto y coma entre las columnas
            ImportResult resultadoPuntoYComa = tryParseAndImport(contenido, ';');

            // Si no se importó nada y hubo errores probamos con la coma
            if (resultadoPuntoYComa.getTotalFilas() == 0 && !resultadoPuntoYComa.getErrores().isEmpty()) {
                ImportResult resultadoComa = tryParseAndImport(contenido, ',');

                //compara y elige el que haya conseguido mas separadores iguales
                int operacionesPC = resultadoPuntoYComa.getInsertados() + resultadoPuntoYComa.getActualizados();
                int operacionesComa = resultadoComa.getInsertados() + resultadoComa.getActualizados();
                if (operacionesComa > operacionesPC) {
                    return resultadoComa;
                }
            }
            return resultadoPuntoYComa;

        } catch (Exception ex) {
            //Error general
            resultado.getErrores().add("Error general procesando el CSV: " + ex.getMessage());
            return resultado;
        }
    }

    private ImportResult tryParseAndImport(String contenidoCsv, char delimitador) {
        ImportResult resultado = new ImportResult();

        try (CSVParser parser = CSVParser.parse(
                contenidoCsv,
                CSVFormat.DEFAULT.builder()
                        //lee las cabeceras de la primera fila
                        .setHeader()
                        //no trata la primera fila como registro
                        .setSkipHeaderRecord(true)
                        //quita espacios en blanco
                        .setTrim(true)
                        //delimitador con el que probar
                        .setDelimiter(delimitador)
                        .build()
        )) {
            int total = 0;
            int insertados = 0;
            int actualizados = 0;

            for (Iterator<CSVRecord> iterator = parser.iterator(); iterator.hasNext(); ) {
                CSVRecord registro = iterator.next();
                total++;
                try {
                    //lectura de cabeceras equivalentes
                    String idTexto = safe(registro, "id");
                    String nombre = safe(registro, "nombre");
                    String fechaTexto = safe(registro, "fechaNacimiento", "fecha_nacimiento");
                    String nacionalidad = safe(registro, "nacionalidad");
                    String dni = safe(registro, "dni");

                    //validaciones mínimas
                    if (idTexto == null || idTexto.isBlank()) {
                        throw new IllegalArgumentException("falta 'id'");
                    }
                    Long id = Long.parseLong(idTexto);

                    //recuperar o crear el jugador con ese id
                    Jugador jugador = jugadorRepository.findById(id).orElseGet(() -> {
                        Jugador nuevo = new Jugador();
                        nuevo.setId(id);
                        return nuevo;
                    });

                    boolean existe = jugadorRepository.existsById(id);

                    //aplicar campos si vienen
                    if (nombre != null) jugador.setNombre(nombre);
                    if (nacionalidad != null) jugador.setNacionalidad(nacionalidad);
                    if (dni != null) jugador.setDni(dni);

                    if (fechaTexto != null && !fechaTexto.isBlank()) {
                        jugador.setFechaNacimiento(LocalDate.parse(fechaTexto, formatoFecha));
                    } else if (!existe) {
                        //Si es nuevo y no trae fecha es null
                        jugador.setFechaNacimiento(null);
                    }

                    //guardar
                    jugadorRepository.save(jugador);
                    if (existe) actualizados++;
                    else insertados++;

                } catch (Exception errorFila) {
                    //guardamos el error indicando el número de fila del CSV
                    resultado.getErrores().add("Fila " + registro.getRecordNumber() + ": " + errorFila.getMessage());
                }
            }

            //resultado de la importacion
            resultado.setTotalFilas(total);
            resultado.setInsertados(insertados);
            resultado.setActualizados(actualizados);
            return resultado;

        } catch (Exception ex) {
            //error de parseo con ese delimitador en concreto
            resultado.getErrores().add("Fallo con delimitador '" + delimitador + "': " + ex.getMessage());
            return resultado;
        }
    }

    //devuelve el primer valor existente y no vacío entre varias posibles cabeceras
    //si ninguna existe o tiene contenido, devuelve nul
    private static String safe(CSVRecord registro, String... cabeceras) {
        for (String cabecera : cabeceras) {
            try {
                String valor = registro.get(cabecera);
                if (valor != null && !valor.isBlank()) return valor.trim();
            } catch (IllegalArgumentException ignored) {
                //La cabecera no existe en este CSV pues seguimos probando con las demás
            }
        }
        return null;
    }
}

