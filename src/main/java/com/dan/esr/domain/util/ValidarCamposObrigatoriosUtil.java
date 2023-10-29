package com.dan.esr.domain.util;

import com.dan.esr.domain.exceptions.NegocioException;

import java.util.Objects;

public final class ValidarCamposObrigatoriosUtil {

    public static final String MSG_PROPRIEDADE_NAO_PODE_SER_NULA = "Não é permitido propriedade nula";


    private ValidarCamposObrigatoriosUtil(){}

    public static void validarCampoObrigatorio(Object obj, String msg) {
        try {
            Objects.requireNonNull(obj, msg + " é obrigatório ser informado.");
        }catch (NullPointerException e) {
            throw new NegocioException(e.getMessage());
        }
    }

}
