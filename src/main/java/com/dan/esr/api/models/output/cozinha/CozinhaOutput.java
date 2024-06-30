package com.dan.esr.api.models.output.cozinha;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CozinhaOutput {

    @ApiModelProperty(example = "1")
    private Long id;

    @ApiModelProperty(example = "Brasileira")
    private String nome;
}