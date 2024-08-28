package com.dan.esr.domain.exceptions;

import javax.persistence.PersistenceException;

public class EntidadeNaoPersistidaException extends PersistenceException {
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