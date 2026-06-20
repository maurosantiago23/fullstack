package com.proyectoperfumeria.ms_analisis_experiencia.service;


import com.proyectoperfumeria.ms_analisis_experiencia.client.CatalogoFeignClient;
import com.proyectoperfumeria.ms_analisis_experiencia.client.UsuarioFeignClient;
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
    private final UsuarioFeignClient usuarioFeignClient;
    private final CatalogoFeignClient catalogoFeignClient;

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

        if (resenaRequestDTO.getCalificacion() == null || resenaRequestDTO.getCalificacion() < 1 || resenaRequestDTO.getCalificacion() > 5) {
            throw new RuntimeException("Operación rechazada: La calificación debe ser un valor entre 1 y 5.");
        }

        usuarioFeignClient.validarUsuario(resenaRequestDTO.getUsuarioId());
        catalogoFeignClient.validarPerfume(resenaRequestDTO.getPerfumeId());

        // Bloqueo de Spam ---
        if (resenaRepository.existsByUsuarioIdAndPerfumeId(resenaRequestDTO.getUsuarioId(), resenaRequestDTO.getPerfumeId())) {
            throw new RuntimeException("Operación rechazada: El usuario ya ha publicado una reseña para este perfume.");
        }

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
