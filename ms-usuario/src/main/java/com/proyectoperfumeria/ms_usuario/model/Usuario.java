package com.proyectoperfumeria.ms_usuario.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table (name= "usuarios")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (name= "nombreCompleto", nullable = false, length = 150)
    private String nombreCompleto;

    @Column (nullable = false, unique = true, length = 100)
    private String email;

    @Column (nullable = false, length = 100)
    private String password;

    @Column (nullable = false, length = 50)
    private String rol;
}
