package com.dan.esr.domain.exceptions.permissao;

import com.dan.esr.domain.exceptions.EntidadeNaoEncontradaException;

public class PermissaoNaoEncontradoException extends EntidadeNaoEncontradaException {

    public PermissaoNaoEncontradoException() {
        this("Nenhuma permissão encontrada.");
    }

    public PermissaoNaoEncontradoException(String message, Throwable causa) {
        super(message, causa);
    }

    public PermissaoNaoEncontradoException(String message) {
        this(message, null);
    }

    public PermissaoNaoEncontradoException(Long id, Throwable causa) {
        this("Nenhuma permissão encontrada com o ID %s".formatted(id), causa);
    }

    public PermissaoNaoEncontradoException(Long id) {
        this(id, null);
    }
}
