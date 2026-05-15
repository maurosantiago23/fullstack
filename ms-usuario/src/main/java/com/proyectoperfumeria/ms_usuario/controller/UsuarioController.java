package com.proyectoperfumeria.ms_usuario.controller;


import com.proyectoperfumeria.ms_usuario.dto.LoginRequestDTO;
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

    private final UsuarioService usuarioService;

    @PostMapping("/registro")
    public ResponseEntity<UsuarioResponseDTO> registrarUsuario(@RequestBody @Valid UsuarioRequestDTO usuarioDTO) {
        return ResponseEntity.ok(usuarioService.registrarCliente(usuarioDTO)); //Agregué Valid y cambie los parametros a RequestDTO y DTO
    }

    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> crearUsuario(@RequestBody @Valid Usuario dto){
        UsuarioResponseDTO nuevo = usuarioService.crearUsuarioAdmin(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }

    @PostMapping("/login")
    public ResponseEntity<UsuarioResponseDTO> login(@RequestBody LoginRequestDTO loginDTO) {
        return ResponseEntity.ok(usuarioService.loginManual(loginDTO));
    }

    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> listar(){
        return ResponseEntity.ok(usuarioService.obtenerTodosUsuarios());
    }


    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> buscarPorId(@PathVariable Long id){
        return usuarioService.obtenerUsuarioPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
