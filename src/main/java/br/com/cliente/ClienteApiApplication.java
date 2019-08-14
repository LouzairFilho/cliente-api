package br.com.cliente;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.cliente.service.MetaWeatherService;

@SpringBootApplication
public class ClienteApiApplication {

	
	
	public static void main(String[] args) {
		SpringApplication.run(ClienteApiApplication.class, args);
		
	}

}
