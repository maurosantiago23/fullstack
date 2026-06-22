package com.proyectoperfumeria.ms_analisis_experiencia.client;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-usuarios", url = "${clients.ms-usuario.url:http://localhost:8081}") //corregido a una url configurable
public interface UsuarioFeignClient {

    @GetMapping("/api/v1/usuarios/{id}")
    Object validarUsuario(@PathVariable("id") Long id);
}
