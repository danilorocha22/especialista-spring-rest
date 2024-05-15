package com.dan.esr.domain.services;

import com.dan.esr.domain.entities.Estado;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@Service
public class ListaEstadosApiService {
    public static final String URL_IBGE_ESTADOS = "https://servicodados.ibge.gov.br/api/v1/localidades/estados";

    public List<Estado> estados() {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = newRequest();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return (response.statusCode() == 200) ? parseToEstados(response.body()) : null;
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    private HttpRequest newRequest() {
        return HttpRequest.newBuilder()
                .uri(URI.create(URL_IBGE_ESTADOS))
                .GET()
                .build();
    }

    private List<Estado> parseToEstados(String jsonEstados) throws JsonProcessingException {
        return new ObjectMapper().readValue(jsonEstados, new TypeReference<>() {
        });
    }


}
