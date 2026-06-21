package com.proyectoperfumeria.ms_analisis_experiencia.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.hateoas.EntityModel;
import com.proyectoperfumeria.ms_analisis_experiencia.dto.ResenaRequestDTO;
import com.proyectoperfumeria.ms_analisis_experiencia.dto.ResenaResponseDTO;
import com.proyectoperfumeria.ms_analisis_experiencia.service.ExperienciaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/experiencia")
@RequiredArgsConstructor
public class ExperienciaController {

    private final ExperienciaService experienciaService;

    @PostMapping("/resenas")
    public ResponseEntity<ResenaResponseDTO> crearResena(@RequestBody @Valid ResenaRequestDTO resenaRequestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(experienciaService.crearResenaDePerfume(resenaRequestDTO));
    }

    @GetMapping("/resenas")
    public ResponseEntity<List<ResenaResponseDTO>> listarResenas() {
        return ResponseEntity.ok(experienciaService.obtenerResenas());
    }

    @GetMapping("/resenas/{id}")
    public ResponseEntity<EntityModel<ResenaResponseDTO>> obtenerResenaId(@PathVariable Long id) {
        return experienciaService.obtenerResenaPorId(id)
                .map(dto -> {
                    EntityModel<ResenaResponseDTO> recurso = EntityModel.of(dto);
                    recurso.add(linkTo(methodOn(this.getClass()).obtenerResenaId(id)).withSelfRel());
                    return ResponseEntity.ok(recurso);
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
