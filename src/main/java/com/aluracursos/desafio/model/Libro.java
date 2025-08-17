package com.aluracursos.desafio.model;

import jakarta.persistence.*;

@Entity
@Table(name = "libros")
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 500)
    private String titulo;

    private Integer numeroDeDescargas;

    @ManyToOne
    @JoinColumn(name = "autor_id")
    private Autor autor;

    public Libro() {}

    public Libro(String titulo, Integer numeroDeDescargas, Autor autor) {
        this.titulo = titulo;
        this.numeroDeDescargas = numeroDeDescargas;
        this.autor = autor;
    }

    public Long getId() { return id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public Integer getNumeroDeDescargas() { return numeroDeDescargas; }
    public void setNumeroDeDescargas(Integer numeroDeDescargas) { this.numeroDeDescargas = numeroDeDescargas; }

    public Autor getAutor() { return autor; }
    public void setAutor(Autor autor) { this.autor = autor; }

    @Override
    public String toString() {
        return "Libro{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", numeroDeDescargas=" + numeroDeDescargas +
                ", autor=" + (autor != null ? autor.getNombre() : "N/A") +
                '}';
    }
}


