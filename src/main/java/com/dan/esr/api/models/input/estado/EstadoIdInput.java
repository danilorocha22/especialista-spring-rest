package com.dan.esr.api.models.input.estado;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class EstadoIdInput {
    @NotNull private Long id;
}