package com.dan.esr.api.v1.models.input.itempedido;

import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemPedidoInput {

    @NotNull
    @ApiModelProperty(example = "1", required = true)
    private Long produtoId;

    @NotNull
    @PositiveOrZero
    @ApiModelProperty(example = "2", required = true)
    private Integer quantidade;

    @ApiModelProperty(example = "Menos picante, por favor")
    private String observacao;
}