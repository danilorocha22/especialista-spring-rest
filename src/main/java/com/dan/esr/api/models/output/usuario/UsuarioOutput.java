package com.dan.esr.api.models.output.usuario;

import com.dan.esr.api.models.output.view.PedidoView;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonView(PedidoView.Resumo.class)
public class UsuarioOutput {
    private Long id;
    private String nome;
    private String email;
}