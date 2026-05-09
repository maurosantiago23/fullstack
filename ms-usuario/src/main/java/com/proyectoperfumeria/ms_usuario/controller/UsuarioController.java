package com.proyectoperfumeria.ms_usuario.controller;


import com.proyectoperfumeria.ms_usuario.dto.UsuarioRequestDTO;
import com.proyectoperfumeria.ms_usuario.dto.UsuarioResponseDTO;
import com.proyectoperfumeria.ms_usuario.model.Usuario;
import com.proyectoperfumeria.ms_usuario.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> listar(){
        return ResponseEntity.ok(usuarioService.obtenerTodosUsuarios());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> buscarPorId(@PathVariable Long id){
        return usuarioService.obtenerUsuarioPorId(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> crearUsuario(@RequestBody @Valid Usuario dto){
        UsuarioResponseDTO nuevo = usuarioService.guardarUsuario(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }
}
