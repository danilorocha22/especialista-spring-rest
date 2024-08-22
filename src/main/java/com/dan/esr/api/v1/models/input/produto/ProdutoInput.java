package com.dan.esr.api.v1.models.input.produto;

import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProdutoInput {
    @NotBlank
    @ApiModelProperty(example = "Bife acebolado")
    private String nome;

    @NotBlank
    @ApiModelProperty(example = "Bife acebolado com molho madeira")
    private String descricao;

    @NotNull
    @ApiModelProperty(example = "R$ 50,00")
    private BigDecimal preco;

    @NotNull
    @ApiModelProperty(example = "true")
    private boolean ativo;
}