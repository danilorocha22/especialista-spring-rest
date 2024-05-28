package com.dan.esr.api.models.input.itempedido;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter @Setter
public class ItemPedidoInput {
    @NotNull private Long produtoId;
    @NotNull @PositiveOrZero private Integer quantidade;
    private String observacao;
}