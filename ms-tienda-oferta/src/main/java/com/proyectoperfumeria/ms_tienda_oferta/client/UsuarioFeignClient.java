package com.proyectoperfumeria.ms_tienda_oferta.client;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-usuarios", url = "http://localhost:8081") //corregido https a http -_-
public interface UsuarioFeignClient {

    @GetMapping("/api/v1/usuarios/{id}")
    Object validarUsuario(@PathVariable("id") Long id);
}
