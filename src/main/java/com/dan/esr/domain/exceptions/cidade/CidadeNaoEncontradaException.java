package com.dan.esr.domain.exceptions.cidade;

import com.dan.esr.domain.exceptions.EntidadeNaoEncontradaException;

import java.io.Serial;

public class CidadeNaoEncontradaException extends EntidadeNaoEncontradaException {
    @Serial
    private static final long serialVersionUID = 1L;

    public CidadeNaoEncontradaException(String message) {
        super(message);
    }

    public CidadeNaoEncontradaException(Long id) {
        this("Não existe cidade cadastrada com ID %s".formatted(id));
    }

    public CidadeNaoEncontradaException(Long id, Throwable cause) {
        this("Não existe cidade cadastrada com ID %s".formatted(id), cause);
    }

    public CidadeNaoEncontradaException(String message, Throwable causa) {
        super(message, causa);
    }
}
