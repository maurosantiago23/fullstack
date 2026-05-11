package com.proyectoperfumeria.ms_catalogo.dto;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PerfumeResponseDTO{

    private Long id;
    private String nombre;
    private String marca;
    private Integer precio;
    private String descripcion;
    private Integer stock;

}
