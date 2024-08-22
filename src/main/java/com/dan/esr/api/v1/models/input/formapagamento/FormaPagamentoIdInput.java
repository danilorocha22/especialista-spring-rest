package com.dan.esr.api.v1.models.input.formapagamento;

import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FormaPagamentoIdInput {
    @NotNull
    @ApiModelProperty(example = "1", required = true)
    private Long id;
}