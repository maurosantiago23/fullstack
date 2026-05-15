package com.proyectoperfumeria.ms_catalogo.config;

import com.proyectoperfumeria.ms_catalogo.model.Perfume;
import com.proyectoperfumeria.ms_catalogo.repository.PerfumeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PerfumeDataInitializer implements CommandLineRunner {

    private final PerfumeRepository perfumeRepository;

    @Override
    public void run(String... args) throws Exception {
        if (perfumeRepository.count() > 0) {
            log.info(">>> Catálogo de perfumes ya detectado en la base de datos.");
            return;
        }

        log.info(">>> Cargando catálogo inicial de perfumes...");

        // Perfume 1
        Perfume perfume1 = new Perfume();
        perfume1.setNombre("Bleu de Chanel");
        perfume1.setMarca("Chanel");
        perfume1.setPrecio(90000);
        perfume1.setDescripcion("Fragancia amaderada aromática, elegante y profunda.");
        perfume1.setStock(15);
        perfumeRepository.save(perfume1);

        // Perfume 2
        Perfume perfume2 = new Perfume();
        perfume2.setNombre("Sauvage Dior");
        perfume2.setMarca("Dior");
        perfume2.setPrecio(95000);
        perfume2.setDescripcion("Fragancia fresca, especiada y con notas cítricas salvajes.");
        perfume2.setStock(10);
        perfumeRepository.save(perfume2);

        // Perfume 3
        Perfume perfume3 = new Perfume();
        perfume3.setNombre("Eros Versace");
        perfume3.setMarca("Versace");
        perfume3.setPrecio(70000);
        perfume3.setDescripcion("Fragancia vibrante y luminosa con notas de menta y manzana verde.");
        perfume3.setStock(20);
        perfumeRepository.save(perfume3);

        log.info(">>> Se han cargado 3 perfumes exitosamente.");
    }
}
