package com.dan.esr.domain.exceptions.restaurante;

import com.dan.esr.domain.exceptions.EntidadeNaoEncontradaException;

public class RestauranteNaoEncontradoException extends EntidadeNaoEncontradaException {

    public RestauranteNaoEncontradoException(String message) {
        super(message);
    }

    public RestauranteNaoEncontradoException(Long id) {
        this("Nenhum restaurante encontrado com ID %s".formatted(id));
    }

}
