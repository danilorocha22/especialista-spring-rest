package com.dan.esr.api.exceptionhandler;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel("Problema")
@JsonInclude(NON_NULL)
public final class Problem {
    //Propriedades padrão da especificação RFC 7807
    @ApiModelProperty(example = "400", position = 1)
    private Integer status;

    @ApiModelProperty(example = "https://danfood.com.br/erro-negocio", position = 10)
    private String type;

    @ApiModelProperty(example = "Violação de regra de negócio", position = 20)
    private String title;

    @ApiModelProperty(example = "Não existe um cadastro de estado com codigo 1", position = 30)
    private String detail;

    //Propriedades adicionais
    @ApiModelProperty(example = "Não existe um cadastro de estado com codigo 1", position = 35)
    private String userMessage;

    @ApiModelProperty(example = "2021-11-14T00:12:13", position = 40)
    private OffsetDateTime timestamp;

    @ApiModelProperty(value = "Lista de objetos ou campos que geraram o erro", position = 50)
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
    @ApiModel("ObjetoProblema")
    public static class Object {

        @ApiModelProperty(example = "preco")
        private String nome;

        @ApiModelProperty(example = "O preço é obrigatório")
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