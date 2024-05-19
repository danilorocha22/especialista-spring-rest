package com.dan.esr.domain.exceptions.cozinha;

import com.dan.esr.domain.exceptions.EntidadeNaoPersistidaException;

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
