package com.dan.esr.api.v1.models.input.estado;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EstadoInput {
    //private Long id;
    @ApiModelProperty(example = "TO")
    private String sigla;
}
