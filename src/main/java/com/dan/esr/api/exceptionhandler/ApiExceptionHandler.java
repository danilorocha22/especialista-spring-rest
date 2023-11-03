package com.dan.esr.api.exceptionhandler;

import com.dan.esr.domain.exceptions.EntidadeEmUsoException;
import com.dan.esr.domain.exceptions.EntidadeNaoEncontradaException;
import com.dan.esr.domain.exceptions.NegocioException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.IgnoredPropertyException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import lombok.NonNull;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Objects;
import java.util.stream.Collectors;

import static com.dan.esr.api.exceptionhandler.Problem.createProblemBuilder;
import static com.dan.esr.api.exceptionhandler.Problem.novoProblema;
import static com.dan.esr.api.exceptionhandler.ProblemType.*;
import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    public static final String MSG_ERRO_GENERICA_USUARIO_FINAL = "Erro interno inesperado no sistema. Tente novamente" +
            " mais tarde e se o problema persistir, entre em contato com o Administrador do sistema.";

    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    public ResponseEntity<Object> handleEntidadeNaoEncontradaException(EntidadeNaoEncontradaException ex,
                                                                       WebRequest req) {
        HttpStatusCode status = NOT_FOUND;
        Problem problem = createProblemBuilder(RECURSO_NAO_ENCONTRADO, status, ex.getMessage()).build();

        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, req);
    }


    @ExceptionHandler(NegocioException.class)
    public ResponseEntity<Object> handleNegocioException(NegocioException ex, WebRequest req) {
        HttpStatusCode status = BAD_REQUEST;
        Problem problem = createProblemBuilder(ERRO_NA_REQUISICAO, status, ex.getMessage()).build();

        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, req);
    }


    @ExceptionHandler(EntidadeEmUsoException.class)
    public ResponseEntity<Object> handleEntidadeEmUsoException(EntidadeEmUsoException ex, WebRequest req) {
        HttpStatusCode status = CONFLICT;
        Problem problem = createProblemBuilder(ENTIDADE_EM_USO, status, ex.getMessage()).build();

        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, req);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handlerException(Exception ex, WebRequest req) {
        HttpStatusCode status = INTERNAL_SERVER_ERROR;
        Problem problem = createProblemBuilder(ERRO_INTERNO_DO_SISTEMA, status, MSG_ERRO_GENERICA_USUARIO_FINAL).build();

        // Importante colocar o printStackTrace (pelo menos por enquanto, que não estamos
        // fazendo logging) para mostrar a stacktrace no console
        // Se não fizer isso, você não vai ver a stacktrace de exceptions que seriam importantes
        // para você durante, especialmente na fase de desenvolvimento
        ex.printStackTrace();

        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, req);
    }


    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(@NonNull HttpMessageNotReadableException ex,
                                                                  @NonNull HttpHeaders headers,
                                                                  @NonNull HttpStatusCode status,
                                                                  @NonNull WebRequest req) {
        Throwable cause = ExceptionUtils.getRootCause(ex);

        return switch (cause.getClass().getSimpleName()) {
            case ("InvalidFormatException") ->
                    handleInvalidFormatException((InvalidFormatException) cause, headers, status, req);

            case ("IgnoredPropertyException") ->
                    handleIgnoredPropertyException((IgnoredPropertyException) cause, headers, status, req);

            case ("UnrecognizedPropertyException") ->
                    handleUnrecognizedPropertyException((UnrecognizedPropertyException) cause, headers, status, req);

            default -> super.handleHttpMessageNotReadable(ex, headers, status, req);
        };
    }


    @Override
    protected ResponseEntity<Object> handleTypeMismatch(@NonNull TypeMismatchException ex, @NonNull HttpHeaders headers,
                                                        @NonNull HttpStatusCode status, @NonNull WebRequest req) {

        if (ex instanceof MethodArgumentTypeMismatchException) {
            return handleMethodArgumentTypeMismatch(ex, headers, status, req);
        }

        return super.handleTypeMismatch(ex, headers, status, req);
    }


    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(@NonNull NoHandlerFoundException ex, @NonNull HttpHeaders headers,
                                                                   @NonNull HttpStatusCode status, @NonNull WebRequest req) {

        String detail = String.format("O recurso solicitado '%s', não existe", ex.getRequestURL());
        Problem problem = createProblemBuilder(RECURSO_NAO_ENCONTRADO, status, detail).build();

        return handleExceptionInternal(ex, problem, headers, status, req);
    }


    @Override
    protected ResponseEntity<Object> handleExceptionInternal(@NonNull Exception ex, Object body, @NonNull HttpHeaders headers,
                                                             @NonNull HttpStatusCode status, @NonNull WebRequest req) {
        if (body == null) {
            body = novoProblema(ex, status);
        } else if (body instanceof String) {
            body = novoProblema((String) body, status);
        }

        return super.handleExceptionInternal(ex, body, headers, status, req);
    }


    private ResponseEntity<Object> handleMethodArgumentTypeMismatch(TypeMismatchException ex, HttpHeaders headers,
                                                                    HttpStatusCode status, WebRequest req) {
        String detail = String.format(" O parâmetro '%s' com valor '%s', é inválido. Requer um tipo %s.",
                ex.getPropertyName(), ex.getValue(), Objects.requireNonNull(ex.getRequiredType()).getSimpleName());

        Problem problem = createProblemBuilder(PARAMETRO_INVALIDO, status, detail).build();

        return handleExceptionInternal(ex, problem, headers, status, req);
    }


    private ResponseEntity<Object> handleInvalidFormatException(InvalidFormatException ex, HttpHeaders headers,
                                                                HttpStatusCode status, WebRequest req) {

        String detail = String.format("A propriedade '%s' recebeu o valor '%s', que é um tipo inválido. Corrija" +
                " e informe um valor compatível com o tipo %s.", getProperty(ex), ex.getValue(), ex.getTargetType()
                .getSimpleName());

        Problem problem = createProblemBuilder(FALHA_AO_LER_REQUISICAO, status, detail)
                .userMessage(MSG_ERRO_GENERICA_USUARIO_FINAL)
                .build();

        return handleExceptionInternal(ex, problem, headers, status, req);
    }


    private ResponseEntity<Object> handleIgnoredPropertyException(IgnoredPropertyException ex, HttpHeaders headers,
                                                                  HttpStatusCode status, WebRequest req) {

        String detail = String.format("A propriedade '%s', no momento, não é aceita pela entidade %s.",
                getProperty(ex), getEntity(ex));

        Problem problem = createProblemBuilder(PROPRIEDADE_IGNORADA, status, detail).build();

        return handleExceptionInternal(ex, problem, headers, status, req);
    }


    private ResponseEntity<Object> handleUnrecognizedPropertyException(UnrecognizedPropertyException ex,
                                                                       HttpHeaders headers, HttpStatusCode status,
                                                                       WebRequest req) {

        String detail = String.format("A propriedade '%s' não existe na entidade %s.", getProperty(ex), getEntity(ex));

        Problem problem = createProblemBuilder(PROPRIEDADE_DESCONHECIDA, status, detail)
                .build();

        return handleExceptionInternal(ex, problem, headers, status, req);
    }

    private static String getProperty(JsonMappingException ex) {
        return ex.getPath().stream()
                .map(JsonMappingException.Reference::getFieldName)
                .collect(Collectors.joining("."));
    }

    private static String getEntity(JsonMappingException ex) {
        return ex.getPath().get(0).getFrom().getClass().getSimpleName();
    }


}
