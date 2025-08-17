package com.aluracursos.desafio.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "autores")
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 300)
    private String nombre;

    @Column(length = 10)
    private String fechaDeNacimiento;

    @Column(length = 10)
    private String fechaDeFallecimiento;

    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Libro> libros = new ArrayList<>();

    public Autor() {}

    public Autor(String nombre, String fechaDeNacimiento, String fechaDeFallecimiento) {
        this.nombre = nombre;
        this.fechaDeNacimiento = fechaDeNacimiento;
        this.fechaDeFallecimiento = fechaDeFallecimiento;
    }

    // Getters y Setters
    public Long getId() { return id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getFechaDeNacimiento() { return fechaDeNacimiento; }
    public void setFechaDeNacimiento(String fechaDeNacimiento) { this.fechaDeNacimiento = fechaDeNacimiento; }

    public String getFechaDeFallecimiento() { return fechaDeFallecimiento; }
    public void setFechaDeFallecimiento(String fechaDeFallecimiento) { this.fechaDeFallecimiento = fechaDeFallecimiento; }

    public List<Libro> getLibros() { return libros; }
    public void setLibros(List<Libro> libros) { this.libros = libros; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Autor)) return false;
        Autor autor = (Autor) o;
        return Objects.equals(nombre == null ? null : nombre.toLowerCase(),
                autor.nombre == null ? null : autor.nombre.toLowerCase());
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre == null ? null : nombre.toLowerCase());
    }

    @Override
    public String toString() {
        return "Autor{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", fechaDeNacimiento='" + fechaDeNacimiento + '\'' +
                ", fechaDeFallecimiento='" + fechaDeFallecimiento + '\'' +
                '}';
    }
}
