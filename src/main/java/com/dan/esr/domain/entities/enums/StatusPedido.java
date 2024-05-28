package com.dan.esr.domain.entities.enums;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum StatusPedido {
    CRIADO,
    CONFIRMADO("CRIADO"),
    ENTREGUE("CONFIRMADO"),
    CANCELADO("CRIADO","CONFIRMADO");

    private final String[] descricoes;

    StatusPedido(String ...statusPermitidos) {
        this.descricoes = statusPermitidos;
    }

    public boolean isStatusNaoPermitido(StatusPedido novoStatus) {
        return !Arrays.asList(novoStatus.getDescricoes()).contains(this.name());
    }
}