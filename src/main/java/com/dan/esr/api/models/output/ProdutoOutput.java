package com.dan.esr.api.models.output;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProdutoOutput {
    private Long id;
    private String nome;
    private String descricao;
    private BigDecimal preco;
}
