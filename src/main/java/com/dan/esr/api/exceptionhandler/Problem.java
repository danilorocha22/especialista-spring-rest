package com.dan.esr.api.exceptionhandler;

import com.dan.esr.domain.exceptions.NegocioException;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import lombok.Builder;
import lombok.Getter;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatusCode;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.dan.esr.api.exceptionhandler.ProblemType.FALHA_AO_LER_REQUISICAO;

@Getter
@Builder
@JsonInclude(Include.NON_NULL)
public final class Problem {

    private Integer status;
    private String type;
    private String title;
    private String detail;


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

    public static String getProperty(InvalidFormatException ex) {
        List<String> propriedades = new ArrayList<>();
        StringBuilder propriedade = new StringBuilder();

        for (JsonMappingException.Reference reference : ex.getPath()) {
            propriedades.add(reference.getFieldName());
        }

        int i = 0;

        while (propriedades.size() > i) {
            propriedade.append(propriedades.get(i)).append(".");
            i++;
        }

        return StringUtils.removeEnd(propriedade.toString(), ".");
    }

    public static Optional<String> getPropertyType(InvalidFormatException ex) throws NoSuchFieldException {
        if (Objects.nonNull(ex)) {
            int idx = ex.getPath().size() - 1;
            Class<?> targetClass = ex.getPath().get(idx).getFrom().getClass();
            String propertyName = ex.getPath().get(idx).getFieldName();

                Field field = targetClass.getDeclaredField(propertyName);
                Class<?> propertyType = field.getType();
                return Optional.of(ClassUtils.getShortClassName(propertyType.getName()));

        }

        return Optional.empty();

    }


}
