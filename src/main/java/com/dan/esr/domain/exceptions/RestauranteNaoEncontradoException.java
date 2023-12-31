package com.dan.esr.domain.exceptions;

import java.io.Serial;

public class RestauranteNaoEncontradoException extends EntidadeNaoEncontradaException {
    @Serial
    private static final long serialVersionUID = 1L;

    public RestauranteNaoEncontradoException(String message) {
        super(message);
    }

    public RestauranteNaoEncontradoException(Long id) {
        this(String.format("Não existe restaurante cadastrado com ID %s", id));
    }

}
