package com.dan.esr.api.models.output.restaurante;

import com.dan.esr.api.models.output.usuario.UsuarioOutput;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RestauranteResponsaveisOutput {
    @JsonProperty("restaurante")
    private String nome;

    @JsonProperty("responsaveis")
    private List<UsuarioOutput> usuarios;
}