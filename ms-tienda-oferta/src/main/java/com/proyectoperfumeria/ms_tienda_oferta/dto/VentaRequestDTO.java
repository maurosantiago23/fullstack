package com.proyectoperfumeria.ms_tienda_oferta.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class VentaRequestDTO {

    @NotNull(message = "El ID del usuario es obligatorio")
    private Long usuarioId;

    @NotNull(message = "El ID del perfume es obligatorio")
    private Long perfumeId;

    @NotNull(message = "El total es obligatorio")
    @Min(value = 0, message = "El total no puede ser negativo")
    private Integer total;

    @NotBlank(message = "El método de pago es obligatorio")
    private String metodoPago;
}
