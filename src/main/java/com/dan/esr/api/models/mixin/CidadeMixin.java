package com.dan.esr.api.models.mixin;

import com.dan.esr.domain.entities.Estado;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public class CidadeMixin {

    @JsonIgnoreProperties(value = "sigla", allowGetters = true)
    private Estado estado;
}
