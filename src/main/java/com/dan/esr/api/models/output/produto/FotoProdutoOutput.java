package com.dan.esr.api.models.output.produto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FotoProdutoOutput {
    private String nomeArquivo;
    private String contentType;
    private Long tamanho;
    private String descricao;
}