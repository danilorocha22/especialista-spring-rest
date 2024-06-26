package com.dan.esr.api.models.output.cidade;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("Cidade")
public class CidadeNomeOutput extends CidadeOutput {

    @ApiModelProperty(example = "1")
    private Long id;

    @ApiModelProperty(example = "Palmas")
    private String nome;
}