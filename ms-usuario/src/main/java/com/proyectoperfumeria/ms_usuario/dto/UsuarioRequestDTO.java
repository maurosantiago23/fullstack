package com.proyectoperfumeria.ms_usuario.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioRequestDTO {

    @NotBlank(message = "El nombre completo no puede estar vacio")
    private String nombreCompleto;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "Debe ser un email valido")
    private String email;

    @NotBlank(message = "La contraseña es obligatoria")
    private String password;

    @NotBlank(message = "El rol a escoger es obligatorio:/ ADMIN O CLIENTE")
    private String rol;
}
