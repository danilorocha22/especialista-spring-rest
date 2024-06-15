package com.dan.esr.infrastructure.services.reports;

public class RelatorioException extends RuntimeException {

    public RelatorioException(String message) {
        super(message);
    }

    public RelatorioException(String message, Throwable cause) {
        super(message, cause);
    }
}