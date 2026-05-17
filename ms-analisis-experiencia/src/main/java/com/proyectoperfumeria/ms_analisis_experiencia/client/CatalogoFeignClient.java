package com.proyectoperfumeria.ms_analisis_experiencia.client;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-catalogo", url = "http://localhost:8082")
public interface CatalogoFeignClient {

    @GetMapping("/api/v1/perfumes/{id}")
    Object validarPerfume(@PathVariable("id") Long id);

}
