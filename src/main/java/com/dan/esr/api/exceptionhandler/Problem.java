package com.dan.esr.api.exceptionhandler;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatusCode;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.time.OffsetDateTime;
import java.util.List;

import static com.dan.esr.core.util.MensagensUtil.formatarNomeEntidadeInput;
import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Getter
@Builder
@JsonInclude(NON_NULL)
public final class Problem {
    //Propriedades padrão da especificação RFC 7807
    private Integer status;
    private String type;
    private String title;
    private String detail;

    //Propriedades adicionais
    private String userMessage;
    private OffsetDateTime timestamp;
    private List<Object> objects;


    /* Métodos da classe Problem */
    public static Problem novoProblema(String msg, HttpStatusCode status) {
        return Problem.builder()
                .title(msg)
                .status(status.value())
                .timestamp(OffsetDateTime.now())
                .build();
    }

    public static ProblemBuilder createProblemBuilder(
            ProblemType problemType,
            HttpStatusCode status,
            String detail
    ) {
        return Problem.builder()
                .status(status.value())
                .type(problemType.getUri())
                .title(problemType.getTitle())
                .detail(detail)
                .timestamp(OffsetDateTime.now());
    }

    @Getter
    @Builder
    public static class Object {
        private String nome;
        private String userMessage;

        /* Métodos da classe Object*/
        public static List<Object> getProblemObjects(
                BindingResult bindingResult,
                MessageSource messageSource
        ) {
            return bindingResult.getAllErrors().stream()
                    .map(objectError -> {
                        String msg = messageSource.getMessage(objectError, LocaleContextHolder.getLocale());
                        String name = StringUtils.capitalize(objectError.getObjectName());
                        name = formatarNomeEntidadeInput(name);

                        if (objectError instanceof FieldError) {
                            name = ((FieldError) objectError).getField();
                        }

                        return Object.builder()
                                .nome(name)
                                .userMessage(msg)
                                .build();
                    }).toList();
        }
    }
}