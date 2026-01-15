package com.example.cronlimpieza.service;

import com.example.cronlimpieza.model.EjecucionTarea;
import com.example.cronlimpieza.model.LogTarea;
import com.example.cronlimpieza.repository.EjecucionTareaRepository;
import com.example.cronlimpieza.repository.LogTareaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class LogTareaService {

    private static final Logger log = LoggerFactory.getLogger(LogTareaService.class);

    private final LogTareaRepository logTareaRepository;
    private final EjecucionTareaRepository ejecucionTareaRepository;

    @Value("${tareas.limpieza.retencion-dias}")
    private int diasRetencion;

    public LogTareaService(LogTareaRepository logTareaRepository,
                           EjecucionTareaRepository ejecucionTareaRepository) {
        this.logTareaRepository = logTareaRepository;
        this.ejecucionTareaRepository = ejecucionTareaRepository;
    }

    public void crearLog(String fechaFicticia) {
        LogTarea log = new LogTarea(fechaFicticia); // fecha = ahora
        logTareaRepository.save(log);
    }

    //ahora usa la fecha que le doy desde el main
    public void crearLogConFecha(String fechaFicticia, LocalDateTime fecha) {
        LogTarea log = new LogTarea(fechaFicticia, fecha);
        logTareaRepository.save(log);
    }

    @Scheduled(cron = "0 */1 * * * *") // cada minuto mientras pruebas
    public void limpiarLogsAntiguos() {

        //Crear registro de auditoría al inicio
        EjecucionTarea ejecucion = new EjecucionTarea(LocalDateTime.now());

        try {
            //Lógica de limpieza
            LocalDateTime fechaLimite =
                    LocalDateTime.now().minus(diasRetencion, ChronoUnit.DAYS);

            List<LogTarea> antiguos =
                    logTareaRepository.findByFechaCreacionBefore(fechaLimite);

            if (!antiguos.isEmpty()) {
                logTareaRepository.deleteAll(antiguos);
            }

            //Rellenar datos de éxito
            ejecucion.setRegistrosBorrados((long) antiguos.size());
            ejecucion.setEstado("OK");
            ejecucion.setMensaje("Limpieza ok");

            log.info("borrados {} logs viejos de {} días)",
                    antiguos.size(), diasRetencion);

        } catch (Exception e) {
            //Si falla, marcamos ERROR
            ejecucion.setEstado("ERROR");
            ejecucion.setMensaje("Error en limpieza: " + e.getMessage());

            log.error("Error en limpieza de logs: {}", e.getMessage(), e);
        }

        //Fecha fin + guardar auditoría
        ejecucion.setFechaFin(LocalDateTime.now());
        ejecucionTareaRepository.save(ejecucion);
    }
}

