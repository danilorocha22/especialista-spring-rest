package com.dan.esr.domain.exceptions.cozinha;

import com.dan.esr.domain.exceptions.EntidadeNaoEncontradaException;

import java.io.Serial;

public class CozinhaNaoEncontradaException extends EntidadeNaoEncontradaException {
    @Serial
    private static final long serialVersionUID = 1L;

    public CozinhaNaoEncontradaException(String message) {
        super(message);
    }

    public CozinhaNaoEncontradaException(Long id) {
        this("Não existe cozinha cadastrada com ID %s".formatted(id));
    }

    public CozinhaNaoEncontradaException(Long id, Throwable causa) {
        this("Não existe cozinha cadastrada com ID %s".formatted(id), causa);
    }

    public CozinhaNaoEncontradaException(String message, Throwable causa) {
        super(message, causa);
    }
}
