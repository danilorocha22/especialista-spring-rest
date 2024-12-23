package com.dan.esr.core.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import java.math.BigDecimal;
import java.util.Objects;

import static java.util.Objects.nonNull;

public class MultiploConstraint implements ConstraintValidator<Multiplo, Number> {
    private int numeroMultiplo;

    @Override
    public void initialize(Multiplo constraintAnnotation) {
        this.numeroMultiplo = constraintAnnotation.numero();
    }

    @Override
    public boolean isValid(Number value, ConstraintValidatorContext context) {
        boolean valido = true;

        if (nonNull(value)) {
            var valorDecimal = BigDecimal.valueOf(value.doubleValue());
            var multiploDecimal = BigDecimal.valueOf(this.numeroMultiplo);
            var resto = valorDecimal.remainder(multiploDecimal);
            valido = BigDecimal.ZERO.compareTo(resto) == 0;
        }

        return valido;
    }
}
