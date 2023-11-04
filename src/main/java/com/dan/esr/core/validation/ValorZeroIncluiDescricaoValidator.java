package com.dan.esr.core.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.ValidationException;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.util.Objects;

public class ValorZeroIncluiDescricaoValidator implements ConstraintValidator<ValorZeroIncluiDescricao, Object> {

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
            BigDecimal valor = (BigDecimal) Objects.requireNonNull(BeanUtils.getPropertyDescriptor(
                    objetoValidacao.getClass(), this.valorField)).getReadMethod().invoke(objetoValidacao);

            String descricao = (String) Objects.requireNonNull(BeanUtils.getPropertyDescriptor(
                    objetoValidacao.getClass(), this.descricaoField)).getReadMethod().invoke(objetoValidacao);

            return validar(valor, descricao);

        } catch (Exception e) {
            throw new ValidationException(e);
        }

    }

    private boolean validar(BigDecimal valor, String descricao) {
        boolean valido = true;

        if (valor != null && BigDecimal.ZERO.compareTo(valor) == 0 && descricao != null) {
            valido = descricao.toLowerCase().contains(this.descricaoObrigatoria.toLowerCase());
        }

        return valido;
    }

}
