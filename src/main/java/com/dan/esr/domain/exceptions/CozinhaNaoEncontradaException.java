package com.dan.esr.domain.exceptions;

import java.io.Serial;

public class CozinhaNaoEncontradaException extends EntidadeNaoEncontradaException {
    @Serial
    private static final long serialVersionUID = 1L;

    public CozinhaNaoEncontradaException(String message) {
        super(message);
    }

    public CozinhaNaoEncontradaException(Long id) {
        this(String.format("Cozinha não encontrada com ID %s", id));
    }

}
