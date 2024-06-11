package com.dan.esr.domain.exceptions.produto;

import com.dan.esr.domain.exceptions.EntidadeNaoEncontradaException;

public class FotoProdutoNaoEncontradaException extends EntidadeNaoEncontradaException {

    public FotoProdutoNaoEncontradaException() {
        this("Nenhuma foto de produto encontrada", null);
    }

    public FotoProdutoNaoEncontradaException(Long id) {
        this("Nenhuma foto de produto, encontrada com ID: %s". formatted(id), null);
    }

    public FotoProdutoNaoEncontradaException(String message, Throwable causa) {
        super(message, causa);
    }
}
