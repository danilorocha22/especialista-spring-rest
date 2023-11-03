package com.dan.esr.domain.exceptions;

import java.io.Serial;

//@ResponseStatus(HttpStatus.CONFLICT)
public class PropriedadeIlegalException extends NegocioException {
    @Serial
    private static final long serialVersionUID = 1L;

    public PropriedadeIlegalException(String message) {
        super(message);
    }
}
