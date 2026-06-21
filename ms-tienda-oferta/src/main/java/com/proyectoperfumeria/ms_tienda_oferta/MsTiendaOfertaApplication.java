package com.proyectoperfumeria.ms_tienda_oferta;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class MsTiendaOfertaApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsTiendaOfertaApplication.class, args);
	}

}
