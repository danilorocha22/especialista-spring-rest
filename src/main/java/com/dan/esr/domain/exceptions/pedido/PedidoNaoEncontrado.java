package com.dan.esr.domain.exceptions.pedido;

import com.dan.esr.domain.exceptions.EntidadeNaoEncontradaException;

public class PedidoNaoEncontrado extends EntidadeNaoEncontradaException {

    public PedidoNaoEncontrado() {
        this("Nenhum pedido encontrado.");
    }

    public PedidoNaoEncontrado(Long id) {
        this("Nenhum pedido encontrado com ID %s.".formatted(id));
    }

    public PedidoNaoEncontrado(String message) {
        this(message, null);
    }

    public PedidoNaoEncontrado(String message, Throwable ex) {
        super(message, ex);
    }
}
