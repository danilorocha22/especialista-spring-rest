package com.dan.esr.api.models.input.estado;

import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EstadoIdInput {
    @NotNull
    @ApiModelProperty(example = "1", required = true)
    private Long id;
}