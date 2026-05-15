package com.proyectoperfumeria.ms_analisis_experiencia.client;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-usuarios", url = "https://localhost:8081")
public interface UsuarioFeignClient {

    @GetMapping("/api/usuarios/{id}")
    Object validarUsuario(@PathVariable("id") Long id);
}
