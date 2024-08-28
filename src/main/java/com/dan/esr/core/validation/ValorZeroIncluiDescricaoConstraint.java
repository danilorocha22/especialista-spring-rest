package com.dan.esr.core.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ValidationException;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.Objects;

public class ValorZeroIncluiDescricaoConstraint implements ConstraintValidator<ValorZeroIncluiDescricao, Object> {
    private String valorField;
    private String descricaoField;
    private String descricaoObrigatoria;

    @Override
    public void initialize(ValorZeroIncluiDescricao constraintAnnotation) {
        this.valorField = constraintAnnotation.valorField();
        this.descricaoField = constraintAnnotation.descricaoField();
        this.descricaoObrigatoria = constraintAnnotation.descricaoObrigatoria();
    }

    @Override
    public boolean isValid(Object objetoValidacao, ConstraintValidatorContext context) {
        try {
            var valor = (BigDecimal) getObject(objetoValidacao, this.valorField);
            var descricao = (String) getObject(objetoValidacao, this.descricaoField);
            return validar(valor, descricao);

        } catch (Exception ex) {
            throw new ValidationException(ex.getLocalizedMessage(), ex);
        }
    }

    private Object getObject(Object objetoValidacao, String nomePropriedade)
            throws IllegalAccessException, InvocationTargetException {

        return Objects.requireNonNull(BeanUtils.getPropertyDescriptor(objetoValidacao.getClass(), nomePropriedade))
                .getReadMethod().invoke(objetoValidacao);
    }

    private boolean validar(BigDecimal valor, String descricao) {
        boolean valido = true;

        if (valor != null && isZero(valor) && descricao != null) {
            valido = descricao.toLowerCase().contains(this.descricaoObrigatoria.toLowerCase());
        }

        return valido;
    }

    private boolean isZero(BigDecimal valor) {
        return BigDecimal.ZERO.compareTo(valor) == 0;
    }
}