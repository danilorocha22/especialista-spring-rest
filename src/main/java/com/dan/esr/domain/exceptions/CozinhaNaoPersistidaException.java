package com.dan.esr.domain.exceptions;

public class CozinhaNaoPersistidaException extends EntidadeNaoPersistidaException {

    public CozinhaNaoPersistidaException() {
        super();
    }

    public CozinhaNaoPersistidaException(String msg) {
        super(msg);
    }

    public CozinhaNaoPersistidaException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
