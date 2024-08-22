package com.dan.esr.api.v1.models.input.usuario;

import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioInput {
    @NotBlank
    @ApiModelProperty(example = "Paulo Gomes")
    private String nome;

    @Email
    @NotBlank
    @ApiModelProperty(example = "paulo.gomes@email.com")
    private String email;

    @NotBlank
    @ApiModelProperty(value = "Deve conter números e letras")
    private String senha;
}