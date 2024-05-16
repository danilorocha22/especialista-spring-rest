package com.dan.esr.api.models.input;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CidadeInput {
    private String nome;
    @JsonIgnoreProperties(value = "sigla", allowGetters = true)
    private EstadoInput estado;
}