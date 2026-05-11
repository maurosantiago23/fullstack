package com.proyectoperfumeria.ms_tienda_oferta.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TiendaResponseDTO {

    private Long id;
    private String nombre;
    private String ubicacion;
}
