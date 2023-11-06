package com.dan.esr.api.models.mixin;

import com.dan.esr.domain.entities.Restaurante;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import java.util.ArrayList;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@JsonRootName(value = "cozinha")
@JsonInclude(NON_NULL)
public class CozinhaMixin {

    @JsonProperty("tipo")
    private String nome;

    @JsonIgnore
    private List<Restaurante> restaurantes = new ArrayList<>();
}
