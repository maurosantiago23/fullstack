package com.proyectoperfumeria.ms_tienda_oferta.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VentaResponseDTO {

    private Long id;
    private Long usuarioId;
    private Long perfumeId;
    private LocalDateTime fechaVenta;
    private Integer total;
    private String metodoPago;
    private String estadoPago;
}
