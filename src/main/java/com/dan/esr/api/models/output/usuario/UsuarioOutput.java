package com.dan.esr.api.models.output.usuario;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioOutput {
    private Long id;
    private String nome;
    private String email;
}