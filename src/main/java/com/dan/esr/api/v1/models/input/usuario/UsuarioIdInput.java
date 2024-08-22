package com.dan.esr.api.v1.models.input.usuario;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioIdInput {

    @ApiModelProperty(example = "1", required = true)
    private Long id;
}