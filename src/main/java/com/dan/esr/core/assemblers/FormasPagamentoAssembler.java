package com.dan.esr.core.assemblers;

import com.dan.esr.api.models.input.formaspagamento.FormasPagamentoInput;
import com.dan.esr.api.models.output.FormasPagamentoOutput;
import com.dan.esr.domain.entities.FormasPagamento;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FormasPagamentoAssembler {
    private final ModelMapper mapper;

    public FormasPagamentoOutput toModel(FormasPagamento formasPagamento) {
        return mapper.map(formasPagamento, FormasPagamentoOutput.class);
    }

    public List<FormasPagamentoOutput> toCollectionModel(List<FormasPagamento> formasPagamento) {
        return formasPagamento.stream()
                .map(this::toModel)
                .toList();
    }

    public FormasPagamento toDomain(FormasPagamentoInput formasPagamentoInput) {
        return mapper.map(formasPagamentoInput, FormasPagamento.class);
    }

    public void copyToDomain(FormasPagamentoInput formasPagamentoInput, FormasPagamento formasPagamento) {
        mapper.map(formasPagamentoInput, formasPagamento);
    }
}
