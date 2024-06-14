package com.dan.esr.domain.services;

import lombok.Builder;
import lombok.Getter;

import java.io.InputStream;
import java.util.UUID;

import static java.util.Objects.nonNull;

public interface StorageAlbumService {

    void armazenar(NovaFoto novaFoto);
    void excluir(String nomeArquivo);
    FotoRecuperada baixar(String nomeArquivo);

    default String gerarNovoNomeFoto(String nomeOriginal) {
        return UUID.randomUUID() + "_" + nomeOriginal;
    }

    /*default void substituir(String nomeArquivoExistente, NovaFoto novaFoto) {
        this.armazenar(novaFoto);

        if (nonNull(nomeArquivoExistente)) {
            this.excluir(nomeArquivoExistente);
        }
    }*/

    @Getter
    @Builder
    class NovaFoto {
        private String nomeArquivo;
        private String contentType;
        private InputStream inputStream;
    }

    @Getter
    @Builder
    class FotoRecuperada {
        private InputStream inputStream;
        private String url;

        public boolean temInputStream() {
            return nonNull(inputStream);
        }

        public boolean temUrl() {
            return nonNull(url);
        }
    }
}