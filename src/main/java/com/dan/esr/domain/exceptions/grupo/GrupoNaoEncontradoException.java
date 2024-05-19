package com.dan.esr.domain.exceptions.grupo;

import com.dan.esr.domain.exceptions.EntidadeNaoEncontradaException;

import java.io.Serial;

public class GrupoNaoEncontradoException extends EntidadeNaoEncontradaException {
    @Serial
    private static final long serialVersionUID = 1L;

    public GrupoNaoEncontradoException() {
        this("Nenhuma grupo encontrado.");
    }

    public GrupoNaoEncontradoException(String message, Throwable causa) {
        super(message, causa);
    }

    public GrupoNaoEncontradoException(String message) {
        this(message, null);
    }

    public GrupoNaoEncontradoException(Long id, Throwable causa) {
        this("Nenhum grupo encontrado com o ID %s".formatted(id), causa);
    }

    public GrupoNaoEncontradoException(Long id) {
        this(id, null);
    }
}
