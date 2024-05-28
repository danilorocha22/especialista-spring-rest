package com.dan.esr.api.models.input.produto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter @Setter
public class ProdutoInput {
    @NotBlank private String nome;
    @NotBlank private String descricao;
    @NotNull private BigDecimal preco;
    @NotNull private boolean ativo;
}