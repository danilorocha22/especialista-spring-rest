package com.dan.esr.api.v1.models.output.restaurante;

import com.dan.esr.api.v1.models.output.endereco.EnderecoOutput;
import com.dan.esr.api.v1.models.output.view.RestauranteView;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Setter
@ApiModel("Restaurante")
@EqualsAndHashCode(callSuper = true)
@Relation(collectionRelation = "restaurantes", itemRelation = "restaurante")
public class RestauranteOutput extends RepresentationModel<RestauranteOutput> {

    @ApiModelProperty(example = "1")
    @JsonView({RestauranteView.Resumo.class, RestauranteView.Status.class, RestauranteView.Pedido.class,
            RestauranteView.FormaPagamento.class, RestauranteView.Aberto.class})
    private Long id;

    //@JsonProperty("restaurante")
    @ApiModelProperty(example = "Thai Gourmet")
    @JsonView({RestauranteView.Resumo.class, RestauranteView.Status.class, RestauranteView.Pedido.class,
            RestauranteView.FormaPagamento.class, RestauranteView.Aberto.class})
    private String nome;

    @ApiModelProperty(example = "true")
    @JsonView({RestauranteView.Resumo.class, RestauranteView.Status.class})
    private boolean ativo;

    @ApiModelProperty(example = "false")
    @JsonView({RestauranteView.Aberto.class})
    private boolean aberto;

    @ApiModelProperty(example = "R$ 5,00")
    @JsonView(RestauranteView.Resumo.class)
    private BigDecimal taxaFrete;

    @JsonProperty("cozinha")
    @JsonView(RestauranteView.Resumo.class)
    @ApiModelProperty(example = "Brasileira")
    private String nomeCozinha;

    @JsonProperty("formasDePagamento")
    @JsonView({RestauranteView.FormaPagamento.class})
    private List<String> nomeFormasPagamento;

    private EnderecoOutput endereco;

    @ApiModelProperty(example = "2019-12-01T20:34:04Z")
    private OffsetDateTime dataCadastro;
}