package com.dan.esr.domain.exceptions.cidade;

import com.dan.esr.domain.exceptions.EntidadeNaoPersistidaException;

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
