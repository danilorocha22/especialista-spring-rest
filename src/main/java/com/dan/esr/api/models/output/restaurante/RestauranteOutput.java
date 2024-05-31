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

    @JsonView({RestauranteView.Resumo.class, RestauranteView.Status.class,
            RestauranteView.FormaPagamento.class, RestauranteView.Aberto.class})
    private Long id;

    @JsonProperty("restaurante")
    @JsonView({RestauranteView.Resumo.class, RestauranteView.Status.class,
            RestauranteView.FormaPagamento.class, RestauranteView.Aberto.class})
    private String nome;

    @JsonView({RestauranteView.Resumo.class, RestauranteView.Status.class})
    private boolean ativo;

    @JsonView({RestauranteView.Aberto.class})
    private boolean aberto;

    @JsonView(RestauranteView.Resumo.class)
    private BigDecimal taxaFrete;

    @JsonView(RestauranteView.Resumo.class)
    @JsonProperty("cozinha")
    private String nomeCozinha;

    @JsonProperty("formasDePagamento")
    @JsonView({RestauranteView.FormaPagamento.class})
    private List<String> descricaoFormasPagamento;

    private EnderecoOutput endereco;
    private OffsetDateTime dataCadastro;
}