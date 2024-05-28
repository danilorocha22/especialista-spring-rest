package com.dan.esr.domain.exceptions.usuario;

import com.dan.esr.domain.exceptions.EntidadeNaoEncontradaException;

public class UsuarioNaoEncontradoException extends EntidadeNaoEncontradaException {

    public UsuarioNaoEncontradoException() {
        this("Nenhum usuário encontrado.");
    }

    public UsuarioNaoEncontradoException(Long id) {
        this("Nenhum usuário encontrado com o ID %s".formatted(id), null);
    }

    public UsuarioNaoEncontradoException(String message) {
        this(message, null);
    }

    public UsuarioNaoEncontradoException(String message, Throwable causa) {
        super(message, causa);
    }
}