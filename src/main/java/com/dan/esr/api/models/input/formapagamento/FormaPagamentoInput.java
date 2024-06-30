package com.dan.esr.api.models.input.formapagamento;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FormaPagamentoInput {

    @ApiModelProperty(example = "Cartão de Crédito", required = true)
    private String nome;
}