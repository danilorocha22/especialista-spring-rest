package com.dan.esr.api.models.input.cidade;

import com.dan.esr.api.models.input.estado.EstadoIdInput;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CidadeIdInput {
    @NotNull private Long id;
    private EstadoIdInput estado;
}