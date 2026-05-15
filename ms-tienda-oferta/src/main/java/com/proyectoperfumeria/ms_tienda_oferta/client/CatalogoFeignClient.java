package com.proyectoperfumeria.ms_tienda_oferta.client;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(name = "ms-catalogo", url = "http://localhost:8082")
public interface CatalogoFeignClient {

    @GetMapping("/api/v1/perfumes/{id}")
    Object validarPerfume(@PathVariable("id") Long id);


    @PutMapping("/api/v1/perfumes/{id}/stock/{cantidad}")
    void descontarStock(@PathVariable("id") Long id, @PathVariable("cantidad") Integer cantidad);
}
