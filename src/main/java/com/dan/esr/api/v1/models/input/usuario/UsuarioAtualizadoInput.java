package com.dan.esr.api.v1.models.input.usuario;

import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioAtualizadoInput {
    @NotBlank
    @ApiModelProperty(example = "Paulo Gomes")
    private String nome;

    @NotBlank
    @ApiModelProperty(example = "paulo.gomes@email.com")
    private String email;
}