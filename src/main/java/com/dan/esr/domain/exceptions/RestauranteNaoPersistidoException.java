package com.dan.esr.domain.exceptions;

import java.io.Serial;

public class RestauranteNaoPersistidoException extends EntidadeNaoPersistidaException {
    @Serial
    private static final long serialVersionUID = 1L;

    public RestauranteNaoPersistidoException() {
        super();
    }

    public RestauranteNaoPersistidoException(String message) {
        super(message);
    }

}
