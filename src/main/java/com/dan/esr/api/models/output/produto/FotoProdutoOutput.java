package com.dan.esr.api.models.output.produto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("Foto do produto")
public class FotoProdutoOutput {
    @ApiModelProperty(example = "arquivo.png ou arquivo.jpg", required = true)
    private String nomeArquivo;

    @ApiModelProperty(example = " image/png ou image/jpg", required = true)
    private String contentType;

    @ApiModelProperty(example = "500KB", required = true)
    private Long tamanho;

    @ApiModelProperty(example = "Arroz com bife acebolado", required = true)
    private String descricao;
}