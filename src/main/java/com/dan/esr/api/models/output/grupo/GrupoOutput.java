package com.dan.esr.api.models.output.grupo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("Grupo")
public class GrupoOutput {

    @ApiModelProperty(example = "1")
    private Long id;

    @ApiModelProperty(example = "Vendedor")
    private String nome;
}