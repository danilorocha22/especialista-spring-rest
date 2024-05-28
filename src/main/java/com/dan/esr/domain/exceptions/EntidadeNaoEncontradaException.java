package com.dan.esr.domain.exceptions;

import java.io.Serial;

//@ResponseStatus(value = HttpStatus.NOT_FOUND) //, reason = "Entidade não encontrada")
public class EntidadeNaoEncontradaException extends NegocioException {
    @Serial
    private static final long serialVersionUID = 1L;

    public EntidadeNaoEncontradaException() {
        this("Nenhuma entidade encontrada.");
    }

    public EntidadeNaoEncontradaException(String message) {
        this(message, null);
    }

    public EntidadeNaoEncontradaException(String message, Throwable causa) {
        super(message, causa);
    }
}
