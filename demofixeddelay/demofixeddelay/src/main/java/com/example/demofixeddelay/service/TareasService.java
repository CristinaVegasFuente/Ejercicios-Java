package com.example.demofixeddelay.service;


import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalTime;

@Service
public class TareasService {

    @Scheduled(fixedDelay = 5000)
    public void tareaPesada() {
        String hilo = Thread.currentThread().getName();
        System.out.println("[" + LocalTime.now() + "] INICIO tareaPesada en hilo: " + hilo);
        try {
            //simulacion trabajo pesado de 7 segundos
            Thread.sleep(7000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println("[" + LocalTime.now() + "] FIN tareaPesada en hilo: " + hilo);
    }

    @Scheduled(fixedDelay = 3000)
    public void tareaLigera() {
        String hilo = Thread.currentThread().getName();
        System.out.println("[" + LocalTime.now() + "] >>> INICIO tareaLigera en hilo: " + hilo);
        try {
            //simulacion de trabajo ligero 1 segundo
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println("[" + LocalTime.now() + "] >>> FIN tareaLigera en hilo: " + hilo);
    }
}


//    //para ver cuántas veces se ejecuta
//    private int contador = 0;
//
//    @Scheduled(fixedDelay = 5000) // 5000 milisegundos = 5 segundos
//    public void tareaConFixedDelay() {
//        contador++;
//        System.out.println("[" + LocalDateTime.now() + "] Ejecutando tareaConFixedDelay. Veces: " + contador);
//
//        try {
//            // Simulamos que la tarea tarda 3 segundos en hacerse
//            Thread.sleep(3000);
//        } catch (InterruptedException e) {
//            Thread.currentThread().interrupt();
//        }
//    }
//}
/*
La tarea se ejecuta
Tarda unos 3 segundos (por el sleep)
Cuando termina, Spring espera 5 segundos (fixedDelay = 5000)
Luego la vuelve a ejecutar
Así en bucle infinito hasta que pares la app
3 segundos que dura la ejecucion + 5 segundos de parada en total 8 segundos
*/