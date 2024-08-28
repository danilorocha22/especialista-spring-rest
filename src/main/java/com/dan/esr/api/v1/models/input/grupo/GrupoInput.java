package com.dan.esr.api.v1.models.input.grupo;

import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GrupoInput {
    @NotBlank
    @ApiModelProperty(example = "Vendedor", required = true)
    private String nome;
}