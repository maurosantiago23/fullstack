package com.proyectoperfumeria.ms_catalogo.service;


import com.proyectoperfumeria.ms_catalogo.dto.PerfumeResponseDTO;
import com.proyectoperfumeria.ms_catalogo.model.Perfume;
import com.proyectoperfumeria.ms_catalogo.repository.PerfumeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
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

    public PerfumeResponseDTO guardarPerfume(PerfumeResponseDTO perfumeResponseDTO){
        Perfume perfume = new Perfume();
        perfume.setNombre(perfumeResponseDTO.getNombre());
        perfume.setMarca(perfumeResponseDTO.getMarca());
        perfume.setPrecio(perfumeResponseDTO.getPrecio());
        perfume.setDescripcion(perfumeResponseDTO.getDescripcion());
        perfume.setStock(perfumeResponseDTO.getStock());

        Perfume perfumeGuardado = perfumeRepository.save(perfume);
        return maptoDTO(perfumeGuardado);
    }

}
