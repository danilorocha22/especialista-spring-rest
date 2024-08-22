package com.dan.esr.api.v2.models.input;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CidadeInputV2 {
    /*@NotNull
    @ApiModelProperty(example = "1", required = true)
    private Long idCidade;*/

    @NotBlank
    @ApiModelProperty(example = "Palmas", required = true)
    private String nomeCidade;

    @NotNull
    @JsonIgnoreProperties(value = "sigla", allowGetters = true)
    private Long idEstado;
}