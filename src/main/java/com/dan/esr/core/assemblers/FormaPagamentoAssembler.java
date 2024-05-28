package com.dan.esr.core.assemblers;

import com.dan.esr.api.models.input.formapagamento.FormaPagamentoInput;
import com.dan.esr.api.models.output.formapagamento.FormaPagamentoOutput;
import com.dan.esr.domain.entities.FormaPagamento;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FormaPagamentoAssembler {
    private final ModelMapper mapper;

    public FormaPagamentoOutput toModel(FormaPagamento formaPagamento) {
        return mapper.map(formaPagamento, FormaPagamentoOutput.class);
    }

    public List<FormaPagamentoOutput> toCollectionModel(List<FormaPagamento> formaPagamento) {
        return formaPagamento.stream()
                .map(this::toModel)
                .toList();
    }

    public FormaPagamento toDomain(FormaPagamentoInput formaPagamentoInput) {
        return mapper.map(formaPagamentoInput, FormaPagamento.class);
    }

    public void copyToDomain(FormaPagamentoInput formaPagamentoInput, FormaPagamento formaPagamento) {
        mapper.map(formaPagamentoInput, formaPagamento);
    }
}
