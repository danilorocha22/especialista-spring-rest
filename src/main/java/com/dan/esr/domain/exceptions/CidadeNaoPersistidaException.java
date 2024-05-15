package com.dan.esr.domain.exceptions;

public class CidadeNaoPersistidaException extends EntidadeNaoPersistidaException {

    public CidadeNaoPersistidaException() {
        super();
    }

    public CidadeNaoPersistidaException(String msg) {
        super(msg);
    }

    public CidadeNaoPersistidaException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
