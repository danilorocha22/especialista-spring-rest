package com.dan.esr.api.exceptionhandler;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatusCode;
import org.springframework.validation.BindException;

import java.time.LocalDateTime;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Getter
@Builder
@JsonInclude(NON_NULL)
public final class Problem {

    private Integer status;
    private String type;
    private String title;
    private String detail;

    private String userMessage;
    private LocalDateTime timestamp;
    private List<Field> fields;


    /* Métodos da classe Problem */
    public static Problem novoProblema(Exception ex, HttpStatusCode status) {
        return Problem.builder()
                .title(ex.getMessage())
                .status(status.value())
                .timestamp(LocalDateTime.now())
                .build();
    }

    public static Problem novoProblema(String msg, HttpStatusCode status) {
        return Problem.builder()
                .title(msg)
                .status(status.value())
                .timestamp(LocalDateTime.now())
                .build();
    }

    public static ProblemBuilder createProblemBuilder(ProblemType problemType, HttpStatusCode status,
                                                      String detail) {
        return Problem.builder()
                .status(status.value())
                .type(problemType.getUri())
                .title(problemType.getTitle())
                .detail(detail)
                .timestamp(LocalDateTime.now());
    }


    @Getter
    @Builder
    public static class Field {
        private String nome;
        private String userMessage;

        /* Métodos da classe Field*/
        public static List<Field> getProblemFields(BindException ex, MessageSource messageSource) {
            return ex.getFieldErrors().stream()
                    .map(fieldError -> {
                        String msg = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());
                        return Field.builder()
                                .nome(fieldError.getField())
                                .userMessage(msg)
                                .build();
                    }).toList();
        }
    }


}
