package com.proyectoperfumeria.ms_catalogo.controller;


import com.proyectoperfumeria.ms_catalogo.dto.PerfumeRequestDTO;
import com.proyectoperfumeria.ms_catalogo.dto.PerfumeResponseDTO;
import com.proyectoperfumeria.ms_catalogo.service.PerfumeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/perfumes")
@RequiredArgsConstructor
public class PerfumeController {

    private final PerfumeService perfumeService;

    @GetMapping
    public ResponseEntity<List<PerfumeResponseDTO>> ListarPerfumes(){
        return ResponseEntity.ok(perfumeService.obtenerTodosLosPerfumes());
    }

    @GetMapping("/{id}" )
    public ResponseEntity<PerfumeResponseDTO> obtenerPerfumeId(@PathVariable Long id){
        return perfumeService.obtenerPerfumePorId(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<PerfumeResponseDTO> crearPerfume(@RequestBody @Valid PerfumeRequestDTO requestDTO){
        PerfumeResponseDTO nuevo = perfumeService.guardarPerfume(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }

    @PutMapping("/{id}/stock/{cantidad}")
    public ResponseEntity<Void> descontarStock(
            @PathVariable Long id,
            @PathVariable Integer cantidad) {
        perfumeService.descontarStock(id, cantidad);
        return ResponseEntity.ok().build();
    }
}
