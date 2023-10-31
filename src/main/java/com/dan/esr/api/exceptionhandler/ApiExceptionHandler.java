package com.dan.esr.api.exceptionhandler;

import com.dan.esr.domain.exceptions.EntidadeNaoEncontradaException;
import com.dan.esr.domain.exceptions.NegocioException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.dan.esr.api.exceptionhandler.Problema.novoProblema;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    public ResponseEntity<?> tratarEntidadeNaoEncontradaException(EntidadeNaoEncontradaException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(novoProblema(e));
    }

    @ExceptionHandler(NegocioException.class)
    public ResponseEntity<?> tratarNegocioException(NegocioException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(novoProblema(e));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> tratarHttpMediaTypeNotSupportedException() {
        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                .body(novoProblema("O tipo de mídia não é aceito"));
    }

}
