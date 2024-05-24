package com.erickcode.desafio4;

import com.erickcode.desafio4.principal.Principal;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Desafio4Application implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(Desafio4Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal();
		principal.muestraMenu();



	}
}
