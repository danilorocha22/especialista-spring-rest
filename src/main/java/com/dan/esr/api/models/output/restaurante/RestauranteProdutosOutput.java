package com.dan.esr.api.models.output.restaurante;

import com.dan.esr.api.models.output.produto.ProdutoOutput;
import lombok.*;

import java.util.List;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class RestauranteProdutosOutput {
    private Long id;
    private String nome;
    private List<ProdutoOutput> produtos;

    public RestauranteProdutosOutput(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }
}