package com.dan.esr.api.models.output.restaurante;

import com.dan.esr.api.models.output.endereco.EnderecoOutput;
import com.dan.esr.api.models.output.view.RestauranteView;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Setter
public class RestauranteOutput {

    @JsonView({RestauranteView.Resumo.class, RestauranteView.Status.class})
    private Long id;

    @JsonProperty("restaurante")
    @JsonView({RestauranteView.Resumo.class, RestauranteView.Status.class})
    private String nome;

    @JsonView({RestauranteView.Resumo.class, RestauranteView.Status.class})
    private boolean ativo;

    private boolean aberto;

    @JsonView(RestauranteView.Resumo.class)
    private BigDecimal taxaFrete;

    @JsonView(RestauranteView.Resumo.class)
    @JsonProperty("cozinha")
    private String nomeCozinha;

    @JsonProperty("formasDePagamento")
    private List<String> descricaoFormasPagamento;

    private EnderecoOutput endereco;
    private OffsetDateTime dataCadastro;
}