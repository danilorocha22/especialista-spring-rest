package com.dan.esr.domain.exceptions;

import java.io.Serial;

//@ResponseStatus(HttpStatus.CONFLICT)
public class EntidadeExistenteException extends NegocioException {
    @Serial
    private static final long serialVersionUID = 1L;

    public EntidadeExistenteException(String message) {
        super(message);
    }

    public EntidadeExistenteException(String message, Throwable causa) {
        super(message, causa);
    }
}
