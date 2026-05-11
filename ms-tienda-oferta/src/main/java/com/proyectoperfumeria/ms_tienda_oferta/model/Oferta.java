package com.proyectoperfumeria.ms_tienda_oferta.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ofertas")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Oferta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "perfume_id", nullable = false)
    private Long perfumeId;

    @Column(name = "tienda_id", nullable = false)
    private Long tiendaId;

    @Column(name = "precio_oferta", nullable = false)
    private Integer precioOferta;
}
