package com.dan.esr.domain.services;

import com.dan.esr.domain.entities.Estado;
import com.dan.esr.domain.repositories.EstadoRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@AllArgsConstructor
@Service
public class EstadoService {

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
                .uri(URI.create("https://servicodados.ibge.gov.br/api/v1/localidades/estados"))
                .GET()
                .build();
    }

    private List<Estado> parseToEstados(String jsonEstados) throws JsonProcessingException {
        return new ObjectMapper().readValue(jsonEstados, new TypeReference<>() {
        });
    }


}
