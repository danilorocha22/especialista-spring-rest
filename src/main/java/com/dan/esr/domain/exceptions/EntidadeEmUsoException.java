package com.dan.esr.domain.exceptions;

import java.io.Serial;

public class EntidadeEmUsoException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public EntidadeEmUsoException(String message) {
        super(message);
    }
}
