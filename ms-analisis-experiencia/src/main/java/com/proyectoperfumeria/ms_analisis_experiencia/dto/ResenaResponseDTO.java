package com.proyectoperfumeria.ms_analisis_experiencia.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResenaResponseDTO {

    private Long id;
    private Long usuarioId;
    private Long perfumeId;
    private Integer calificacion;
    private String comentario;
    private LocalDateTime fechaPublicacion;
}
