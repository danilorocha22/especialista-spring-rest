package com.dan.esr.domain.exceptions;

import java.io.Serial;

public class EntidadeComAtributoNuloException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public EntidadeComAtributoNuloException(String message) {
        super(message);
    }
}
