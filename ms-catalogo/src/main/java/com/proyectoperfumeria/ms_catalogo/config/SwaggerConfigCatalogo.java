package com.proyectoperfumeria.ms_catalogo.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//  URL para probar Swagger en el navegador: http://localhost:8082/swagger-ui/index.html
@Configuration
public class SwaggerConfigCatalogo {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API Catálogo de Perfumes")
                        .version("1.0")
                        .description("Gestión de inventario y productos de la perfumería."));
    }
}
