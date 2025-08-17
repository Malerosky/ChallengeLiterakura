package com.aluracursos.desafio;

import com.aluracursos.desafio.principal.Principal;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DesafioApplication implements CommandLineRunner {

    private final Principal principal;

    public DesafioApplication(Principal principal) {
        this.principal = principal;
    }

    public static void main(String[] args) {
        SpringApplication.run(DesafioApplication.class, args);
    }

    @Override
    public void run(String... args) {
        principal.muestraElMenu();
    }
}
