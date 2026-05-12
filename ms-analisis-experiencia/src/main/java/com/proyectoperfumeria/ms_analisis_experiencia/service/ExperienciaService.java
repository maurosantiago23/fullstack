package com.proyectoperfumeria.ms_analisis_experiencia.service;


import com.proyectoperfumeria.ms_analisis_experiencia.dto.ResenaRequestDTO;
import com.proyectoperfumeria.ms_analisis_experiencia.dto.ResenaResponseDTO;
import com.proyectoperfumeria.ms_analisis_experiencia.model.Resena;
import com.proyectoperfumeria.ms_analisis_experiencia.repository.ResenaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExperienciaService {

    private final ResenaRepository resenaRepository;


    private ResenaResponseDTO mapToResenaDTO(Resena resena) {
        return new ResenaResponseDTO(
                resena.getId(),
                resena.getUsuarioId(),
                resena.getPerfumeId(),
                resena.getCalificacion(),
                resena.getComentario(),
                resena.getFechaPublicacion()
        );
    }

    public ResenaResponseDTO crearResenaDePerfume(ResenaRequestDTO resenaRequestDTO) {
        Resena resena = new Resena();
        resena.setUsuarioId(resenaRequestDTO.getUsuarioId());
        resena.setPerfumeId(resenaRequestDTO.getPerfumeId());
        resena.setCalificacion(resenaRequestDTO.getCalificacion());
        resena.setComentario(resenaRequestDTO.getComentario());
        resena.setFechaPublicacion(LocalDateTime.now());

        Resena guardada = resenaRepository.save(resena);
        return mapToResenaDTO(guardada);
    }

    public List<ResenaResponseDTO> obtenerResenas() {
        List<Resena> resenas = resenaRepository.findAll();
        List<ResenaResponseDTO> dtos = new ArrayList<>();

        for (Resena resena : resenas) {
            dtos.add(mapToResenaDTO(resena));
        }

        return dtos;
    }

}
