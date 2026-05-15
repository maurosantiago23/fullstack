package com.proyectoperfumeria.ms_analisis_experiencia.config;

import com.proyectoperfumeria.ms_analisis_experiencia.model.Resena;
import com.proyectoperfumeria.ms_analisis_experiencia.repository.ResenaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class ResenasDataInitializer implements CommandLineRunner {

    private final ResenaRepository resenaRepository;

    @Override
    public void run(String... args) throws Exception {

        if (resenaRepository.count() > 0) {
            log.info(">>> Reseñas ya detectadas. Saltando inicialización.");
            return;
        }

        log.info(">>> Cargando 3 reseñas iniciales (una para cada perfume)...");

        //  Reseña 1: Cliente Prueba (ID 2) evalúa Bleu de Chanel (ID 1)
        Resena resena1 = new Resena();
        resena1.setUsuarioId(2L);
        resena1.setPerfumeId(1L);
        resena1.setCalificacion(5);
        resena1.setComentario("Excelente fijación, es mi perfume favorito para salir en Viña.");
        resena1.setFechaPublicacion(LocalDateTime.now().minusDays(3));
        resenaRepository.save(resena1);

        //  Reseña 2: Administrador (ID 1) evalúa Sauvage Dior (ID 2)
        Resena resena2 = new Resena();
        resena2.setUsuarioId(1L);
        resena2.setPerfumeId(2L);
        resena2.setCalificacion(4);
        resena2.setComentario("Muy buen aroma, fresco y potente. El stock llegó bien al Mall Marina.");
        resena2.setFechaPublicacion(LocalDateTime.now().minusDays(2));
        resenaRepository.save(resena2);

        //  Reseña 3: Cliente Prueba (ID 2) evalúa Eros Versace (ID 3)
        Resena resena3 = new Resena();
        resena3.setUsuarioId(2L);
        resena3.setPerfumeId(3L);
        resena3.setCalificacion(5);
        resena3.setComentario("Me encanta la nota de menta que tiene. Muy recomendado para el día a día.");
        resena3.setFechaPublicacion(LocalDateTime.now().minusDays(1));
        resenaRepository.save(resena3);

        log.info(">>> Carga de 3 reseñas finalizada correctamente.");
    }
}
