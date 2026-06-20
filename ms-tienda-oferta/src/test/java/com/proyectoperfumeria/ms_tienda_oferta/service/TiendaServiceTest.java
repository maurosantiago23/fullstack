package com.proyectoperfumeria.ms_tienda_oferta.service;

import com.proyectoperfumeria.ms_tienda_oferta.client.CatalogoFeignClient;
import com.proyectoperfumeria.ms_tienda_oferta.client.UsuarioFeignClient;
import com.proyectoperfumeria.ms_tienda_oferta.dto.OfertaRequestDTO;
import com.proyectoperfumeria.ms_tienda_oferta.dto.VentaRequestDTO;
import com.proyectoperfumeria.ms_tienda_oferta.model.Oferta;
import com.proyectoperfumeria.ms_tienda_oferta.repository.OfertaRepository;
import com.proyectoperfumeria.ms_tienda_oferta.repository.TiendaRepository;
import com.proyectoperfumeria.ms_tienda_oferta.repository.VentaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)

class TiendaServiceTest {

    @Mock private TiendaRepository tiendaRepository;
    @Mock private VentaRepository ventaRepository;
    @Mock private OfertaRepository ofertaRepository;
    @Mock private UsuarioFeignClient usuarioFeignClient;
    @Mock private CatalogoFeignClient catalogoFeignClient;

    @InjectMocks private TiendaService tiendaService;

    @Test
    void testBUG_O2fertaFantasma_RechazaTiendaInexistente() {
        // 1. DTO de un atacante intentando crear una oferta para una tienda falsa (ID 999)
        OfertaRequestDTO peticionFalsa = new OfertaRequestDTO();
        peticionFalsa.setPerfumeId(1L);
        peticionFalsa.setTiendaId(999L);
        peticionFalsa.setPrecioOferta(15000);

        // 2. Simulamos la respuesta de la base de datos original para que no se caiga por otros errores
        Oferta ofertaSimulada = new Oferta();
        ofertaSimulada.setId(1L);
        ofertaSimulada.setPerfumeId(1L);
        ofertaSimulada.setTiendaId(999L);
        ofertaSimulada.setPrecioOferta(15000);

        // Usamos lenient() para que Mockito no arroje error cuando el parche bloquee la operación antes de guardar
        org.mockito.Mockito.lenient().when(ofertaRepository.save(org.mockito.ArgumentMatchers.any(Oferta.class))).thenReturn(ofertaSimulada);

        // 3. Verificamos que el sistema DETECTE que la tienda 999 no existe y lance un error
        org.junit.jupiter.api.Assertions.assertThrows(RuntimeException.class, () -> tiendaService.crearOferta(peticionFalsa));
    }
    @Test
    void testBUG_03EvitarFraudeManipulacionPrecio() {
        // 1. DTO fraudulento (Hackerman intenta pagar $100 pesos)
        VentaRequestDTO ventaFraudulenta = new VentaRequestDTO();
        ventaFraudulenta.setUsuarioId(1L);
        ventaFraudulenta.setPerfumeId(10L);
        // *Nota: Si tu DTO usa Double o String, cambia el 100 por 100.0 o "100"
        ventaFraudulenta.setTotal(100);
        ventaFraudulenta.setMetodoPago("WEBPAY");
        // 2. Oferta Real en la base de datos (El perfume realmente vale $50.000)
        Oferta ofertaReal = new Oferta();
        ofertaReal.setPerfumeId(10L);
        ofertaReal.setPrecioOferta(50000);
        // 3. Le decimos a Mockito que la BD devuelva la oferta real
        when(ofertaRepository.findAll()).thenReturn(List.of(ofertaReal));
        // 4. Verificamos que el sistema detecte la estafa y LANCE un error
        // (Como tu código actual NO lanza error, la prueba va a fallar, dándote la X amarilla)
        assertThrows(RuntimeException.class, () -> tiendaService.registrarVentaPerfume(ventaFraudulenta));
    }
}