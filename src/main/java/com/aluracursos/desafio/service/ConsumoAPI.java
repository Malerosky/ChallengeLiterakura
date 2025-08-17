package com.aluracursos.desafio.service;

public class ConsumoAPI {
    private final HttpCliente cliente = new HttpCliente();
    private final HttpRequestPersonalizado requestBuilder = new HttpRequestPersonalizado();
    private final HttpResponseGestor responseGestor = new HttpResponseGestor();

    public String obtenerDatos(String url) {
        var request = requestBuilder.crearRequest(url);
        return responseGestor.obtenerRespuesta(cliente.getClient(), request);
    }
}
