package com.dan.esr.api.models.input.grupo;

import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GrupoInput {
    @NotBlank
    @ApiModelProperty(example = "Vendedor", required = true)
    private String nome;
}