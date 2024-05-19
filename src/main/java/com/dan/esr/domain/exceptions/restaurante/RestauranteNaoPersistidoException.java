package com.dan.esr.domain.exceptions.restaurante;

import com.dan.esr.domain.exceptions.EntidadeNaoPersistidaException;

import java.io.Serial;

public class RestauranteNaoPersistidoException extends EntidadeNaoPersistidaException {
    @Serial
    private static final long serialVersionUID = 1L;

    public RestauranteNaoPersistidoException(String message, Throwable cause) {
        super(message, cause);
    }

    public RestauranteNaoPersistidoException(String message) {
        super(message);
    }

}
