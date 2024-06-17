package com.dan.esr.domain.services;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Singular;

import java.util.Set;

public interface EnvioSmsService {
    void enviar(Sms sms);

    @Getter
    @Builder
    class Sms {
        @NonNull
        @Singular
        private Set<String> destinatarios;

        @NonNull
        private String assunto;

        @NonNull
        private String mensagem;
    }
}