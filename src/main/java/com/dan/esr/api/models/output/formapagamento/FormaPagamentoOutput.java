package com.dan.esr.api.models.output.formapagamento;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FormaPagamentoOutput {

    @ApiModelProperty(example = "1")
    private Long id;

    @ApiModelProperty(example = "Débito")
    private String nome;
}