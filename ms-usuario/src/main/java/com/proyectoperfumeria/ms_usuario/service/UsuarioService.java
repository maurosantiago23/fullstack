package com.proyectoperfumeria.ms_usuario.service;

import com.proyectoperfumeria.ms_usuario.dto.UsuarioResponseDTO;
import com.proyectoperfumeria.ms_usuario.model.Usuario;
import com.proyectoperfumeria.ms_usuario.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioResponseDTO maptoDTO(Usuario usuario){
        return new UsuarioResponseDTO(usuario.getId(),
                usuario.getNombreCompleto(),
                usuario.getEmail(),
                usuario.getRol());
    }

    public List<UsuarioResponseDTO> obtenerTodosUsuarios(){
        List<Usuario> listaUsuarios = usuarioRepository.findAll();
        List<UsuarioResponseDTO> listaUsuariosDTO = new ArrayList<>();

        for(Usuario usuario : listaUsuarios){
            listaUsuariosDTO.add(maptoDTO(usuario));
        }
        return listaUsuariosDTO;
    }

    public Optional<UsuarioResponseDTO> obtenerUsuarioPorId(Long id){
        return usuarioRepository.findById(id).map(this::maptoDTO);
    }

    public UsuarioResponseDTO guardarUsuario(Usuario dto) {

        if(usuarioRepository.findByEmail(dto.getEmail()).isPresent()){
            throw new RuntimeException("El email que ingreso ya se encuentra registrado");
        }

        Usuario usuario = new Usuario();
        usuario.setNombreCompleto(dto.getNombreCompleto());
        usuario.setEmail(dto.getEmail());
        usuario.setPassword(dto.getPassword());
        usuario.setRol(dto.getRol());

        Usuario usuarioGuardado = usuarioRepository.save(usuario);
        return maptoDTO(usuarioGuardado);
    }
}
