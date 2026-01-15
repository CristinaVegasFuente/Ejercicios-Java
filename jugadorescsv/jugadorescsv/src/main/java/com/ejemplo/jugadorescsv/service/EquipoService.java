package com.ejemplo.jugadorescsv.service;

import com.ejemplo.jugadorescsv.model.Equipo;
import com.ejemplo.jugadorescsv.repository.EquipoRepository;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

//servicio para importar equipos desde un archivo csv
@Service
public class EquipoService {

    //repositorio JPA para la entidad equipo
    private final EquipoRepository equipoRepository;

    public EquipoService(EquipoRepository equipoRepository) {
        this.equipoRepository = equipoRepository;
    }

    @Transactional
    public ImportResult importarCsv(MultipartFile archivoCsv) {
        final ImportResult resultado = new ImportResult();

        //validación del archivo si esta vacio para
        if (archivoCsv == null || archivoCsv.isEmpty()) {
            resultado.getErrores().add("csv vacio.");
            return resultado;
        }

        try {
            //lee el contenido completo del archivo para probar distintos delimitadores
            final String contenidoCsv;
            try (BufferedReader lector =
                         new BufferedReader(new InputStreamReader(archivoCsv.getInputStream(), StandardCharsets.UTF_8))) {

                final StringBuilder acumulador = new StringBuilder();
                String linea;
                while ((linea = lector.readLine()) != null) {
                    acumulador.append(linea).append('\n');
                }
                contenidoCsv = acumulador.toString();
            }

            //delimitador punto y coma
            ImportResult resultadoPuntoYComa = intentarParsearEImportar(contenidoCsv, ';');

            //si no hubo inserciones ni actualizaciones prueba con la coma
            if ((resultadoPuntoYComa.getInsertados() + resultadoPuntoYComa.getActualizados()) == 0) {
                ImportResult resultadoComa = intentarParsearEImportar(contenidoCsv, ',');

                //si con la coma hay insercciones es el correcto
                if ((resultadoComa.getInsertados() + resultadoComa.getActualizados())
                        > (resultadoPuntoYComa.getInsertados() + resultadoPuntoYComa.getActualizados())) {
                    return resultadoComa;
                }
            }

            //si con el punto y coma ya hubo cambios devolvemos el primer resultado.
            return resultadoPuntoYComa;

        } catch (Exception e) {
            //error no asociado a una fila concreta
            resultado.getErrores().add("Error general procesando el CSV: " + e.getMessage());
            return resultado;
        }
    }

    private ImportResult intentarParsearEImportar(String contenidoCsv, char delimitador) {
        final ImportResult resultado = new ImportResult();

        try (CSVParser parser = CSVParser.parse(
                contenidoCsv,
                CSVFormat.DEFAULT.builder()
                        //usa la primera línea como cabeceras
                        .setHeader()
                        //omite la fila de cabecera al iterar
                        .setSkipHeaderRecord(true)
                        //quita espacios en blanco
                        .setTrim(true)
                        //delimitador a probar ; o ,
                        .setDelimiter(delimitador)
                        .build()
        )) {
            int totalFilas = 0;
            int totalInsertados = 0;
            int totalActualizados = 0;

            //itera cada fila de datos sin contar con la cabecera para registrar los datos
            for (CSVRecord fila : parser) {
                totalFilas++;
                try {
                    //lectura de columnas
                    final String idComoTexto        = valorSeguro(fila, "id");
                    final String nombre             = valorSeguro(fila, "nombre");
                    final String pais               = valorSeguro(fila, "pais", "país");
                    final String fundacionComoTexto = valorSeguro(fila, "fundacion", "fundación");
                    final String cif                = valorSeguro(fila, "cif");

                    //Validación mínima el id es obligatorio
                    if (idComoTexto == null || idComoTexto.isBlank()) {
                        throw new IllegalArgumentException("La columna 'id' es obligatoria.");
                    }

                    //convierte a int el id
                    final int id = Integer.parseInt(idComoTexto);

                    Integer anioFundacion = null;
                    if (fundacionComoTexto != null && !fundacionComoTexto.isBlank()) {
                        anioFundacion = Integer.parseInt(fundacionComoTexto);
                    }

                    //si existe el equipo con el id actualiza
                    //si no existe lo crea y lo guarda
                    final boolean yaExiste = equipoRepository.existsById(id);

                    final Equipo equipo = equipoRepository.findById(id).orElseGet(() -> {
                        Equipo nuevo = new Equipo();
                        nuevo.setId(id);
                        return nuevo;
                    });

                    //asigna valores si estan en el csv
                    if (nombre != null) equipo.setNombre(nombre);
                    if (pais != null) equipo.setPais(pais);
                    if (cif != null) equipo.setCif(cif);

                    //cuando hacemos un alta nueva el año de fundacion esta a cero
                    if (anioFundacion != null) {
                        equipo.setFundacion(anioFundacion);
                    } else if (!yaExiste) {
                        equipo.setFundacion(0);
                    }

                    //guarda el cambio
                    equipoRepository.save(equipo);

                    if (yaExiste) {
                        totalActualizados++;
                    } else {
                        totalInsertados++;
                    }

                } catch (Exception errorEnFila) {
                    //captura el error de esta fila y continuamos con el resto
                    resultado.getErrores().add("Fila " + fila.getRecordNumber() + ": " + errorEnFila.getMessage());
                }
            }

            //totales para este intento con el delimitador indicado
            resultado.setTotalFilas(totalFilas);
            resultado.setInsertados(totalInsertados);
            resultado.setActualizados(totalActualizados);
            return resultado;

        } catch (Exception e) {
            //error al parsear o configurar el parser con este delimitador
            resultado.getErrores().add("Fallo con delimitador '" + delimitador + "': " + e.getMessage());
            return resultado;
        }
    }

    private static String valorSeguro(CSVRecord fila, String... cabeceras) {
        for (String cabecera : cabeceras) {
            try {
                String valor = fila.get(cabecera);
                if (valor != null && !valor.isBlank()) {
                    return valor.trim();
                }
            } catch (IllegalArgumentException ignored) {
                //la cabecera no existe en este csv pues probamos la siguiente
            }
        }
        return null;
    }
}
