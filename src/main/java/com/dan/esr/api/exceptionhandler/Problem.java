package com.dan.esr.api.exceptionhandler;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonMappingException.Reference;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import lombok.Builder;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatusCode;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@JsonInclude(Include.NON_NULL)
public final class Problem {

    private Integer status;
    private String type;
    private String title;
    private String detail;
    private String userMessage;


    /* Métodos */
    public static Problem novoProblema(Exception ex, HttpStatusCode status) {
        return Problem.builder()
                .title(ex.getMessage())
                .status(status.value())
                .build();
    }

    public static Problem novoProblema(String msg, HttpStatusCode status) {
        return Problem.builder()
                .title(msg)
                .status(status.value())
                .build();
    }

    public static Problem.ProblemBuilder createProblemBuilder(ProblemType problemType, HttpStatusCode status, String detail) {
        return Problem.builder()
                .status(status.value())
                .type(problemType.getUri())
                .title(problemType.getTitle())
                .detail(detail);
    }


}
