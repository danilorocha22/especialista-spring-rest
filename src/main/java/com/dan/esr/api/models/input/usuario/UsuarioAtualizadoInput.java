package com.dan.esr.api.models.input.usuario;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UsuarioAtualizadoInput {
    @NotBlank private String nome;
    @NotBlank private String email;
}