package com.dan.esr.api.exceptionhandler;

import com.dan.esr.domain.exceptions.EntidadeEmUsoException;
import com.dan.esr.domain.exceptions.EntidadeNaoEncontradaException;
import com.dan.esr.domain.exceptions.NegocioException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import lombok.NonNull;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static com.dan.esr.api.exceptionhandler.Problem.*;

@RestControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  @NonNull HttpHeaders headers,
                                                                  @NonNull HttpStatusCode status,
                                                                  @NonNull WebRequest request) {
        Throwable rootCause = ExceptionUtils.getRootCause(ex);

        if (rootCause instanceof InvalidFormatException) {
            try {
                return handleInvalidFormatException((InvalidFormatException) rootCause, headers, status, request);
            } catch (NoSuchFieldException e) {
                throw new NegocioException(e.getMessage());
            }
        }

        String detail = "O corpo da requisição está inválido. Verifique erro de sintaxe.";
        Problem problem = createProblemBuilder(ProblemType.FALHA_AO_LER_REQUISICAO, status, detail).build();

        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    public ResponseEntity<?> handleEntidadeNaoEncontradaException(EntidadeNaoEncontradaException ex,
                                                                  WebRequest request) {
        HttpStatusCode status = HttpStatus.NOT_FOUND;
        Problem problem = createProblemBuilder(ProblemType.ENTIDADE_NAO_ENCONTRADA, status, ex.getMessage()).build();

        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(NegocioException.class)
    public ResponseEntity<?> handleNegocioException(NegocioException ex, WebRequest request) {
        HttpStatusCode status = HttpStatus.BAD_REQUEST;
        Problem problem = createProblemBuilder(ProblemType.ERRO_NA_REQUISICAO, status, ex.getMessage()).build();

        return handleExceptionInternal(ex, problem, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(EntidadeEmUsoException.class)
    public ResponseEntity<?> handleEntidadeEmUsoException(EntidadeEmUsoException ex, WebRequest request) {
        HttpStatusCode status = HttpStatus.CONFLICT;
        Problem problem = createProblemBuilder(ProblemType.ENTIDADE_EM_USO, status, ex.getMessage()).build();

        return handleExceptionInternal(ex, problem, new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(@NonNull Exception ex, Object body,
                                                             @NonNull HttpHeaders headers,
                                                             @NonNull HttpStatusCode status,
                                                             @NonNull WebRequest request) {
        if (body == null) {
            body = novoProblema(ex, status);
        } else if (body instanceof String) {
            body = novoProblema((String) body, status);
        }

        return super.handleExceptionInternal(ex, body, headers, status, request);
    }

    private ResponseEntity<Object> handleInvalidFormatException(InvalidFormatException ex, HttpHeaders headers,
                                                                HttpStatusCode status, WebRequest request) throws NoSuchFieldException {

        String property = getProperty(ex);
        String propertyType = "";

        if (getPropertyType(ex).isPresent()){
            propertyType = getPropertyType(ex).get();
        }

        String detail = String.format("A propriedade '%s' recebeu o valor '%s', que é um tipo inválido. Corrija" +
                " e informe um valor compatível com o tipo %s.", property, ex.getValue(), propertyType);

        Problem problem = createProblemBuilder(ProblemType.FALHA_AO_LER_REQUISICAO, status, detail).build();

        return handleExceptionInternal(ex, problem, headers, status, request);
    }
}