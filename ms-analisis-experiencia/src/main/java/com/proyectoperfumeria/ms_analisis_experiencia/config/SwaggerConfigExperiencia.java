package com.proyectoperfumeria.ms_analisis_experiencia.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//  URL para probar Swagger en el navegador: http://localhost:8084/swagger-ui/index.html
@Configuration
public class SwaggerConfigExperiencia {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API Análisis de Experiencia")
                        .version("1.0")
                        .description("Gestión de reseñas y calificaciones de clientes."));
    }
}
