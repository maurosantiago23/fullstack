package com.proyectoperfumeria.ms_usuario.service;

import com.proyectoperfumeria.ms_usuario.dto.LoginRequestDTO;
import com.proyectoperfumeria.ms_usuario.dto.UsuarioRequestDTO;
import com.proyectoperfumeria.ms_usuario.dto.UsuarioResponseDTO;
import com.proyectoperfumeria.ms_usuario.model.Usuario;
import com.proyectoperfumeria.ms_usuario.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository; //Agregado el final
    private final PasswordEncoder passwordEncoder; //Agregado el password encoder

    public UsuarioResponseDTO maptoDTO(Usuario usuario) {
        return new UsuarioResponseDTO(
                usuario.getId(),
                usuario.getNombreCompleto(),
                usuario.getEmail(),
                usuario.getRol()
        );
    }

    public List<UsuarioResponseDTO> obtenerTodosUsuarios() {
        List<Usuario> listaUsuarios = usuarioRepository.findAll();
        List<UsuarioResponseDTO> listaUsuariosDTO = new ArrayList<>();
        for (Usuario usuario : listaUsuarios) {
            listaUsuariosDTO.add(maptoDTO(usuario));
        }
        return listaUsuariosDTO;
    }

    public Optional<UsuarioResponseDTO> obtenerUsuarioPorId(Long id) {
        return usuarioRepository.findById(id).map(this::maptoDTO);
    }

    public UsuarioResponseDTO loginManual(LoginRequestDTO dto) {
        Usuario usuario = usuarioRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new RuntimeException("Credenciales inválidas"));
        if (!passwordEncoder.matches(dto.getPassword(), usuario.getPassword())){
            throw new RuntimeException("Credenciales Invalidas");
        }
        return maptoDTO(usuario);
    }

    public UsuarioResponseDTO registrarCliente(UsuarioRequestDTO dto) {
        if (usuarioRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new RuntimeException("El email que ingresó ya se encuentra registrado");
        }

        Usuario usuario = new Usuario();
        usuario.setNombreCompleto(dto.getNombreCompleto());
        usuario.setEmail(dto.getEmail());
        usuario.setPassword(passwordEncoder.encode(dto.getPassword()));
        usuario.setRol("CLIENTE");

        Usuario usuarioGuardado = usuarioRepository.save(usuario);
        return maptoDTO(usuarioGuardado);
    }

    public UsuarioResponseDTO crearUsuarioAdmin(UsuarioRequestDTO dto) {
        if (usuarioRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new RuntimeException("El email que ingresó ya se encuentra registrado");
        }

        Usuario usuario = new Usuario();
        usuario.setNombreCompleto(dto.getNombreCompleto());
        usuario.setEmail(dto.getEmail());
        usuario.setPassword(passwordEncoder.encode(dto.getPassword()));
        usuario.setRol(dto.getRol());

        Usuario usuarioGuardado = usuarioRepository.save(usuario);
        return maptoDTO(usuarioGuardado);
    }
}

// usuario.setPassword(dto.getPassword()); Test no corregido