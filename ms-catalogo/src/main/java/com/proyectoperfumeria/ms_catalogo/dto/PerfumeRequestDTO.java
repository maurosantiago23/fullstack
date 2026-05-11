package com.proyectoperfumeria.ms_catalogo.dto;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PerfumeRequestDTO {

    @NotBlank(message = "El nombre del perfumes es obligatorio")
    private String nombre;

    @NotNull(message = "La marca del perfume es obligatoria")
    private String marca;

    @NotNull(message = "El precio del perfume es obligatorio")
    @Min(value = 0, message = "El precio no puede ser 0 o negativo")
    private Integer precio;

    private String descripcion;

    @NotNull(message = "El stock del perfume es obligatorio")
    @Min(value = 0, message = "El stock no puede ser 0 o negativo")
    private Integer stock;

}
