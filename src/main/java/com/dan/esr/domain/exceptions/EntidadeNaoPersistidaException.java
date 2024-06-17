package com.dan.esr.domain.exceptions;

import jakarta.persistence.PersistenceException;

import java.io.Serial;

public class EntidadeNaoPersistidaException extends PersistenceException {
    @Serial
    private static final long serialVersionUID = 1L;

    public EntidadeNaoPersistidaException() {
        super();
    }

    public EntidadeNaoPersistidaException(String message) {
        super(message);
    }

    public EntidadeNaoPersistidaException(String msg, Throwable cause) {
        super(msg, cause);
    }
}