package com.proyectoperfumeria.ms_usuario.config;

import com.proyectoperfumeria.ms_usuario.model.Usuario;
import com.proyectoperfumeria.ms_usuario.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.datafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class UsuarioDataInitializer implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final Faker faker;

    @Override
    public void run(String... args) throws Exception {
        if(usuarioRepository.count() > 0){
            log.info("--- Usuarios ya detectados en la base de datos. ---");
            return;
        }

        log.info("--- Cargando usuarios iniciales... ---");

        // 1. Usuario ADMIN (Manual para pruebas de Postman/Swagger)
        Usuario admin = new Usuario();
        admin.setNombreCompleto("Administrador Perfumeria");
        admin.setEmail("admin@perfumeria.cl");
        admin.setPassword(passwordEncoder.encode("admin123"));
        admin.setRol("ADMIN");
        usuarioRepository.save(admin);

        // 2. Usuario CLIENTE (Manual para pruebas de Postman/Swagger)
        Usuario cliente = new Usuario();
        cliente.setNombreCompleto("Cliente Viña");
        cliente.setEmail("cliente@gmail.com");
        cliente.setPassword(passwordEncoder.encode("cliente123"));
        cliente.setRol("CLIENTE");
        usuarioRepository.save(cliente);

        // 3. Generación masiva con Datafaker
        log.info("--- Generando 20 clientes aleatorios con Datafaker... ---");
        List<Usuario> usuariosFalsos = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            Usuario fakeUser = new Usuario();

            fakeUser.setNombreCompleto(faker.name().fullName());
            fakeUser.setEmail(faker.internet().emailAddress());

            // Genera contraseña aleatoria (8-16 caracteres, con mayúsculas y símbolos)
            String claveAleatoria = faker.internet().password(8, 16, true, true);
            fakeUser.setPassword(passwordEncoder.encode(claveAleatoria));

            fakeUser.setRol("CLIENTE");

            usuariosFalsos.add(fakeUser);
        }

        // Guardado en lote optimizado
        usuarioRepository.saveAll(usuariosFalsos);
        log.info("--- ¡Base de datos poblada exitosamente con 22 usuarios en total! ---");
    }
}
