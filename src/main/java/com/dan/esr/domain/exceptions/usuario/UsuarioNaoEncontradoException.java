package com.dan.esr.domain.exceptions.usuario;

import com.dan.esr.domain.exceptions.EntidadeNaoEncontradaException;

public class UsuarioNaoEncontradoException extends EntidadeNaoEncontradaException {

    public UsuarioNaoEncontradoException() {
        this("Nenhum usuário encontrado.");
    }

    public UsuarioNaoEncontradoException(String message) {
        this(message, null);
    }

    public UsuarioNaoEncontradoException(Long id) {
        this(id, null);
    }

    public UsuarioNaoEncontradoException(Long id, Throwable cause) {
        this("Nenhum usuário encontrado com o ID %s".formatted(id), cause);
    }

    public UsuarioNaoEncontradoException(String message, Throwable causa) {
        super(message, causa);
    }
}
