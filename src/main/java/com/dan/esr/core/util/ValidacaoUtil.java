package com.dan.esr.core.util;

import com.dan.esr.domain.exceptions.PropriedadeIlegalException;

import java.util.Objects;

public final class ValidacaoUtil {
    public static final String MSG_PROPRIEDADE_NAO_PODE_SER_NULA = "Não é permitido propriedade nula";

    private ValidacaoUtil() {
    }

    public static void validarCampoObrigatorio(Object obj, String msg) {
        try {
            Objects.requireNonNull(obj, "%s é obrigatório ser informado.".formatted(msg));
        } catch (NullPointerException e) {
            throw new PropriedadeIlegalException(e.getMessage());
        }
    }

    public static boolean isCampoVazio(String nome) {
        return nome != null && nome.isBlank();
    }

}
