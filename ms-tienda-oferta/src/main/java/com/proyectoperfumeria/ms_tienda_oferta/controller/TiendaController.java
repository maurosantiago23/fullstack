package com.proyectoperfumeria.ms_tienda_oferta.controller;

import com.proyectoperfumeria.ms_tienda_oferta.dto.*;
import com.proyectoperfumeria.ms_tienda_oferta.service.TiendaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class TiendaController {

    private final TiendaService tiendaService;

    @PostMapping("/tiendas")
    public ResponseEntity<?> crearTienda(@RequestBody @Valid TiendaRequestDTO tiendaRequestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(tiendaService.crearTienda(tiendaRequestDTO));
    }

    @GetMapping("/tiendas")
    public ResponseEntity<List<TiendaResponseDTO>> listarTiendas() {
        return ResponseEntity.ok(tiendaService.obtenerTiendas());
    }

    @PostMapping("/ofertas")
    public ResponseEntity<OfertaResponseDTO> crearOferta(@RequestBody @Valid OfertaRequestDTO ofertaRequestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(tiendaService.crearOferta(ofertaRequestDTO));
    }

    @GetMapping("/ofertas")
    public ResponseEntity<List<OfertaResponseDTO>> listarOfertas() {
        return ResponseEntity.ok(tiendaService.obtenerOfertasPorTienda());
    }

    @PostMapping("/ventas")
    public ResponseEntity<VentaResponseDTO> registrarVenta(@RequestBody @Valid VentaRequestDTO ventaRequestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(tiendaService.registrarVentaPerfume(ventaRequestDTO));
    }

    @GetMapping("/ventas")
    public ResponseEntity<List<VentaResponseDTO>> listarVentas() {
        return ResponseEntity.ok(tiendaService.obtenerVentasPerfume());
    }
}
