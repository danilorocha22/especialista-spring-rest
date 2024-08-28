package com.dan.esr.api.v1.models.input.usuario;

import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioSenhaInput {
    @NotBlank
    @ApiModelProperty(value = "Deve conter números e letras")
    private String senha;

    @NotBlank
    @ApiModelProperty(value = "Deve conter números e letras")
    private String novaSenha;
}