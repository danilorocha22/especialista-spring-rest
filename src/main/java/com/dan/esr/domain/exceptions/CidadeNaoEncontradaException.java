package com.dan.esr.domain.exceptions;

import java.io.Serial;

public class CidadeNaoEncontradaException extends EntidadeNaoEncontradaException {
    @Serial
    private static final long serialVersionUID = 1L;

    public CidadeNaoEncontradaException(String message) {
        super(message);
    }

    public CidadeNaoEncontradaException(Long id) {
        this(String.format("Não existe cidade cadastrada com ID %s", id));
    }

}
