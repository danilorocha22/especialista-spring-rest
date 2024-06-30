package com.dan.esr.api.models.output.cidade;

import com.dan.esr.api.models.output.estado.EstadoOutput;
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

    @ApiModelProperty(example = "Palmas")
    private String nome;

    private EstadoOutput estado;
}