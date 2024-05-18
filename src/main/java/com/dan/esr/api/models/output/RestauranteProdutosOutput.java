package com.dan.esr.api.models.output;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RestauranteProdutosOutput {
    private Long id;
    private String nome;
    @JsonProperty("produtos") private List<ProdutoOutput> produtos;

    public RestauranteProdutosOutput(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }
}