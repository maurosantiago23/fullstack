package com.proyectoperfumeria.ms_analisis_experiencia.config;

import com.proyectoperfumeria.ms_analisis_experiencia.model.Resena;
import com.proyectoperfumeria.ms_analisis_experiencia.repository.ResenaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.datafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class ResenasDataInitializer implements CommandLineRunner {

    private final ResenaRepository resenaRepository;
    private final Faker faker; // Inyectado automáticamente por Lombok

    @Override
    public void run(String... args) throws Exception {

        if (resenaRepository.count() > 0) {
            log.info(">>> Reseñas ya detectadas. Saltando inicialización.");
            return;
        }

        log.info(">>> Cargando 3 reseñas iniciales fijas para la defensa...");

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


        // --- GENERACIÓN MASIVA CON DATAFAKER ---
        log.info(">>> Generando reseñas masivas de clientes con Datafaker...");

        List<Resena> resenasFalsas = new ArrayList<>();

        // Un buen pool de comentarios realistas para que no se vea repetitivo
        String[] comentarios = {
                "Huele increíble, pero no dura tanto en mi piel.",
                "10/10, siempre me preguntan qué perfume llevo puesto.",
                "Es un clásico, ideal para regalar en cumpleaños.",
                "Me llegó rapidísimo el pedido, excelente servicio en tienda.",
                "Buena relación calidad-precio.",
                "Aroma muy elegante, lo uso para la oficina.",
                "No fue de mi total agrado, lo encontré un poco dulce para mi gusto.",
                "Perfecto, fijación extrema, me dura más de 8 horas."
        };

        for (int i = 0; i < 30; i++) {
            Resena r = new Resena();

            // Asumiendo que generamos 22 usuarios y 23 perfumes en los otros MS
            r.setUsuarioId((long) faker.number().numberBetween(1, 23));
            r.setPerfumeId((long) faker.number().numberBetween(1, 24));

            // Calificación aleatoria entre 3 y 5 estrellas
            r.setCalificacion(faker.number().numberBetween(3, 6));
            r.setComentario(faker.options().option(comentarios));

            // Fechas aleatorias de publicación dentro de los últimos 90 días
            r.setFechaPublicacion(LocalDateTime.now().minusDays(faker.number().numberBetween(4, 90)));

            resenasFalsas.add(r);
        }

        // Guardado masivo de las 30 reseñas
        resenaRepository.saveAll(resenasFalsas);

        log.info(">>> Carga de 3 reseñas manuales y 30 masivas finalizada correctamente.");
        log.info(">>> ¡ARQUITECTURA DE LA PERFUMERÍA COMPLETAMENTE POBLADA!");
    }
}
