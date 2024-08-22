package com.dan.esr.api.v1.models.input.permissao;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PermissaoInput {
    @NotBlank private String nome;
    @NotBlank private String descricao;
}
