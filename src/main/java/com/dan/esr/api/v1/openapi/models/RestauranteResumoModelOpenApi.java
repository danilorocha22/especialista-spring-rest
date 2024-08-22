package com.dan.esr.api.v1.openapi.models;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@ApiModel("RestauranteResumoOutput")
public class RestauranteResumoModelOpenApi {
    @ApiModelProperty(example = "1")
    private Long id;

    @ApiModelProperty(example = "Thai Gourmet")
    private String nome;

    @ApiModelProperty(example = "true")
    private boolean ativo;

    @ApiModelProperty(example = "R$ 25,00")
    private BigDecimal taxaFrete;

    @ApiModelProperty(example = "Brasileira")
    private String nomeCozinha;
}
