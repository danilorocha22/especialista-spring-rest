package com.dan.esr.api.models.output.cidade;

import com.dan.esr.api.models.output.estado.EstadoOutput;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("Cidade")
public class CidadeEstadoOutput extends CidadeOutput {
    //@ApiModelProperty(value = "ID da cidade", example = "1")
    @ApiModelProperty(example = "1")
    private Long id;

    @JsonProperty("cidade")
    @ApiModelProperty(example = "Palmas")
    private String nome;

    @JsonProperty("estado")
    @ApiModelProperty(example = "TO")
    private String estadoSigla;
}