package com.dan.esr.api.models.output.estado;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel(value = "Estado")
public class EstadoOutput {
    @ApiModelProperty(example = "TO")
    private String sigla;
}