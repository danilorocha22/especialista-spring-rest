package com.dan.esr.core.util;

import com.dan.esr.domain.exceptions.EntidadeNaoEncontradaException;
import com.dan.esr.domain.exceptions.PropriedadeIlegalException;

import java.util.List;
import java.util.Objects;

public final class ValidacaoUtil {

    private ValidacaoUtil() {
    }

    public static void validarCampoObrigatorio(String nomeCampo, String msg) {
        nomeCampo = atribuirNulo(nomeCampo);

        try {
            Objects.requireNonNull(nomeCampo, "%s é obrigatório ser informado."
                    .formatted(msg));
        } catch (NullPointerException ex) {
            throw new PropriedadeIlegalException(ex.getMessage());
        }
    }

    private static String atribuirNulo(String nomeCampo) {
        if (isVazio(nomeCampo))
            nomeCampo = null;
        return nomeCampo;
    }

    private static boolean isVazio(String nomeCampo) {
        return nomeCampo != null && nomeCampo.isBlank();
    }

    public static void validarSeVazio(List<?> objetos) {
        if (objetos.isEmpty()) {
            throw new EntidadeNaoEncontradaException();
        }
    }
}