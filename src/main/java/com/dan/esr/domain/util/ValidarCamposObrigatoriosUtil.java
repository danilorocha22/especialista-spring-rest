package com.dan.esr.domain.util;

import com.dan.esr.domain.exceptions.ParametroInadequadoException;

import java.util.Objects;

public final class ValidarCamposObrigatoriosUtil {

    private ValidarCamposObrigatoriosUtil(){}

    public static void validarCampoObrigatorio(Object obj, String msg) {
        try {
            Objects.requireNonNull(obj, msg + " é obrigatório ser informado.");
        }catch (NullPointerException e) {
            throw new ParametroInadequadoException(e.getMessage());
        }
    }

}
