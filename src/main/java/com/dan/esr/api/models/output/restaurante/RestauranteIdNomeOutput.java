package com.dan.esr.api.models.output.restaurante;

import com.dan.esr.api.models.output.view.PedidoView;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonView(PedidoView.Resumo.class)
public class RestauranteIdNomeOutput {
    private Long id;
    private String nome;
}