package com.dan.esr.api.v1.models.output.produto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProdutoFotoOutput {
    private Long produtoId;
    private String nomeProduto;
    private String descricao;
    private Long tamanho;
}