package com.proyectoperfumeria.ms_catalogo.config;

import com.proyectoperfumeria.ms_catalogo.model.Perfume;
import com.proyectoperfumeria.ms_catalogo.repository.PerfumeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.datafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class PerfumeDataInitializer implements CommandLineRunner {

    private final PerfumeRepository perfumeRepository;
    private final Faker faker; // Lombok se encarga de inyectar tu Bean de Faker

    @Override
    public void run(String... args) throws Exception {
        if (perfumeRepository.count() > 0) {
            log.info(">>> Catálogo de perfumes ya detectado en la base de datos.");
            return;
        }

        log.info(">>> Cargando catálogo inicial de perfumes fijos para la defensa...");

        // Perfume 1 (ID 1)
        Perfume perfume1 = new Perfume();
        perfume1.setNombre("Bleu de Chanel");
        perfume1.setMarca("Chanel");
        perfume1.setPrecio(90000);
        perfume1.setDescripcion("Fragancia amaderada aromática, elegante y profunda.");
        perfume1.setStock(15);
        perfumeRepository.save(perfume1);

        // Perfume 2 (ID 2)
        Perfume perfume2 = new Perfume();
        perfume2.setNombre("Sauvage Dior");
        perfume2.setMarca("Dior");
        perfume2.setPrecio(95000);
        perfume2.setDescripcion("Fragancia fresca, especiada y con notas cítricas salvajes.");
        perfume2.setStock(10);
        perfumeRepository.save(perfume2);

        // Perfume 3 (ID 3)
        Perfume perfume3 = new Perfume();
        perfume3.setNombre("Eros Versace");
        perfume3.setMarca("Versace");
        perfume3.setPrecio(70000);
        perfume3.setDescripcion("Fragancia vibrante y luminosa con notas de menta y manzana verde.");
        perfume3.setStock(20);
        perfumeRepository.save(perfume3);

        log.info(">>> Abasteciendo el resto del catálogo con Datafaker...");

        List<Perfume> perfumesFalsos = new ArrayList<>();
        // Un buen arreglo de marcas extra para darle caché a los datos de relleno
        String[] marcas = {"Paco Rabanne", "Giorgio Armani", "Hugo Boss", "Carolina Herrera", "Calvin Klein", "Tom Ford"};

        for (int i = 0; i < 20; i++) {
            Perfume p = new Perfume();

            // Inventamos nombres de productos aleatorios (suelen quedar bastante bien)
            p.setNombre(faker.commerce().productName());
            p.setMarca(faker.options().option(marcas)); // Elige una marca al azar del arreglo
            p.setPrecio(faker.number().numberBetween(35000, 130000));

            // Genera una frase de relleno que parezca descripción
            p.setDescripcion(faker.lorem().sentence(8));
            p.setStock(faker.number().numberBetween(5, 50));

            perfumesFalsos.add(p);
        }

        // Guardado masivo de los 20 perfumes extra
        perfumeRepository.saveAll(perfumesFalsos);

        log.info(">>> Se han cargado 3 perfumes manuales y 20 aleatorios exitosamente.");
    }
}
