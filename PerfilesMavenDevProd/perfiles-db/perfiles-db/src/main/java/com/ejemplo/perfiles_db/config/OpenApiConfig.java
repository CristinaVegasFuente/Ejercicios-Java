package com.ejemplo.perfiles_db.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Usuarios - Perfiles Maven")
                        .version("1.0.0")
                        .description("API para practicar perfiles Maven y jugar con Swagger.")
                        .contact(new Contact()
                                .name("Cristina Dev")
                                .email("cristina@example.com")
                                .url("https://github.com/cristina")
                        )
                        .license(new License()
                                .name("Licencia Pro")
                                .url("https://miempresa.com/licencia")
                        )
                );
    }
}
