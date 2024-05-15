package com.dan.esr.domain.exceptions;

import org.springframework.dao.DataAccessException;

public class PersistenciaException extends DataAccessException {

    public PersistenciaException(String message) {
        super(message);
    }

    public PersistenciaException(String message, Throwable cause) {
        super(message, cause);
    }
}
