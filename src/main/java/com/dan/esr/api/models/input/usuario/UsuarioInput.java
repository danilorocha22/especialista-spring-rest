package com.dan.esr.api.models.input.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UsuarioInput {
    @NotBlank private String nome;
    @NotBlank @Email private String email;
    @NotBlank private String senha;
}