package com.dan.esr.domain.exceptions;

import java.io.Serial;

public class EstadoNaoEncontradoException extends EntidadeNaoEncontradaException {
    @Serial
    private static final long serialVersionUID = 1L;

    public EstadoNaoEncontradoException(String message) {
        super(message);
    }

    public EstadoNaoEncontradoException(Long id) {
        this(String.format("Não existe estado cadastrado com ID %s", id));
    }
}
