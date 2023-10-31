package com.dan.esr.domain.exceptions;

import java.io.Serial;

//@ResponseStatus(HttpStatus.CONFLICT)
public class EntidadeEmUsoException extends NegocioException {
    @Serial
    private static final long serialVersionUID = 1L;

    public EntidadeEmUsoException(String message) {
        super(message);
    }
}
