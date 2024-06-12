package com.dan.esr.domain.exceptions.produto;

import com.dan.esr.domain.exceptions.EntidadeNaoEncontradaException;

public class FotoProdutoNaoEncontradaException extends EntidadeNaoEncontradaException {

    public FotoProdutoNaoEncontradaException(Long restauranteId, Long produtoId) {
        this(("Nenhuma foto cadastrada para o produto com código %s " +
                "para restaurante com código %s").formatted(produtoId, restauranteId), null);
    }

    public FotoProdutoNaoEncontradaException(String message, Throwable causa) {
        super(message, causa);
    }
}