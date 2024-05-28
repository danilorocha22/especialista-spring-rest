package com.dan.esr.domain.exceptions.cozinha;

import com.dan.esr.domain.exceptions.EntidadeNaoEncontradaException;

import java.io.Serial;

public class CozinhaNaoEncontradaException extends EntidadeNaoEncontradaException {
    @Serial
    private static final long serialVersionUID = 1L;

    public CozinhaNaoEncontradaException() {
        this("Nenhuma cozinha encontrada.");
    }

    public CozinhaNaoEncontradaException(Long id) {
        this("Não existe cozinha cadastrada com ID %s.".formatted(id));
    }

    public CozinhaNaoEncontradaException(String message) {
        this(message, null);
    }

    public CozinhaNaoEncontradaException(String message, Throwable causa) {
        super(message, causa);
    }
}