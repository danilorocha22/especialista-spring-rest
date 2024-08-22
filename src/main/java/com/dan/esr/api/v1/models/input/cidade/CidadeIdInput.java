package com.dan.esr.api.v1.models.input.cidade;

import com.dan.esr.api.v1.models.input.estado.EstadoIdInput;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CidadeIdInput {

    @NotNull
    @ApiModelProperty(example = "1", required = true)
    private Long id;

    @Valid
    @NotNull
    private EstadoIdInput estado;
}