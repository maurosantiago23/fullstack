package com.proyectoperfumeria.ms_analisis_experiencia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class MsAnalisisExperienciaApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsAnalisisExperienciaApplication.class, args);
	}

}
