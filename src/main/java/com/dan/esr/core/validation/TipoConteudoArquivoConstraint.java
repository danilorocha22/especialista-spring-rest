package com.dan.esr.core.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static java.util.Arrays.asList;

public class TipoConteudoArquivoConstraint implements ConstraintValidator<TipoConteudoArquivo, MultipartFile> {
    private List<String> tiposPermitidos;

    @Override
    public void initialize(TipoConteudoArquivo constraintAnnotation) {
        this.tiposPermitidos = asList(constraintAnnotation.tiposPermitidos());
    }

    @Override
    public boolean isValid(MultipartFile value, ConstraintValidatorContext context) {
        return value == null || tiposPermitidos.contains(value.getContentType());
    }
}