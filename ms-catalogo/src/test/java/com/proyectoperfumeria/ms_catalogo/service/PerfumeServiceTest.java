package com.proyectoperfumeria.ms_catalogo.service;

import com.proyectoperfumeria.ms_catalogo.dto.PerfumeRequestDTO;
import com.proyectoperfumeria.ms_catalogo.model.Perfume;
import com.proyectoperfumeria.ms_catalogo.repository.PerfumeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PerfumeServiceTest {

    @Mock private PerfumeRepository perfumeRepository;
    @InjectMocks private PerfumeService perfumeService;

    @Test
    void testBUG_04ManipulacionStockCantidadesNegativas() {
        // 1. Preparamos un perfume en la BD simulada con 10 unidades
        Perfume perfumeBase = new Perfume();
        perfumeBase.setId(1L);
        perfumeBase.setNombre("Perfume Test");
        perfumeBase.setStock(10);
        // Le ponemos lenient() para que Mockito no se enoje si el parche bloquea la operación antes de llegar aquí
        org.mockito.Mockito.lenient().when(perfumeRepository.findById(1L)).thenReturn(Optional.of(perfumeBase));
        // Exigimos que el sistema lance una excepción (nuestro parche de seguridad)
        assertThrows(RuntimeException.class, () -> perfumeService.descontarStock(1L, -5));
    }

    @Test
    void testBUG_05CreacionPerfumeValoresInvalidos() {
        // 1. DTO malicioso: Atacante intenta crear un producto con precio negativo
        PerfumeRequestDTO peticionInvalida = new PerfumeRequestDTO();
        peticionInvalida.setNombre("Perfume Fraude");
        peticionInvalida.setMarca("Marca Falsa");
        peticionInvalida.setPrecio(-50000); // ¡El bug está aquí!
        peticionInvalida.setDescripcion("Huele a estafa");
        peticionInvalida.setStock(10);

        // Simulamos que la base de datos lo guarda sin problemas (para el escenario vulnerable)
        Perfume perfumeSimulado = new Perfume();
        perfumeSimulado.setId(1L);
        perfumeSimulado.setPrecio(-50000);

        // Usamos lenient() para que Mockito no arroje error rojo cuando el parche bloquee la operación antes de guardar
        org.mockito.Mockito.lenient().when(perfumeRepository.save(org.mockito.ArgumentMatchers.any(Perfume.class))).thenReturn(perfumeSimulado);

        // 2. Verificamos que el sistema detecte el precio negativo y LANCE un error
        org.junit.jupiter.api.Assertions.assertThrows(RuntimeException.class, () -> perfumeService.guardarPerfume(peticionInvalida));
    }
}