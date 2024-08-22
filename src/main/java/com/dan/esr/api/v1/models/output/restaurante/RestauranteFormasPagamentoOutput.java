package com.dan.esr.api.v1.models.output.restaurante;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ApiModel("Formas de Pagamento do Restaurante")
@Relation(collectionRelation = "formas-pagamento", itemRelation = "forma-pagamento")
public class RestauranteFormasPagamentoOutput extends RepresentationModel<RestauranteFormasPagamentoOutput> {

    @ApiModelProperty(example = "1")
    private Long id;

    @ApiModelProperty(example = "Thai Gourmet")
    private String nome;

    @JsonProperty("formasDePagamento")
    private List<String> nomeFormasPagamento;
}