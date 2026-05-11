package com.proyectoperfumeria.ms_tienda_oferta.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TiendaRequestDTO {

    @NotBlank(message = "El nombre de la tienda es obligatorio")
    private String nombre;

    @NotBlank(message = "La ubicacion de la tienda es obligatoria")
    private String ubicacion;
}
