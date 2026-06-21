package com.proyectoperfumeria.ms_tienda_oferta.config;


import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Configuration;

//  URL para probar Swagger en el navegador: http://localhost:8083/swagger-ui/index.html
@Configuration
public class SwaggerConfigTiendaOferta {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API Tienda y Ofertas")
                        .version("1.0")
                        .description("Motor de ventas, validación de transacciones y descuentos."));
    }
}
