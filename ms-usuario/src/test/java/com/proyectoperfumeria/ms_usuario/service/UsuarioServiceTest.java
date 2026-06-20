package com.proyectoperfumeria.ms_usuario.service;
import com.proyectoperfumeria.ms_usuario.dto.UsuarioRequestDTO;
import com.proyectoperfumeria.ms_usuario.model.Usuario;
import com.proyectoperfumeria.ms_usuario.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Optional;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock private UsuarioRepository usuarioRepository;
    @Mock private PasswordEncoder passwordEncoder;
    @InjectMocks private UsuarioService usuarioService;

    @Test
    void testBUG001_CrearUsuarioAdminUsaEncoder() {
        // Solución del rojo: Ahora usamos el DTO, tal como lo exige tu servicio parchado
        UsuarioRequestDTO dto = new UsuarioRequestDTO();
        dto.setNombreCompleto("Felipe Admin");
        dto.setEmail("admin@duocuc.cl");
        dto.setPassword("admin123");
        dto.setRol("ADMIN");

        when(usuarioRepository.findByEmail(dto.getEmail())).thenReturn(Optional.empty());
        when(usuarioRepository.save(any(Usuario.class))).thenAnswer(i -> i.getArgument(0));
        usuarioService.crearUsuarioAdmin(dto);

        verify(passwordEncoder, times(1)).encode("admin123");
    }
}