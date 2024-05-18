package com.dan.esr.api.models.output;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestauranteStatusOutput {
    private Long id;
    private String nome;
    private boolean ativo;
}
