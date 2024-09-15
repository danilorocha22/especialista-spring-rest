package com.dan.esr.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum ProblemType {

    FALHA_AO_LER_REQUISICAO("Erro ao Ler a Requisição", "/erro-ao-ler-requisicao"),
    RECURSO_NAO_ENCONTRADO("Recurso não Encontrado", "/recurso-nao-encontrado"),
    RECURSO_EXISTENTE("Recurso já Existe", "/recurso-existente"),
    ENTIDADE_EM_USO("Entidade em Uso", "/entidade-em-uso"),
    ERRO_NA_REQUISICAO("Erro na Requisição", "/erro-na-requisicao"),
    PROPRIEDADE_IGNORADA("Propriedade Ignorada", "/propriedade-ignorada"),
    PROPRIEDADE_DESCONHECIDA("Propriedade Desconhecida", "/propriedade-desconhecida"),
    PROPRIEDADE_INVALIDA("Propriedade Inválida", "/propriedade-invalida"),
    PARAMETRO_INVALIDO("Parâmetro Inválido", "/parametro-invalido"),
    ERRO_INTERNO_DO_SISTEMA("Erro do Sistema", "/erro-interno-do-sistema"),
    ACESSO_NEGADO("Acesso Negado", "/acesso-negado");

    private final String title;
    private final String uri;
    private static final String URL = "https://danfood.com.br";

    ProblemType(String title, String path) {
        this.title = title;
        this.uri = URL.concat(path);
    }

}
