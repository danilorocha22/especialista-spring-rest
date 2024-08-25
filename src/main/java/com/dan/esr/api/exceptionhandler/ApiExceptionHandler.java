package com.dan.esr.api.exceptionhandler;

import com.ctc.wstx.exc.WstxUnexpectedCharException;
import com.dan.esr.domain.exceptions.*;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.IgnoredPropertyException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.TypeMismatchException;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Objects;

import static com.dan.esr.api.exceptionhandler.Problem.Object.getProblemObjects;
import static com.dan.esr.api.exceptionhandler.Problem.createProblemBuilder;
import static com.dan.esr.api.exceptionhandler.Problem.novoProblema;
import static com.dan.esr.api.exceptionhandler.ProblemType.*;
import static com.dan.esr.core.util.MensagensUtil.*;
import static java.util.stream.Collectors.joining;
import static org.springframework.http.HttpStatus.*;

@Slf4j
@AllArgsConstructor
@RestControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {
    private MessageSource messageSource;

    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    public ResponseEntity<Object> handleEntidadeNaoEncontradaException(
            EntidadeNaoEncontradaException ex,
            WebRequest req
    ) {
        HttpStatusCode status = NOT_FOUND;
        Problem problem = createProblemBuilder(RECURSO_NAO_ENCONTRADO, status, ex.getMessage())
                .userMessage(ex.getMessage())
                .build();

        log.error("handleEntidadeNaoEncontradaException() -> Erro: {}", ex.getLocalizedMessage(), ex);
        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, req);
    }

    @ExceptionHandler(EntidadeNaoPersistidaException.class)
    public ResponseEntity<Object> handleEntidadeNaoPersistidaException(
            EntidadeNaoEncontradaException ex,
            WebRequest req
    ) {
        HttpStatusCode status = UNPROCESSABLE_ENTITY;
        Problem problem = createProblemBuilder(PROPRIEDADE_INVALIDA, status, ex.getMessage())
                .userMessage(ex.getMessage())
                .build();

        log.error("handleEntidadeNaoPersistidaException() -> Erro: {}", ex.getLocalizedMessage(), ex);
        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, req);
    }

    @ExceptionHandler(EntidadeEmUsoException.class)
    public ResponseEntity<Object> handleEntidadeEmUsoException(
            EntidadeEmUsoException ex,
            WebRequest req
    ) {
        HttpStatusCode status = CONFLICT;
        Problem problem = createProblemBuilder(ENTIDADE_EM_USO, status, ex.getMessage())
                .userMessage(ex.getMessage())
                .build();

        log.error("handleEntidadeEmUsoException() -> Erro: {}", ex.getLocalizedMessage(), ex);
        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, req);
    }

    @ExceptionHandler(ValidacaoException.class)
    public ResponseEntity<Object> handleValidacaoException(
            ValidacaoException ex,
            WebRequest req
    ) {
        /*HttpStatusCode status = BAD_REQUEST;
        Problem problem = createProblemBuilder(PROPRIEDADE_INVALIDA, status, MSG_PROPRIEDADE_INVALIDA)
                .userMessage(MSG_PROPRIEDADE_INVALIDA)
                .objects(getProblemObjects(ex.getBindingResult().getAllErrors(), this.messageSource))
                .build();*/

        log.error("handleValidacaoException() -> Erro: {}", ex.getLocalizedMessage(), ex);
        return handleValidationInternal(ex, new HttpHeaders(), BAD_REQUEST, req, ex.getBindingResult());
    }

    @ExceptionHandler(PropriedadeIlegalException.class)
    public ResponseEntity<Object> handlePropriedadeIlegalException(
            PropriedadeIlegalException ex,
            WebRequest req
    ) {
        HttpStatusCode status = BAD_REQUEST;
        Problem problem = createProblemBuilder(PROPRIEDADE_INVALIDA, status, ex.getMessage())
                .userMessage(ex.getMessage())
                .build();

        log.error("handlePropriedadeIlegalException() -> Erro: {}", ex.getLocalizedMessage(), ex);
        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, req);
    }

    @ExceptionHandler(EntidadeExistenteException.class)
    public ResponseEntity<Object> handleEntidadeExistenteException(
            EntidadeExistenteException ex,
            WebRequest req
    ) {
        HttpStatusCode status = CONFLICT;
        Problem problem = createProblemBuilder(RECURSO_EXISTENTE, status, ex.getMessage())
                .userMessage(ex.getMessage())
                .build();

        log.error("handleEntidadeExistenteException() -> Erro: {}", ex.getLocalizedMessage(), ex);
        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, req);
    }

    @ExceptionHandler(PersistenciaException.class)
    public ResponseEntity<Object> handlePersistenciaException(
            PersistenciaException ex,
            WebRequest req
    ) {
        HttpStatusCode status = INTERNAL_SERVER_ERROR;
        Problem problem = createProblemBuilder(ERRO_INTERNO_DO_SISTEMA, status, MSG_ERRO_BANCO_DE_DADOS)
                .userMessage(ex.getMessage())
                .build();

        log.error("handlePersistenciaException() -> Erro: {}", ex.getLocalizedMessage(), ex);
        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, req);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgumentException(
            IllegalArgumentException ex,
            WebRequest req
    ) {
        String detail = "O argumento informado é inválido.";
        HttpStatusCode status = BAD_REQUEST;
        Problem problem = createProblemBuilder(PROPRIEDADE_INVALIDA, status, detail)
                .userMessage(detail)
                .build();

        log.error("handleIllegalArgumentException() -> Erro: {}", ex.getLocalizedMessage(), ex);
        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, req);
    }

    @ExceptionHandler(NegocioException.class)
    public ResponseEntity<Object> handleNegocioException(NegocioException ex, WebRequest req) {
        HttpStatusCode status = BAD_REQUEST;
        Problem problem = createProblemBuilder(ERRO_NA_REQUISICAO, status, ex.getMessage())
                .userMessage(ex.getMessage())
                .build();

        log.error("handleNegocioException() -> Erro: {}", ex.getLocalizedMessage(), ex);
        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, req);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handlerException(Exception ex, WebRequest req) {
        HttpStatusCode status = INTERNAL_SERVER_ERROR;
        Problem problem = createProblemBuilder(ERRO_INTERNO_DO_SISTEMA, status, ex.getMessage())
                .userMessage(MSG_ERRO_GENERICO_SERVIDOR)
                .build();

        log.error("handlerException() -> Erro {}", ex.getLocalizedMessage(), ex);
        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, req);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(
            @NonNull HttpMediaTypeNotAcceptableException ex,
            @NonNull HttpHeaders headers,
            @NonNull HttpStatusCode status,
            @NonNull WebRequest request
    ) {
        return ResponseEntity.status(status).headers(headers).build();
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            @NonNull MethodArgumentNotValidException ex,
            @NonNull HttpHeaders headers,
            @NonNull HttpStatusCode status,
            @NonNull WebRequest req
    ) {
        /*Problem problem = createProblemBuilder(PROPRIEDADE_INVALIDA, status, MSG_PROPRIEDADE_INVALIDA)
                .userMessage(MSG_PROPRIEDADE_INVALIDA)
                .objects(getProblemObjects(ex.getAllErrors(), this.messageSource))
                .build();*/

        log.error("handleMethodArgumentNotValid() -> Erro: {}", ex.getLocalizedMessage(), ex);
        return handleValidationInternal(ex, headers, status, req, ex.getBindingResult());
    }

    @Override
    protected ResponseEntity<Object> handleServletRequestBindingException(
            @NonNull ServletRequestBindingException ex,
            @NonNull HttpHeaders headers,
            @NonNull HttpStatusCode status,
            @NonNull WebRequest req
    ) {
        log.error("handleServletRequestBindingException() -> Erro: {}", ex.getLocalizedMessage(), ex);
        return handleExceptionInternal(ex, ex.getBody(), headers, status, req);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(
            @NonNull NoHandlerFoundException ex,
            @NonNull HttpHeaders headers,
            @NonNull HttpStatusCode status,
            @NonNull WebRequest req
    ) {
        String detail = "O recurso solicitado '%s', não existe".formatted(ex.getRequestURL());
        Problem problem = createProblemBuilder(RECURSO_NAO_ENCONTRADO, status, detail)
                .userMessage(detail)
                .build();

        log.error("handleNoHandlerFoundException() -> Erro: {}", ex.getLocalizedMessage(), ex);
        return handleExceptionInternal(ex, problem, headers, status, req);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(
            @NonNull TypeMismatchException ex,
            @NonNull HttpHeaders headers,
            @NonNull HttpStatusCode status,
            @NonNull WebRequest req
    ) {
        if (ex instanceof MethodArgumentTypeMismatchException) {
            return handleMethodArgumentTypeMismatch(ex, headers, status, req);
        }

        log.error("handleTypeMismatch() -> Erro: {}", ex.getLocalizedMessage(), ex);
        return super.handleTypeMismatch(ex, headers, status, req);
    }

    private ResponseEntity<Object> handleMethodArgumentTypeMismatch(
            TypeMismatchException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest req
    ) {
        String nomeEntidade = Objects.requireNonNull(ex.getRequiredType()).getSimpleName();
        String detail = "O parâmetro '%s' com valor '%s', é inválido. Requer um tipo %s.".formatted(
                ex.getPropertyName(), ex.getValue(), formatarNomeEntidadeInput(nomeEntidade));

        Problem problem = createProblemBuilder(PARAMETRO_INVALIDO, status, detail)
                .userMessage(MSG_ERRO_GENERICO_CLIENTE)
                .build();

        log.error("handleMethodArgumentTypeMismatch() -> Erro: {}", ex.getLocalizedMessage(), ex);
        return handleExceptionInternal(ex, problem, headers, status, req);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            @NonNull HttpMessageNotReadableException ex,
            @NonNull HttpHeaders headers,
            @NonNull HttpStatusCode status,
            @NonNull WebRequest req
    ) {
        Throwable cause = ExceptionUtils.getRootCause(ex);
        String exceptionName = cause.getClass().getSimpleName();

        return switch (exceptionName) {
            case ("InvalidFormatException") ->
                    handleInvalidFormatException((InvalidFormatException) cause, headers, status, req);

            case ("IgnoredPropertyException") ->
                    handleIgnoredPropertyException((IgnoredPropertyException) cause, headers, status, req);

            case ("UnrecognizedPropertyException") ->
                    handleUnrecognizedPropertyException((UnrecognizedPropertyException) cause, headers, status, req);

            case ("WstxUnexpectedCharException") ->
                    handleWstxUnexpectedCharException((WstxUnexpectedCharException) cause, headers, status, req);

            case ("JsonParseException") -> handleJsonParseException((JsonParseException) cause, headers, status, req);

            case ("MismatchedInputException") ->
                    handleMismatchedInputException((MismatchedInputException) cause, headers, status, req);

            default -> super.handleHttpMessageNotReadable(ex, headers, status, req);
        };
    }

    private ResponseEntity<Object> handleInvalidFormatException(
            InvalidFormatException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest req
    ) {
        String detail = "A propriedade '%s' recebeu o valor '%s', que é um tipo inválido. "
                .concat("Corrija e informe um valor compatível com o tipo %s.")
                .formatted(getPropertyName(ex), ex.getValue(), ex.getTargetType().getSimpleName());

        Problem problem = createProblemBuilder(FALHA_AO_LER_REQUISICAO, status, detail)
                .userMessage(MSG_ERRO_GENERICO_CLIENTE)
                .build();

        log.error("handleInvalidFormatException() -> Erro: {}", ex.getLocalizedMessage(), ex);
        return handleExceptionInternal(ex, problem, headers, status, req);
    }

    private ResponseEntity<Object> handleIgnoredPropertyException(
            IgnoredPropertyException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest req
    ) {
        String nomeEntidade = getEntityName(ex);
        String detail = "A propriedade '%s', no momento, não é aceita pela entidade %s.".formatted(
                getPropertyName(ex), formatarNomeEntidadeInput(nomeEntidade));
        Problem problem = createProblemBuilder(PROPRIEDADE_IGNORADA, status, detail)
                .userMessage(detail)
                .build();

        log.error("handleIgnoredPropertyException() -> Erro: {}", ex.getLocalizedMessage(), ex);
        return handleExceptionInternal(ex, problem, headers, status, req);
    }

    private ResponseEntity<Object> handleUnrecognizedPropertyException(
            UnrecognizedPropertyException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest req
    ) {
        String detail = "A propriedade '%s' não existe na entidade %s."
                .formatted(getPropertyName(ex), getEntityName(ex));
        Problem problem = createProblemBuilder(PROPRIEDADE_DESCONHECIDA, status, detail)
                .userMessage(detail)
                .build();

        log.error("handleUnrecognizedPropertyException() -> Erro: {}", ex.getLocalizedMessage(), ex);
        return handleExceptionInternal(ex, problem, headers, status, req);
    }

    private ResponseEntity<Object> handleWstxUnexpectedCharException(
            WstxUnexpectedCharException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest req
    ) {
        Problem problem = createProblemBuilder(ERRO_NA_REQUISICAO, status, ex.getLocalizedMessage())
                .userMessage("Caractere inesperado: %s.".formatted(ex.getChar()))
                .build();

        log.error("handleWstxUnexpectedCharException() -> Erro: {}", ex.getLocalizedMessage(), ex);
        return handleExceptionInternal(ex, problem, headers, status, req);
    }

    private ResponseEntity<Object> handleJsonParseException(
            JsonParseException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest req
    ) {
        String detail = "Erro na desserialização do JSON. Possível erro de sintaxe: %s."
                .formatted(ex.getOriginalMessage());

        Problem problem = createProblemBuilder(ERRO_NA_REQUISICAO, status, detail)
                .userMessage(MSG_ERRO_GENERICO_CLIENTE)
                .build();

        log.error("handleJsonParseException() -> Erro: {}", detail, ex);
        return handleExceptionInternal(ex, problem, headers, status, req);
    }

    private ResponseEntity<Object> handleMismatchedInputException(
            MismatchedInputException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest req
    ) {
        String detail = "Erro ao tentar desserializar os dados JSON em um objeto Java, " +
                "devido à incompatibilidade nos tipos de dados ou na estrutura esperada: %s -> %s."
                        .formatted(getEntityName(ex), getPropertyName(ex));

        Problem problem = createProblemBuilder(ERRO_NA_REQUISICAO, status, detail)
                .userMessage(MSG_ERRO_GENERICO_CLIENTE)
                .build();

        log.error("handleMismatchedInputException() -> Erro: {}", ex.getLocalizedMessage(), ex);
        return handleExceptionInternal(ex, problem, headers, status, req);
    }

    private ResponseEntity<Object> handleValidationInternal(
            @NonNull Exception ex,
            @NonNull HttpHeaders headers,
            @NonNull HttpStatusCode status,
            @NonNull WebRequest req,
            @NonNull BindingResult bindingResult
    ) {
        Problem problem = createProblemBuilder(PROPRIEDADE_INVALIDA, status, MSG_PROPRIEDADE_INVALIDA)
                .userMessage(MSG_PROPRIEDADE_INVALIDA)
                .objects(getProblemObjects(bindingResult, this.messageSource))
                .build();
        return handleExceptionInternal(ex, problem, headers, status, req);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(
            @NonNull Exception ex,
            Object body,
            @NonNull HttpHeaders headers,
            @NonNull HttpStatusCode status,
            @NonNull WebRequest req
    ) {
        if (body == null) {
            body = novoProblema(ex.getMessage(), status);
        } else if (body instanceof String) {
            body = novoProblema((String) body, status);
        }

        return super.handleExceptionInternal(ex, body, headers, status, req);
    }

    private static String getPropertyName(JsonMappingException ex) {
        return ex.getPath().stream()
                .map(JsonMappingException.Reference::getFieldName)
                .collect(joining("."));
    }

    private static String getEntityName(JsonMappingException ex) {
        return formatarNomeEntidadeInput(ex.getPath().get(0).getFrom().getClass().getSimpleName());
    }
}