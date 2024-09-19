package com.dan.esr.domain.services;

public interface CodificacaoSenhaService {

    String criptografar(String rawPassword);

    boolean equivalente(String rawPassword, String encodedPassword);

    default boolean diferente(String rawPassword, String encodePassword) {
        return !this.equivalente(rawPassword, encodePassword);
    }
}
