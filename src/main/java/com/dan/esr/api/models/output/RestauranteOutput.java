package com.dan.esr.api.models.output;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Setter
public class RestauranteOutput {
    private Long id;
    private String nome;
    private boolean ativo;
    private BigDecimal taxaFrete;
    @JsonProperty("cozinha") private String nomeCozinha;
    @JsonProperty("formasDePagamento") private List<String> descricaoFormasPagamento;
    private EnderecoOutput endereco;
    private OffsetDateTime dataCadastro;
    //private List<ProdutoOutput> produtos;
}