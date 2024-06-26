package com.dan.esr.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

//@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NegocioException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public NegocioException() {
        super();
    }

    public NegocioException(String message) {
        this(message, null);
    }

    public NegocioException(Exception ex) {
        this(null, ex);
    }

    public NegocioException(String message, Throwable causa) {
        super(message, causa);
    }
}
