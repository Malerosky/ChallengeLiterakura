package com.aluracursos.desafio.principal;

import com.aluracursos.desafio.model.*;
import com.aluracursos.desafio.repository.AutorRepository;
import com.aluracursos.desafio.repository.LibroRepository;
import com.aluracursos.desafio.service.ConsumoAPI;
import com.aluracursos.desafio.service.ConvierteDatos;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class Principal {

    private static final String URL_BASE = "https://gutendex.com/books/";
    private final LibroRepository libroRepository;
    private final AutorRepository autorRepository;
    private final ConsumoAPI consumoAPI = new ConsumoAPI();
    private final ConvierteDatos conversor = new ConvierteDatos();
    private final Scanner teclado = new Scanner(System.in);

    public Principal(LibroRepository libroRepository, AutorRepository autorRepository) {
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
    }

    public void muestraElMenu() {
        int opcion = -1;
        while (opcion != 6) {
            System.out.println("""
                ============================
                Elija una opción:
                1 - Buscar libro por título
                2 - Listar libros registrados
                3 - Listar autores registrados
                4 - Listar autores vivos en un determinado año
                5 - Listar libros por idioma
                6 - Salir
                ============================
                """);

            if (!teclado.hasNextInt()) {
                System.out.println("Ingrese un número válido.");
                teclado.nextLine();
                continue;
            }
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1 -> buscarYGuardarLibroPorTitulo();
                case 2 -> listarLibrosRegistrados();
                case 3 -> listarAutoresRegistrados();
                case 4 -> listarAutoresVivosPorAnio();
                case 5 -> listarLibrosPorIdioma();
                case 6 -> System.out.println("Saliendo del programa...");
                default -> System.out.println("Opción inválida, intente de nuevo.");
            }
        }
    }

    @Transactional
    private void buscarYGuardarLibroPorTitulo() {
        System.out.println("Ingrese el título del libro a buscar:");
        String titulo = teclado.nextLine().trim();
        if (titulo.isEmpty()) {
            System.out.println("Título vacío.");
            return;
        }


        var existente = libroRepository.findByTituloIgnoreCase(titulo);
        if (existente.isPresent()) {
            System.out.println("Ya existe en BD: " + existente.get().getTitulo());
            return;
        }

        var json = consumoAPI.obtenerDatos(URL_BASE + "?search=" + titulo.replace(" ", "+"));
        var datosBusqueda = conversor.obtenerDatos(json, Datos.class);

        if (datosBusqueda == null || datosBusqueda.resultados() == null || datosBusqueda.resultados().isEmpty()) {
            System.out.println(" No se encontró el libro en la API.");
            return;
        }


        DatosLibros d = datosBusqueda.resultados().get(0);


        Autor autor = null;
        if (d.autor() != null && !d.autor().isEmpty()) {
            DatosAutor da = d.autor().get(0); //
            String nombre = da.nombre();
            String nac = da.fechaDeNacimiento();
            String def = da.fechaDeFallecimiento();

            autor = autorRepository.findByNombreIgnoreCase(nombre)
                    .orElseGet(() -> new Autor(nombre, nac, def));
        }

        Integer descargas = d.numeroDeDescargas() != null
                ? d.numeroDeDescargas().intValue()
                : 0;

        Libro libro = new Libro(d.titulo(), descargas, autor);
        libroRepository.save(libro);

        System.out.println("Guardado en BD: " + libro.getTitulo());
    }

    private void listarLibrosRegistrados() {
        var libros = libroRepository.findAll();
        if (libros.isEmpty()) {
            System.out.println("No hay libros registrados aún.");
            return;
        }
        libros.forEach(l ->
                System.out.printf("- %s | Descargas: %s | Autor: %s%n",
                        l.getTitulo(),
                        l.getNumeroDeDescargas(),
                        l.getAutor() != null ? l.getAutor().getNombre() : "N/A"));
    }

    private void listarAutoresRegistrados() {
        var autores = autorRepository.findAll();
        if (autores.isEmpty()) {
            System.out.println("No hay autores registrados aún.");
            return;
        }
        autores.forEach(a ->
                System.out.printf("- %s (nac: %s, fallec: %s)%n",
                        a.getNombre(), a.getFechaDeNacimiento(), a.getFechaDeFallecimiento()));
    }

    private void listarAutoresVivosPorAnio() {
        System.out.println("Ingrese el año a consultar:");
        if (!teclado.hasNextInt()) {
            System.out.println("Año inválido.");
            teclado.nextLine();
            return;
        }
        int anio = teclado.nextInt();
        teclado.nextLine();

        var autoresVivos = autorRepository.findAll().stream()
                .filter(a -> {
                    Integer nac = toInt(a.getFechaDeNacimiento());
                    Integer def = toInt(a.getFechaDeFallecimiento());
                    if (nac == null) return false;
                    if (def == null) return anio >= nac;
                    return anio >= nac && anio <= def;
                })
                .map(Autor::getNombre)
                .collect(Collectors.toCollection(TreeSet::new));

        if (autoresVivos.isEmpty()) {
            System.out.println("No se encontraron autores vivos en ese año.");
        } else {
            autoresVivos.forEach(System.out::println);
        }
    }

    private Integer toInt(String s) {
        try {
            return (s == null || s.isBlank()) ? null : Integer.parseInt(s.trim());
        } catch (Exception e) {
            return null;
        }
    }

    private void listarLibrosPorIdioma() {
        System.out.println("Ingrese el código de idioma (ej: en, es, fr):");
        String idioma = teclado.nextLine().trim();
        if (idioma.isEmpty()) {
            System.out.println("Idioma vacío.");
            return;
        }

        System.out.println("👉 Funcionalidad pendiente (no se guarda idioma en BD con este modelo simplificado).");
    }
}


