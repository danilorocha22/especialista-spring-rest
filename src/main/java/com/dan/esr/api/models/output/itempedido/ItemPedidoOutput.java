package com.dan.esr.api.models.output.itempedido;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter @Setter
public class ItemPedidoOutput {
    private Long produtoId;
    private String produtoNome;
    private Integer quantidade;
    private BigDecimal precoUnitario;
    private BigDecimal precoTotal;
    private String observacao;
}