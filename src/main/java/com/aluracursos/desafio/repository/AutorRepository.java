package com.aluracursos.desafio.repository;

import com.aluracursos.desafio.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor, Long> {
    Optional<Autor> findByNombreIgnoreCase(String nombre);
}
