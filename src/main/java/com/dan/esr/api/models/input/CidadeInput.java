package com.dan.esr.api.models.input;

import com.dan.esr.domain.entities.Estado;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CidadeInput {
    private String nome;
    private Estado estado;
}