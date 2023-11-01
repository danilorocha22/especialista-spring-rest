package com.dan.esr.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum ProblemType {

    FALHA_AO_LER_REQUISICAO("Erro ao tentar ler a requisição", "/erro-ao-tentar-ler-requisicao"),
    ENTIDADE_NAO_ENCONTRADA("Entidade não encontrada", "/entidade-nao-encontrada"),
    ENTIDADE_EM_USO("Entidade em uso", "/entidade-em-uso"),
    ERRO_NA_REQUISICAO("Erro na requisição", "/erro-na-requisicao");

    private final String title;
    private final String uri;
    private static final String URL = "https://danfood.com.br";

    ProblemType(String title, String path) {
        this.title = title;
        this.uri = URL.concat(path);
    }

}
