package com.dan.esr.core.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
@Constraint(validatedBy = {ArquivoTamanhoConstraint.class})
public @interface ArquivoTamanho {

    String message() default "tamanho do arquivo excedeu o máximo permitido";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String max();
}