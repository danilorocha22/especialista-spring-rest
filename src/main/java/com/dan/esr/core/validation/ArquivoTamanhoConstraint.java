package com.dan.esr.core.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.util.unit.DataSize;
import org.springframework.web.multipart.MultipartFile;

public class ArquivoTamanhoConstraint implements ConstraintValidator<ArquivoTamanho, MultipartFile> {
    private DataSize maxSize;

    @Override
    public void initialize(ArquivoTamanho constraintAnnotation) {
        this.maxSize = DataSize.parse(constraintAnnotation.max());
    }

    @Override
    public boolean isValid(MultipartFile value, ConstraintValidatorContext context) {
        return value == null || value.getSize() <= this.maxSize.toBytes();
    }
}