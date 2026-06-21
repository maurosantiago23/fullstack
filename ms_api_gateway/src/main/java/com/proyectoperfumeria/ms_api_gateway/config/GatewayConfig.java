package com.proyectoperfumeria.ms_api_gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import static org.springframework.cloud.gateway.server.mvc.filter.LoadBalancerFilterFunctions.lb;
import static org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions.route;
import static org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions.http;
import static org.springframework.web.servlet.function.RequestPredicates.path;

@Configuration
public class GatewayConfig {

    @Bean
    public RouterFunction<ServerResponse> usuarioRoute() {
        return route("ms-usuario")
                .route(path("/api/v1/usuarios/**"), http())
                .filter(lb("ms-usuario"))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> catalogoRoute() {
        return route("ms-catalogo")
                .route(path("/api/v1/perfumes/**"), http())
                .filter(lb("ms-catalogo"))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> tiendasRoute() {
        return route("ms-tiendas")
                .route(path("/api/v1/tiendas/**"), http())
                .filter(lb("ms-tienda-oferta"))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> ofertasRoute() {
        return route("ms-ofertas")
                .route(path("/api/v1/ofertas/**"), http())
                .filter(lb("ms-tienda-oferta"))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> ventasRoute() {
        return route("ms-ventas")
                .route(path("/api/v1/ventas/**"), http())
                .filter(lb("ms-tienda-oferta"))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> experienciaRoute() {
        return route("ms-analisis-experiencia")
                .route(path("/api/experiencia/**"), http())
                .filter(lb("ms-analisis-experiencia"))
                .build();
    }
}