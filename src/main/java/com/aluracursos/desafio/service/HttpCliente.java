package com.aluracursos.desafio.service;

import java.io.IOException;
import java.net.http.HttpClient;

public class HttpCliente {

    private final HttpClient client;

    public HttpCliente() {
        this.client = HttpClient.newHttpClient();
    }

    public HttpClient getClient() {
        return client;
    }
}
