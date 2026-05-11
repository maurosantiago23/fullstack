package com.proyectoperfumeria.ms_catalogo.repository;

import com.proyectoperfumeria.ms_catalogo.model.Perfume;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PerfumeRepository extends JpaRepository<Perfume, Long> {

}
