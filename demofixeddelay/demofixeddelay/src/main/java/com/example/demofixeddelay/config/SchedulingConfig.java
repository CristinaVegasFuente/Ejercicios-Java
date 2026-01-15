package com.example.demofixeddelay.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
//Spring usará este scheduler para tus @Scheduled
//Podrás tener hasta 5 tareas programadas ejecutándose a la vez
@Configuration
public class SchedulingConfig {

    @Bean
    public ThreadPoolTaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(3);                  //3 hilos en el pool
        scheduler.setThreadNamePrefix("cron-");    //Para verlos en logs
        scheduler.initialize();
        return scheduler;
    }
}