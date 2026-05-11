package com.proyectoperfumeria.ms_tienda_oferta.repository;


import com.proyectoperfumeria.ms_tienda_oferta.model.Oferta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OfertaRepository extends JpaRepository<Oferta, Long> {
}
