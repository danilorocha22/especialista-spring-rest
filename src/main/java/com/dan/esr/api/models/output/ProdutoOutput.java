package com.dan.esr.api.models.output;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@ToString
@Getter
@Setter
public class ProdutoOutput {
    private Long id;
    private String nome;
    private String descricao;
    private BigDecimal preco;
}
