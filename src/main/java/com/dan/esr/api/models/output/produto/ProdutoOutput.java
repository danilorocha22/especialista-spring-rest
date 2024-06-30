package com.dan.esr.api.models.output.produto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@ApiModel("Produto")
public class ProdutoOutput {
    @ApiModelProperty(example = "1")
    private Long id;

    @ApiModelProperty(example = "Bife acebolado")
    private String nome;

    @ApiModelProperty(example = "Bife acebolado com molho madeira")
    private String descricao;

    @ApiModelProperty(example = "R$ 100,00")
    private BigDecimal preco;

    @ApiModelProperty(example = "true")
    private boolean ativo;
}