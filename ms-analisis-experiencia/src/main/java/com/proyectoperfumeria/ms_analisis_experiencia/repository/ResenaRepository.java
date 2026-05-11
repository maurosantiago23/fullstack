package com.proyectoperfumeria.ms_analisis_experiencia.repository;

import com.proyectoperfumeria.ms_analisis_experiencia.model.Resena;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResenaRepository extends JpaRepository<Resena, Long> {
}
