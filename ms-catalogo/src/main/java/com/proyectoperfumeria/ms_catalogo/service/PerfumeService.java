package com.proyectoperfumeria.ms_catalogo.service;


import com.proyectoperfumeria.ms_catalogo.dto.PerfumeRequestDTO;
import com.proyectoperfumeria.ms_catalogo.dto.PerfumeResponseDTO;
import com.proyectoperfumeria.ms_catalogo.model.Perfume;
import com.proyectoperfumeria.ms_catalogo.repository.PerfumeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class PerfumeService {

    private final PerfumeRepository perfumeRepository;

    private PerfumeResponseDTO maptoDTO(Perfume perfume) {
        return new PerfumeResponseDTO(perfume.getId(),
                perfume.getNombre(),
                perfume.getMarca(),
                perfume.getPrecio(),
                perfume.getDescripcion(),
                perfume.getStock()
        );
    }
    public List<PerfumeResponseDTO> obtenerTodosLosPerfumes(){
        List<Perfume> perfumes = perfumeRepository.findAll();
        List<PerfumeResponseDTO> perfumesDTO = new ArrayList<>();
        for(Perfume perfume : perfumes){
            perfumesDTO.add(maptoDTO(perfume));
        }
        return perfumesDTO;
    }

    public Optional <PerfumeResponseDTO> obtenerPerfumePorId(Long id){
        return perfumeRepository.findById(id).map(this::maptoDTO);
    }

    public PerfumeResponseDTO guardarPerfume(PerfumeRequestDTO requestDTO){

        // Validación de Negocio ---
        if (requestDTO.getPrecio() == null || requestDTO.getPrecio() <= 0) {
            throw new RuntimeException("Error: El precio del perfume debe ser mayor a cero.");
        }
        if (requestDTO.getStock() == null || requestDTO.getStock() < 0) {
            throw new RuntimeException("Error: El stock inicial no puede ser negativo.");
        }
        Perfume perfume = new Perfume();
        perfume.setNombre(requestDTO.getNombre());
        perfume.setMarca(requestDTO.getMarca());
        perfume.setPrecio(requestDTO.getPrecio());
        perfume.setDescripcion(requestDTO.getDescripcion());
        perfume.setStock(requestDTO.getStock());

        Perfume perfumeGuardado = perfumeRepository.save(perfume);
        return maptoDTO(perfumeGuardado);
    }

    public void descontarStock(Long perfumeId, Integer cantidad) {
        // SEGURIDAD ---
        if (cantidad == null || cantidad <= 0) {
            throw new RuntimeException("Error: La cantidad a descontar debe ser mayor a cero.");
        }
        Perfume perfume = perfumeRepository.findById(perfumeId)
                .orElseThrow(() -> new RuntimeException("Error: El perfume no existe."));
        if (perfume.getStock() < cantidad) {
            throw new RuntimeException("Error: Stock insuficiente para " + perfume.getNombre());
        }
        perfume.setStock(perfume.getStock() - cantidad);
        perfumeRepository.save(perfume);
    }

}
