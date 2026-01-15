package com.ejemplo.jugadorescsv.service;

import com.ejemplo.jugadorescsv.model.Contrato;
import com.ejemplo.jugadorescsv.model.ContratoId;
import com.ejemplo.jugadorescsv.repository.ContratoRepository;
import com.ejemplo.jugadorescsv.repository.EquipoRepository;
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

//prueba con id, que salga vacio porque no tiene id por si mismo, tiene idJugado e idEquipo.
@Service
public class ContratoService {

    private final ContratoRepository contratoRepository;
    private final JugadorRepository jugadorRepository;
    private final EquipoRepository equipoRepository;

    //formato de fecha año, mes y dia
    private final DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public ContratoService(ContratoRepository contratoRepository,
                           JugadorRepository jugadorRepository,
                           EquipoRepository equipoRepository) {
        this.contratoRepository = contratoRepository;
        this.jugadorRepository = jugadorRepository;
        this.equipoRepository = equipoRepository;
    }

    //importa un csv con contratos
    @Transactional
    public ImportResult importarCsv(MultipartFile archivo) {
        ImportResult resultado = new ImportResult();

        //validación básica del archivo
        if (archivo == null || archivo.isEmpty()) {
            resultado.getErrores().add("csv vacío.");
            return resultado;
        }

        try {
            //lee el contenido para poder probar con distintos delimitadores
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

            //primera prueba delimitador punto y coma
            ImportResult resultadoPuntoYComa = intentarParsearEImportar(contenido, ';');

            //si no funciono probamos con coma
            if ((resultadoPuntoYComa.getInsertados() + resultadoPuntoYComa.getActualizados()) == 0) {
                ImportResult resultadoComa = intentarParsearEImportar(contenido, ',');
                int operacionesComa = resultadoComa.getInsertados() + resultadoComa.getActualizados();
                int operacionesPuntoYComa = resultadoPuntoYComa.getInsertados() + resultadoPuntoYComa.getActualizados();

                //devolvemos el que mejor resultado haya tenido
                if (operacionesComa > operacionesPuntoYComa) {
                    return resultadoComa;
                }
            }
            return resultadoPuntoYComa;

        } catch (Exception e) {
            resultado.getErrores().add("Error con el csv: " + e.getMessage());
            return resultado;
        }
    }

    //intenta parsear el contenido del csv con el delimitador indicado
    //e insertar/actualizar contratos (upsert) en base a la clave compuesta
    private ImportResult intentarParsearEImportar(String contenidoCsv, char delimitador) {
        ImportResult resultado = new ImportResult();

        try (CSVParser parser = CSVParser.parse(
                contenidoCsv,
                CSVFormat.DEFAULT.builder()
                        .setHeader()               // usa la primera línea como cabeceras
                        .setSkipHeaderRecord(true) // omite la fila de cabecera al iterar
                        .setTrim(true)             // quita espacios al principio y final
                        .setDelimiter(delimitador) // delimitador a probar
                        .build()
        )) {
            int totalFilas = 0;
            int insertados = 0;
            int actualizados = 0;

            //itera cada fila de datos sin la cabecera
            for (CSVRecord fila : parser) {
                totalFilas++;
                try {
                    //columnas nombres alternativos
                    String idJugadorTexto = valorSeguro(fila, "id_jugador", "idJugador", "jugador_id");
                    String idEquipoTexto  = valorSeguro(fila, "id_equipo",  "idEquipo",  "equipo_id");
                    String fechaInicioTxt = valorSeguro(fila, "fecha_inicio", "fechaInicio");
                    String dorsalTexto    = valorSeguro(fila, "dorsal");
                    String fechaFinTexto  = valorSeguro(fila, "fecha_fin", "fechaFin");

                    //validaciones mínimas
                    if (idJugadorTexto == null || idJugadorTexto.isBlank()) {
                        throw new IllegalArgumentException("La columna 'id_jugador' es obligatoria.");
                    }
                    if (idEquipoTexto == null || idEquipoTexto.isBlank()) {
                        throw new IllegalArgumentException("La columna 'id_equipo' es obligatoria.");
                    }
                    if (fechaInicioTxt == null || fechaInicioTxt.isBlank()) {
                        throw new IllegalArgumentException("La columna 'fecha_inicio' es obligatoria.");
                    }

                    //convierte tipos a int
                    int idJugador = Integer.parseInt(idJugadorTexto);
                    int idEquipo  = Integer.parseInt(idEquipoTexto);

                    //validación de existencia
                    if (!jugadorRepository.existsById((long) idJugador)) {
                        throw new IllegalArgumentException("No existe Jugador con id=" + idJugador);
                    }
                    if (!equipoRepository.existsById(idEquipo)) {
                        throw new IllegalArgumentException("No existe Equipo con id=" + idEquipo);
                    }

                    LocalDate fechaInicio = LocalDate.parse(fechaInicioTxt, formatoFecha);
                    Integer dorsal = (dorsalTexto == null || dorsalTexto.isBlank())
                            ? null
                            : Integer.parseInt(dorsalTexto);
                    LocalDate fechaFin = (fechaFinTexto == null || fechaFinTexto.isBlank())
                            ? null
                            : LocalDate.parse(fechaFinTexto, formatoFecha);

                    //Upsert por clave compuesta idJugador, idEquipo, fechaInicio
                    ContratoId contratoId = new ContratoId(idJugador, idEquipo, fechaInicio, fechaFin);

                    boolean yaExistia = contratoRepository.existsById(contratoId);

                    Contrato contrato = contratoRepository.findById(contratoId).orElseGet(() -> {
                        Contrato nuevo = new Contrato();
                        nuevo.setId(contratoId);
                        return nuevo;
                    });

                    //asigna los campos
                    contrato.setDorsal(dorsal != null ? dorsal : 0);


                    //guarda
                    contratoRepository.save(contrato);

                    //contadores
                    if (yaExistia) {
                        actualizados++;
                    } else {
                        insertados++;
                    }

                } catch (Exception errorEnFila) {
                    //error de esta fila y continuamos
                    resultado.getErrores().add("Fila " + fila.getRecordNumber() + ": " + errorEnFila.getMessage());
                }
            }

            //Totales de este intento con el delimitador elegido
            resultado.setTotalFilas(totalFilas);
            resultado.setInsertados(insertados);
            resultado.setActualizados(actualizados);
            return resultado;

        } catch (Exception e) {
            //error al parsear con ese delimitador
            resultado.getErrores().add("Fallo con delimitador '" + delimitador + "': " + e.getMessage());
            return resultado;
        }
    }

    //devuelve el primer valor no vacío encontrado entre los nombres de cabecera dados
    //Si ninguna cabecera existe o tiene contenido me devuelve null
    private static String valorSeguro(CSVRecord fila, String... cabeceras) {
        for (String cabecera : cabeceras) {
            try {
                String valor = fila.get(cabecera);
                if (valor != null && !valor.isBlank()) {
                    return valor.trim();
                }
            } catch (IllegalArgumentException ignorado) {
                //la cabecera no existe en esta fila pues  probamos con la siguiente variante
            }
        }
        return null;
    }
}
