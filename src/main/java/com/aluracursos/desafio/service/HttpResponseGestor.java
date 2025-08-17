package com.aluracursos.desafio.service;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;

public class HttpResponseGestor {

    public String obtenerRespuesta(HttpClient client, HttpRequest request) {
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Error al procesar la respuesta de la API", e);
        }
    }
}
