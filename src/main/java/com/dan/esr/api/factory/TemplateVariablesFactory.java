package com.dan.esr.api.factory;

import com.dan.esr.domain.exceptions.NegocioException;
import lombok.Getter;
import org.springframework.hateoas.TemplateVariable;
import org.springframework.hateoas.TemplateVariables;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.hateoas.TemplateVariable.VariableType.REQUEST_PARAM;

@Getter
public enum TemplateVariablesFactory {

    SORT() {
        @Override
        public TemplateVariable toTemplateVariable() {
            return new TemplateVariable("sort", REQUEST_PARAM);
        }
    },
    PAGE() {
        @Override
        public TemplateVariable toTemplateVariable() {
            return new TemplateVariable("page", REQUEST_PARAM);
        }
    },
    SIZE() {
        @Override
        public TemplateVariable toTemplateVariable() {
            return new TemplateVariable("size", REQUEST_PARAM);
        }
    },
    CLIENTE_ID() {
        @Override
        public TemplateVariable toTemplateVariable() {
            return new TemplateVariable("clienteId", REQUEST_PARAM);
        }
    },
    RESTAURANTE_ID() {
        @Override
        public TemplateVariable toTemplateVariable() {
            return new TemplateVariable("restauranteId", REQUEST_PARAM);
        }
    },
    DATA_CRIACAO_INICIAL() {
        @Override
        public TemplateVariable toTemplateVariable() {
            return new TemplateVariable("dataCriacaoInicio", REQUEST_PARAM);
        }
    },
    DATA_CRIACAO_FINAL() {
        @Override
        public TemplateVariable toTemplateVariable() {
            return new TemplateVariable("dataCriacaoFim", REQUEST_PARAM);
        }
    },

    TIME_OFFSET() {
        @Override
        public TemplateVariable toTemplateVariable() {
            return new TemplateVariable("timeOffset", REQUEST_PARAM);
        }
    };

    private TemplateVariable.VariableType variableType;

    protected abstract TemplateVariable toTemplateVariable();

    public static TemplateVariables createTemplateVariables() {
        List<TemplateVariable> variables = Arrays.stream(values())
                .map(TemplateVariablesFactory::toTemplateVariable)
                .toList();
        return new TemplateVariables(variables);
    }

    public static TemplateVariables createTemplateVariables(TemplateVariablesFactory... variables) {
        if (variables == null || variables.length == 0) {
            return createTemplateVariables();
        }

        List<TemplateVariable> templateVariables = new ArrayList<>();
        for (TemplateVariablesFactory templateVariablesFactory : variables) {
            try {
                TemplateVariable templateVariable = templateVariablesFactory.toTemplateVariable();
                templateVariables.add(templateVariable);
            } catch (IllegalArgumentException e) {
                throw new NegocioException("Parâmetro da consulta inválido: " + templateVariablesFactory);
            }
        }
        return new TemplateVariables(templateVariables);
    }
}