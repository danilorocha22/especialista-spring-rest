package com.dan.esr.api.models.output;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CidadeEstadoOutput extends CidadeOutput {
    private Long id;
    private String nome;
    private EstadoOutput estado;
}