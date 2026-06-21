package com.proyectoperfumeria.ms_usuario.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


//  URL para probar Swagger en el navegador: http://localhost:8081/swagger-ui/index.html

@Configuration
public class SwaggerConfigUsuario {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API Usuarios - Perfumería 2026")
                        .version("1.0")
                        .description("Documentación oficial de los endpoints para la gestión de usuarios y clientes de la tienda."));
    }
}
