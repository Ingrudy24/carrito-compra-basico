package com.carrito_compra.proyecto;

import com.carrito_compra.proyecto.repository.ProductoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ProyectoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProyectoApplication.class, args);
	}

    @Bean
    public CommandLineRunner testProductoRepo(ProductoRepository repo) {
        return args -> {
            System.out.println("productos en la base");
          repo.findAll().forEach(p -> System.out.println(p.getNombre()+ "--" + p.getPrecio()));
        };
    }
}
