package com.dan.esr.api.models.output;

import com.dan.esr.domain.entities.Endereco;
import com.dan.esr.domain.entities.Produto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class RestauranteOutput {
    private Long id;
    private String nome;
    private BigDecimal taxaFrete;
    @JsonProperty("cozinha") private CozinhaOutput cozinhaOutput;
    @JsonProperty("formasDePagamento") private List<FormasDePagamentoOutput> formasDePagamentoOutput;
    //private List<ProdutoOutput> produtos;
    private Endereco endereco;
}