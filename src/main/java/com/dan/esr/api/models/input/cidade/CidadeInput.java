package com.dan.esr.api.models.input.cidade;

import com.dan.esr.api.models.input.estado.EstadoIdInput;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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

    @NotNull
    @JsonIgnoreProperties(value = "sigla", allowGetters = true)
    private EstadoIdInput estado;
}