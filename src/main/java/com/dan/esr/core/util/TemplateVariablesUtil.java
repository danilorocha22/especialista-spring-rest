package com.dan.esr.core.util;

import org.springframework.hateoas.TemplateVariable;
import org.springframework.hateoas.TemplateVariables;

import java.util.List;

import static org.springframework.hateoas.TemplateVariable.VariableType.REQUEST_PARAM;

public final class TemplateVariablesUtil {

    private TemplateVariablesUtil() {
    }

    public static TemplateVariables templateVariables() {
        return new TemplateVariables(List.of(
                new TemplateVariable("sort", REQUEST_PARAM),
                new TemplateVariable("page", REQUEST_PARAM),
                new TemplateVariable("size", REQUEST_PARAM)
        ));
    }
}