package com.dan.esr.api.v1.assemblers;

import com.dan.esr.api.v1.controllers.formapagamento.FormaPagamentoController;
import com.dan.esr.api.v1.models.input.formapagamento.FormaPagamentoInput;
import com.dan.esr.api.v1.models.output.formapagamento.FormaPagamentoOutput;
import com.dan.esr.domain.entities.FormaPagamento;
import lombok.NonNull;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static com.dan.esr.api.v1.links.Links.linkToFormasPagamento;

@Component
public class FormaPagamentoAssembler extends RepresentationModelAssemblerSupport<FormaPagamento, FormaPagamentoOutput> {
    private final ModelMapper mapper;

    @Autowired
    public FormaPagamentoAssembler(ModelMapper mapper) {
        super(FormaPagamentoController.class, FormaPagamentoOutput.class);
        this.mapper = mapper;
    }

    @NonNull
    @Override
    public FormaPagamentoOutput toModel(@NonNull FormaPagamento formaPagamento) {
        FormaPagamentoOutput formaPagamentoOutput = createModelWithId(formaPagamento.getId(), formaPagamento);
        this.mapper.map(formaPagamento, formaPagamentoOutput);
        return formaPagamentoOutput.add(linkToFormasPagamento());
    }

    @NonNull
    @Override
    public CollectionModel<FormaPagamentoOutput> toCollectionModel(@NonNull Iterable<? extends FormaPagamento> entities) {
        return super.toCollectionModel(entities).add(linkToFormasPagamento());
    }

    public FormaPagamento toDomain(FormaPagamentoInput formaPagamentoInput) {
        return mapper.map(formaPagamentoInput, FormaPagamento.class);
    }

    public void copyToDomain(FormaPagamentoInput formaPagamentoInput, FormaPagamento formaPagamento) {
        mapper.map(formaPagamentoInput, formaPagamento);
    }
}