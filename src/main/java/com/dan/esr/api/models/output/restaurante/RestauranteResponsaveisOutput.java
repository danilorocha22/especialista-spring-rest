package com.dan.esr.api.models.output.restaurante;

import com.dan.esr.api.models.output.usuario.UsuarioOutput;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@ApiModel("Responsáveis do Restaurante")
public class RestauranteResponsaveisOutput {
    @JsonProperty("restaurante")
    @ApiModelProperty(example = "Restaurante")
    private String nome;

    @JsonProperty("responsaveis")
    private List<UsuarioOutput> usuarios;
}