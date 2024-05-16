package com.dan.esr.api.models.output;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CidadeNomeOutput extends CidadeOutput {
    private Long id;
    private String nome;
}