package com.dan.esr.domain.exceptions.produto;

import com.dan.esr.domain.exceptions.EntidadeNaoEncontradaException;

public class ProdutoNaoEncontradoException extends EntidadeNaoEncontradaException {

    public ProdutoNaoEncontradoException() {
        this("Nenhum produto encontrado.");
    }

    public ProdutoNaoEncontradoException(Long id) {
        this("Nenhum produto encontrado com o ID %s".formatted(id));
    }

    public ProdutoNaoEncontradoException(String message) {
        this(message, null);
    }

    public ProdutoNaoEncontradoException(String message, Throwable causa) {
        super(message, causa);
    }
}
