package com.proyectoperfumeria.ms_tienda_oferta.config;


import com.proyectoperfumeria.ms_tienda_oferta.model.Oferta;
import com.proyectoperfumeria.ms_tienda_oferta.model.Tienda;
import com.proyectoperfumeria.ms_tienda_oferta.model.Venta;
import com.proyectoperfumeria.ms_tienda_oferta.repository.OfertaRepository;
import com.proyectoperfumeria.ms_tienda_oferta.repository.TiendaRepository;
import com.proyectoperfumeria.ms_tienda_oferta.repository.VentaRepository;
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
public class TOVDataInitializer implements CommandLineRunner {

    private final TiendaRepository tiendaRepository;
    private final OfertaRepository ofertaRepository;
    private final VentaRepository ventaRepository;
    private final Faker faker;

    @Override
    public void run(String... args) {
        if (tiendaRepository.count() > 0) {
            log.info(">>> Tiendas ya detectadas en la base de datos. Saltando inicialización.");
            return;
        }

        log.info(">>> [CONFIG] Mapeo de Perfumes para la Defensa:");
        log.info(">>> ID 1: Bleu de Chanel");
        log.info(">>> ID 2: Sauvage Dior");
        log.info(">>> ID 3: Eros Versace");

        log.info(">>> Iniciando carga de 3 Tiendas, 3 Ofertas y 3 Ventas manuales...");

        // --- 1. DATOS MANUALES PARA LA DEFENSA ---

        Tienda tienda1 = new Tienda();
        tienda1.setNombreTienda("Aroma Mall Marina");
        tienda1.setUbicacion("Viña del Mar");
        tienda1 = tiendaRepository.save(tienda1);

        Tienda tienda2 = new Tienda();
        tienda2.setNombreTienda("Aroma Portal Belloto");
        tienda2.setUbicacion("Quilpué");
        tienda2 = tiendaRepository.save(tienda2);

        Tienda tienda3 = new Tienda();
        tienda3.setNombreTienda("Aroma Boulevard");
        tienda3.setUbicacion("Viña del Mar Norte");
        tienda3 = tiendaRepository.save(tienda3);

        Oferta oferta1 = new Oferta();
        oferta1.setPerfumeId(1L);
        oferta1.setTiendaId(tienda1.getId());
        oferta1.setPrecioOferta(85000);
        ofertaRepository.save(oferta1);

        Oferta oferta2 = new Oferta();
        oferta2.setPerfumeId(1L);
        oferta2.setTiendaId(tienda2.getId());
        oferta2.setPrecioOferta(79990);
        ofertaRepository.save(oferta2);

        Oferta oferta3 = new Oferta();
        oferta3.setPerfumeId(2L);
        oferta3.setTiendaId(tienda3.getId());
        oferta3.setPrecioOferta(92000);
        ofertaRepository.save(oferta3);

        Venta venta1 = new Venta();
        venta1.setUsuarioId(1L);
        venta1.setPerfumeId(1L);
        venta1.setFechaVenta(LocalDateTime.now());
        venta1.setTotal(79990);
        venta1.setMetodoPago("Tarjeta Débito");
        venta1.setEstadoPago("PAGO APROBADO");
        ventaRepository.save(venta1);

        Venta venta2 = new Venta();
        venta2.setUsuarioId(2L);
        venta2.setPerfumeId(1L);
        venta2.setFechaVenta(LocalDateTime.now().minusDays(1));
        venta2.setTotal(85000);
        venta2.setMetodoPago("Tarjeta Crédito");
        venta2.setEstadoPago("PAGO APROBADO");
        ventaRepository.save(venta2);

        Venta venta3 = new Venta();
        venta3.setUsuarioId(1L);
        venta3.setPerfumeId(3L);
        venta3.setFechaVenta(LocalDateTime.now());
        venta3.setTotal(65000);
        venta3.setMetodoPago("Transferencia");
        venta3.setEstadoPago("PAGO PENDIENTE");
        ventaRepository.save(venta3);


        // --- 2. GENERACIÓN MASIVA CON DATAAFAKER ---
        log.info(">>> Generando datos masivos de relleno con Datafaker...");

        // Generar 10 Tiendas extra
        List<Tienda> tiendasFalsas = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Tienda t = new Tienda();
            t.setNombreTienda("Perfumería " + faker.name().lastName());
            t.setUbicacion(faker.address().city());
            tiendasFalsas.add(t);
        }
        // Guardamos y recuperamos la lista para usar sus IDs en las ofertas
        List<Tienda> tiendasGuardadas = tiendaRepository.saveAll(tiendasFalsas);

        // Generar 20 Ofertas extra
        List<Oferta> ofertasFalsas = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            Oferta o = new Oferta();
            o.setPerfumeId((long) faker.number().numberBetween(1, 21)); // Asume que hay unos 20 perfumes

            // Asignamos la oferta a una de las tiendas que acabamos de crear
            Tienda tiendaRandom = tiendasGuardadas.get(faker.number().numberBetween(0, tiendasGuardadas.size()));
            o.setTiendaId(tiendaRandom.getId());

            o.setPrecioOferta(faker.number().numberBetween(35000, 110000));
            ofertasFalsas.add(o);
        }
        ofertaRepository.saveAll(ofertasFalsas);

        // Generar 30 Ventas extra
        List<Venta> ventasFalsas = new ArrayList<>();
        String[] metodos = {"Tarjeta Débito", "Tarjeta Crédito", "Transferencia", "Efectivo"};
        String[] estados = {"PAGO APROBADO", "PAGO RECHAZADO", "PAGO PENDIENTE"};

        for (int i = 0; i < 30; i++) {
            Venta v = new Venta();
            v.setUsuarioId((long) faker.number().numberBetween(1, 23)); // 22 usuarios generados en el otro MS
            v.setPerfumeId((long) faker.number().numberBetween(1, 21));

            // Inventamos fechas de venta aleatorias de los últimos 60 días
            v.setFechaVenta(LocalDateTime.now().minusDays(faker.number().numberBetween(1, 60)));

            v.setTotal(faker.number().numberBetween(30000, 120000));
            v.setMetodoPago(faker.options().option(metodos));
            v.setEstadoPago(faker.options().option(estados));
            ventasFalsas.add(v);
        }
        ventaRepository.saveAll(ventasFalsas);

        log.info(">>> Carga finalizada. Estructura lista para trabajar con tu compañero.");
    }
}
