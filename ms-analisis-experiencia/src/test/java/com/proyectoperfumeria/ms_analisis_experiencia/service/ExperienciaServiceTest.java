package com.proyectoperfumeria.ms_analisis_experiencia.service;

import com.proyectoperfumeria.ms_analisis_experiencia.client.CatalogoFeignClient;
import com.proyectoperfumeria.ms_analisis_experiencia.client.UsuarioFeignClient;
import com.proyectoperfumeria.ms_analisis_experiencia.dto.ResenaRequestDTO;
import com.proyectoperfumeria.ms_analisis_experiencia.model.Resena;
import com.proyectoperfumeria.ms_analisis_experiencia.repository.ResenaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
class ExperienciaServiceTest {

    @Mock private ResenaRepository resenaRepository;
    @Mock private UsuarioFeignClient usuarioFeignClient;
    @Mock private CatalogoFeignClient catalogoFeignClient;

    @InjectMocks private ExperienciaService experienciaService;

    @Test
    void testBUG_006CalificacionFueraDeRango() {
        // 1. DTO malicioso: Un usuario intenta calificar un perfume con 100 estrellas (el máximo debería ser 5)
        ResenaRequestDTO peticionFalsa = new ResenaRequestDTO();
        peticionFalsa.setUsuarioId(1L);
        peticionFalsa.setPerfumeId(1L);
        peticionFalsa.setCalificacion(100); // <- El dato corrupto
        peticionFalsa.setComentario("¡El mejor perfume del universo!");
        // Simulamos el guardado para evitar errores de Mockito cuando el código vulnerable pase de largo
        Resena resenaSimulada = new Resena();
        lenient().when(resenaRepository.save(org.mockito.ArgumentMatchers.any(Resena.class))).thenReturn(resenaSimulada);
        // 2. Exigimos que el sistema DETECTE el número ilógico y lance un error.
        assertThrows(RuntimeException.class, () -> experienciaService.crearResenaDePerfume(peticionFalsa));
    }

    @Test
    void testBUG_007SpamResenas_UsuarioSoloPuedeComentarUnaVez() {
        // 1. DTO de un usuario intentando comentar de nuevo
        ResenaRequestDTO peticionDuplicada = new ResenaRequestDTO();
        peticionDuplicada.setUsuarioId(1L);
        peticionDuplicada.setPerfumeId(5L);
        peticionDuplicada.setCalificacion(5);
        peticionDuplicada.setComentario("Vuelvo a comentar porque me encanta.");

        // 2. Le decimos a la base de datos simulada que este usuario YA comentó este perfume
        org.mockito.Mockito.when(resenaRepository.existsByUsuarioIdAndPerfumeId(1L, 5L)).thenReturn(true);

        // 3. Exigimos que el sistema lo detecte y lance una excepción
        org.junit.jupiter.api.Assertions.assertThrows(RuntimeException.class, () -> experienciaService.crearResenaDePerfume(peticionDuplicada));
    }
}