package com.example.cronlimpieza;

import com.example.cronlimpieza.service.LogTareaService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.time.LocalDateTime;

@SpringBootApplication
@EnableScheduling
public class CronlimpiezaApplication {

    @Bean
    public CommandLineRunner init(LogTareaService service) {
        return args -> {


            //son fechas ficticiaaas!!!
            //log de hoy
            service.crearLog("Log de hoy mismo");

            //log de hace 5 días
            service.crearLogConFecha("Log de hace 5 días",
                    LocalDateTime.now().minusDays(5));

            //log de hace 15 días
            service.crearLogConFecha("Log de hace 15 días",
                    LocalDateTime.now().minusDays(15));

            //log de hace 40 días
            service.crearLogConFecha("Log de hace 40 días",
                    LocalDateTime.now().minusDays(40));

            //log de hace 100 días
            service.crearLogConFecha("Log de hace 100 días",
                    LocalDateTime.now().minusDays(100));
        };
    }

    public static void main(String[] args) {
		SpringApplication.run(CronlimpiezaApplication.class, args);
	}

}
