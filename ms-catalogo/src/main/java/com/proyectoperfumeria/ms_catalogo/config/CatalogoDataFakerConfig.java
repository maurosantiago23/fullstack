package com.proyectoperfumeria.ms_catalogo.config;

import net.datafaker.Faker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.Locale;

@Configuration
public class CatalogoDataFakerConfig {

    @Bean
    public Faker faker() {
        return new Faker(new Locale("es", "CL"));
    }
}
