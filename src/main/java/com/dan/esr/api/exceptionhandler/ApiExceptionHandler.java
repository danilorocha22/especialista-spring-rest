package com.dan.esr.api.exceptionhandler;

import com.dan.esr.domain.exceptions.EntidadeEmUsoException;
import com.dan.esr.domain.exceptions.EntidadeNaoEncontradaException;
import com.dan.esr.domain.exceptions.NegocioException;
import com.fasterxml.jackson.databind.exc.IgnoredPropertyException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
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
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Objects;

import static com.dan.esr.api.exceptionhandler.Problem.*;

@RestControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(@NonNull HttpMessageNotReadableException ex,
                                                                  @NonNull HttpHeaders headers,
                                                                  @NonNull HttpStatusCode status,
                                                                  @NonNull WebRequest req) {
        Throwable cause = ExceptionUtils.getRootCause(ex);
        String detail = "O corpo da requisição está inválido. Verifique erro de sintaxe.";
        Problem problem = createProblemBuilder(ProblemType.FALHA_AO_LER_REQUISICAO, status, detail).build();

        return switch (cause.getClass().getSimpleName()) {
            case ("InvalidFormatException") ->
                    handleInvalidFormatException((InvalidFormatException) cause, headers, status, req);

            case ("IgnoredPropertyException") ->
                    handleIgnoredPropertyException((IgnoredPropertyException) cause, headers, status, req);

            case ("UnrecognizedPropertyException") ->
                    handleUnrecognizedPropertyException((UnrecognizedPropertyException) cause, headers, status, req);

            default -> handleExceptionInternal(ex, problem, new HttpHeaders(), status, req);
        };
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<?> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex,
                                                                       WebRequest req) {
        HttpStatusCode status = HttpStatus.BAD_REQUEST;

        String detail = String.format(" O parâmetro '%s' com valor '%s', é inválido. Requer um tipo %s.",
                        ex.getName(), ex.getValue(), Objects.requireNonNull(ex.getRequiredType()).getSimpleName());

        Problem problem = createProblemBuilder(ProblemType.PARAMETRO_INVALIDO, status, detail).build();

        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, req);
    }

    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    public ResponseEntity<?> handleEntidadeNaoEncontradaException(EntidadeNaoEncontradaException ex,
                                                                  WebRequest req) {
        HttpStatusCode status = HttpStatus.NOT_FOUND;
        Problem problem = createProblemBuilder(ProblemType.ENTIDADE_NAO_ENCONTRADA, status, ex.getMessage()).build();

        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, req);
    }

    @ExceptionHandler(NegocioException.class)
    public ResponseEntity<?> handleNegocioException(NegocioException ex, WebRequest req) {
        HttpStatusCode status = HttpStatus.BAD_REQUEST;
        Problem problem = createProblemBuilder(ProblemType.ERRO_NA_REQUISICAO, status, ex.getMessage()).build();

        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, req);
    }

    @ExceptionHandler(EntidadeEmUsoException.class)
    public ResponseEntity<?> handleEntidadeEmUsoException(EntidadeEmUsoException ex, WebRequest req) {
        HttpStatusCode status = HttpStatus.CONFLICT;
        Problem problem = createProblemBuilder(ProblemType.ENTIDADE_EM_USO, status, ex.getMessage()).build();

        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, req);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(@NonNull Exception ex, Object body,
                                                             @NonNull HttpHeaders headers,
                                                             @NonNull HttpStatusCode status,
                                                             @NonNull WebRequest req) {
        if (body == null) {
            body = novoProblema(ex, status);
        } else if (body instanceof String) {
            body = novoProblema((String) body, status);
        }

        return super.handleExceptionInternal(ex, body, headers, status, req);
    }

    private ResponseEntity<Object> handleInvalidFormatException(InvalidFormatException ex, HttpHeaders headers,
                                                                HttpStatusCode status, WebRequest req) {
        String property = getProperty(ex);

        String detail = String.format("A propriedade '%s' recebeu o valor '%s', que é um tipo inválido. Corrija" +
                " e informe um valor compatível com o tipo %s.", property, ex.getValue(), ex.getTargetType()
                .getSimpleName());
        Problem problem = createProblemBuilder(ProblemType.FALHA_AO_LER_REQUISICAO, status, detail).build();

        return handleExceptionInternal(ex, problem, headers, status, req);
    }

    private ResponseEntity<Object> handleIgnoredPropertyException(IgnoredPropertyException ex, HttpHeaders headers,
                                                                  HttpStatusCode status, WebRequest req) {

        String detail = String.format("A propriedade '%s', no momento, não é aceita pela entidade %s.",
                getProperty(ex), getEntity(ex));
        Problem problem = createProblemBuilder(ProblemType.PROPRIEDADE_IGNORADA, status, detail).build();

        return handleExceptionInternal(ex, problem, headers, status, req);
    }

    private ResponseEntity<Object> handleUnrecognizedPropertyException(UnrecognizedPropertyException ex,
                                                                       HttpHeaders headers, HttpStatusCode status,
                                                                       WebRequest req) {

        String detail = String.format("A propriedade '%s' não existe na entidade %s.", getProperty(ex), getEntity(ex));
        Problem problem = createProblemBuilder(ProblemType.PROPRIEDADE_DESCONHECIDA, status, detail).build();

        return handleExceptionInternal(ex, problem, headers, status, req);
    }


}
