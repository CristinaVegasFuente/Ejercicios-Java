package com.example.demofixeddelay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DemofixeddelayApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemofixeddelayApplication.class, args);
	}

}
