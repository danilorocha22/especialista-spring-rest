package com.dan.esr.api.models.input.cidade;

import com.dan.esr.api.models.input.estado.EstadoIdInput;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CidadeInput {
    private String nome;
    @JsonIgnoreProperties(value = "sigla", allowGetters = true)
    private EstadoIdInput estado;
}