package com.proyectoperfumeria.ms_tienda_oferta.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OfertaRequestDTO {

    @NotNull(message = "El ID del perfume es obligatorio")
    private Long perfumeId;

    @NotNull(message = "El ID de la tienda es obligatorio")
    private Long tiendaId;

    @NotNull(message = "El precio de la oferta del perfume es obligatorio")
    @Min(value = 0, message = "El precio no puede ser 0 o negativo")
    private Integer precioOferta;
}
