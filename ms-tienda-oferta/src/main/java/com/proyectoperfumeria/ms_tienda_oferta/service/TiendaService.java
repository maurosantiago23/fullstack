package com.proyectoperfumeria.ms_tienda_oferta.service;


import com.proyectoperfumeria.ms_tienda_oferta.client.CatalogoFeignClient;
import com.proyectoperfumeria.ms_tienda_oferta.client.UsuarioFeignClient;
import com.proyectoperfumeria.ms_tienda_oferta.dto.*;
import com.proyectoperfumeria.ms_tienda_oferta.model.Oferta;
import com.proyectoperfumeria.ms_tienda_oferta.model.Tienda;
import com.proyectoperfumeria.ms_tienda_oferta.model.Venta;
import com.proyectoperfumeria.ms_tienda_oferta.repository.OfertaRepository;
import com.proyectoperfumeria.ms_tienda_oferta.repository.TiendaRepository;
import com.proyectoperfumeria.ms_tienda_oferta.repository.VentaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TiendaService {

    private final VentaRepository ventaRepository;
    private final OfertaRepository ofertaRepository;
    private final TiendaRepository tiendaRepository;
    private final UsuarioFeignClient usuarioFeignClient;
    private final CatalogoFeignClient catalogoFeignClient;

    public TiendaResponseDTO mapToTiendaDTO(Tienda tienda) {
           return new TiendaResponseDTO(tienda.getId(), tienda.getNombreTienda(), tienda.getUbicacion());
    }

    public TiendaResponseDTO crearTienda(TiendaRequestDTO tiendaRequestDTO) {
        Tienda tienda = new Tienda();
        tienda.setNombreTienda(tiendaRequestDTO.getNombre());
        tienda.setUbicacion(tiendaRequestDTO.getUbicacion());
        return mapToTiendaDTO(tiendaRepository.save(tienda));
    }

    public List<TiendaResponseDTO> obtenerTiendas(){
        List<Tienda> tiendas = tiendaRepository.findAll();
        List<TiendaResponseDTO> tiendasDTO = new ArrayList<>();
        for (Tienda tienda : tiendas) {
            tiendasDTO.add(mapToTiendaDTO(tienda));
        }
        return tiendasDTO;
    }

    private OfertaResponseDTO mapToOfertaDTO(Oferta oferta) {
        return new OfertaResponseDTO(
                oferta.getId(),
                oferta.getPerfumeId(),
                oferta.getTiendaId(),
                oferta.getPrecioOferta());
    }

    public OfertaResponseDTO crearOferta(OfertaRequestDTO ofertaRequestDTO) {

        // 1. Validar que el perfume realmente exista en el catálogo
        catalogoFeignClient.validarPerfume(ofertaRequestDTO.getPerfumeId());
        // 2. Validar que la tienda exista en la base de datos
        tiendaRepository.findById(ofertaRequestDTO.getTiendaId())
                .orElseThrow(() -> new RuntimeException("Operación rechazada: La tienda con ID " + ofertaRequestDTO.getTiendaId() + " no existe."));
        // Si ambas validaciones pasan, recién ahí guardamos la oferta
        Oferta oferta = new Oferta();
        oferta.setPerfumeId(ofertaRequestDTO.getPerfumeId());
        oferta.setTiendaId(ofertaRequestDTO.getTiendaId());
        oferta.setPrecioOferta(ofertaRequestDTO.getPrecioOferta());

        return mapToOfertaDTO(ofertaRepository.save(oferta));
    }

    public List<OfertaResponseDTO> obtenerOfertasPorTienda() {
        List<Oferta> ofertas = ofertaRepository.findAll();
        List<OfertaResponseDTO> ofertasDTO = new ArrayList<>();
        for (Oferta oferta : ofertas) {
            ofertasDTO.add(mapToOfertaDTO(oferta));
        }
        return ofertasDTO;
    }

    private VentaResponseDTO mapToVentaDTO(Venta venta) {
        return new VentaResponseDTO(
                venta.getId(),
                venta.getUsuarioId(),
                venta.getPerfumeId(),
                venta.getFechaVenta(),
                venta.getTotal(),
                venta.getMetodoPago(),
                venta.getEstadoPago()
        );
    }

    public VentaResponseDTO registrarVentaPerfume(VentaRequestDTO ventaRequestDTO) {
        usuarioFeignClient.validarUsuario(ventaRequestDTO.getUsuarioId());
        catalogoFeignClient.validarPerfume(ventaRequestDTO.getPerfumeId());

        List<Oferta> ofertasActivas = ofertaRepository.findAll();
        boolean ventaAutorizada = false;

        for (Oferta oferta : ofertasActivas) {
            if (String.valueOf(oferta.getPerfumeId()).equals(String.valueOf(ventaRequestDTO.getPerfumeId()))) {
                if (String.valueOf(oferta.getPrecioOferta()).equals(String.valueOf(ventaRequestDTO.getTotal()))) {
                    ventaAutorizada = true; // Luz verde
                }
                break; // Cortamos la búsqueda porque ya encontramos el perfume
            }
        }
        if (!ventaAutorizada) {
            throw new RuntimeException("Operación rechazada: No existe una oferta activa para este perfume o el monto es inválido.");
        }

        catalogoFeignClient.descontarStock(ventaRequestDTO.getPerfumeId(), 1);

        Venta venta = new Venta();
        venta.setUsuarioId(ventaRequestDTO.getUsuarioId());
        venta.setPerfumeId(ventaRequestDTO.getPerfumeId());
        venta.setTotal(ventaRequestDTO.getTotal());
        venta.setMetodoPago(ventaRequestDTO.getMetodoPago());
        venta.setFechaVenta(LocalDateTime.now());
        venta.setEstadoPago("PAGO APROBADO!");

        Venta ventaGuardada = ventaRepository.save(venta);
        return mapToVentaDTO(ventaGuardada);
    }

    public List<VentaResponseDTO> obtenerVentasPerfume() {
        List<Venta> ventas = ventaRepository.findAll();
        List<VentaResponseDTO> ventaResponseDTOS = new ArrayList<>();
        for (Venta venta : ventas) {
            ventaResponseDTOS.add(mapToVentaDTO(venta));
        }
        return ventaResponseDTOS;
    }

}
