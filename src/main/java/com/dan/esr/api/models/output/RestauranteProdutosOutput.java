package com.dan.esr.api.models.output;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RestauranteProdutosOutput {
    private Long id;
    private String nome;
    @JsonProperty("produtos") private List<ProdutoOutput> produtoOutputs;
}