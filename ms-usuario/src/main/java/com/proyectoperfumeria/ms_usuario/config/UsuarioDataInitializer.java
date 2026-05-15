package com.proyectoperfumeria.ms_usuario.config;

import com.proyectoperfumeria.ms_usuario.model.Usuario;
import com.proyectoperfumeria.ms_usuario.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UsuarioDataInitializer implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception{
        if(usuarioRepository.count() > 0){
            log.info("--- Usuarios ya detectados en la base de datos. ---");
            return;
        }

        log.info("--- Cargando usuarios iniciales... ---");

        //usuario ADMIN
        Usuario admin = new Usuario();
        admin.setNombreCompleto("Administrador Perfumeria");
        admin.setEmail("admin@perfumeria.cl");
        admin.setPassword(passwordEncoder.encode("admin123"));
        admin.setRol("ADMIN");
        usuarioRepository.save(admin);

        //usuario CLIENTE
        Usuario cliente = new Usuario();
        cliente.setNombreCompleto("Cliente Viña");
        cliente.setEmail("cliente@gmail.com");
        cliente.setPassword(passwordEncoder.encode("cliente123"));
        cliente.setRol("CLIENTE");
        usuarioRepository.save(cliente);
    }
}
