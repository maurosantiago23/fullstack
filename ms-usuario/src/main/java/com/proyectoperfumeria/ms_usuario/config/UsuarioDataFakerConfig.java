package com.proyectoperfumeria.ms_usuario.config;

import net.datafaker.Faker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.Locale;

@Configuration
public class UsuarioDataFakerConfig {

    @Bean
    public Faker faker() {
        return new Faker(new Locale("es", "CL"));
    }
}
