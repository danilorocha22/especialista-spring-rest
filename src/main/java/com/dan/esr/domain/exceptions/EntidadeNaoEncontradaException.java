package com.dan.esr.domain.exceptions;

import java.io.Serial;

//@ResponseStatus(value = HttpStatus.NOT_FOUND) //, reason = "Entidade não encontrada")
public class EntidadeNaoEncontradaException extends NegocioException {
    @Serial
    private static final long serialVersionUID = 1L;

    public EntidadeNaoEncontradaException() {
        super();
    }

    public EntidadeNaoEncontradaException(String message) {
        super(message);
    }

    public EntidadeNaoEncontradaException(String message, Throwable causa) {
        super(message, causa);
    }
}
