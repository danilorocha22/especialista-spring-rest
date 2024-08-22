package com.dan.esr.api.v1.models.output.restaurante;

import com.dan.esr.api.v1.models.output.view.PedidoView;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("Restaurante")
@JsonView(PedidoView.Resumo.class)
public class RestauranteIdNomeOutput {
    @ApiModelProperty(example = "1")
    private Long id;

    @ApiModelProperty(example = "Restaurante da Mamãe")
    private String nome;
}