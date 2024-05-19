package com.dan.esr.domain.exceptions.restaurante;

import com.dan.esr.domain.exceptions.EntidadeNaoEncontradaException;

import java.io.Serial;

public class RestauranteNaoEncontradoException extends EntidadeNaoEncontradaException {
    @Serial
    private static final long serialVersionUID = 1L;

    public RestauranteNaoEncontradoException(String message) {
        super(message);
    }

    public RestauranteNaoEncontradoException(Long id) {
        this("Nenhum restaurante cadastrado com ID %s".formatted(id));
    }

}
