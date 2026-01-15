package com.ejemplo.perfiles_db;

import com.ejemplo.perfiles_db.model.Usuario;
import com.ejemplo.perfiles_db.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class PerfilesDbApplication {

    //trae el perfil activo solo para mostrarlo en consola
    @Value("${spring.profiles.active:default}")
    private String activeProfile;

    public static void main(String[] args) {
        SpringApplication.run(PerfilesDbApplication.class, args);
    }

//    @Bean
//    public CommandLineRunner initData(UsuarioRepository usuarioRepository) {
//        return args -> {
//            String nombreUsuario = "Usuario DESCONOCIDO";
//
//            if ("dev".equalsIgnoreCase(activeProfile)) {
//                nombreUsuario = "Usuario DEV";
//            } else if ("prod".equalsIgnoreCase(activeProfile)) {
//                nombreUsuario = "Usuario PROD";
//            }
//
//            Usuario usuario = new Usuario(nombreUsuario);
//            usuarioRepository.save(usuario);
//
//            System.out.println("==================================================");
//            System.out.println("Perfil activo: " + activeProfile);
//            System.out.println("Se ha guardado en BD un usuario: " + nombreUsuario);
//            System.out.println("==================================================");
//        };
//    }
}