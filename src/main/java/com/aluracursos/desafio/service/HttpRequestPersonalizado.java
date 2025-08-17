package com.aluracursos.desafio.service;

import java.net.URI;
import java.net.http.HttpRequest;

public class HttpRequestPersonalizado {

    public HttpRequest crearRequest(String url) {
        return HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();
    }
}
