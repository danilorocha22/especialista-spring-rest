package com.dan.esr.api.v1.models.input.cidade;

import com.dan.esr.api.v1.models.input.estado.EstadoIdInput;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CidadeInput {
    @NotBlank
    @ApiModelProperty(example = "Palmas", required = true)
    private String nome;

    //@JsonIgnoreProperties(value = "sigla", allowGetters = true)
    @NotNull
    private EstadoIdInput estado;
}