package com.dan.esr.core.assemblers;

import com.dan.esr.api.controllers.cozinha.CozinhaPesquisaController;
import com.dan.esr.api.models.input.cozinha.CozinhaInput;
import com.dan.esr.api.models.output.cozinha.CozinhaOutput;
import com.dan.esr.domain.entities.Cozinha;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CozinhaAssembler extends RepresentationModelAssemblerSupport<Cozinha, CozinhaOutput> {
    private final ModelMapper mapper;

    @Autowired
    public CozinhaAssembler(ModelMapper mapper) {
        super(CozinhaPesquisaController.class, CozinhaOutput.class);
        this.mapper = mapper;
    }

    @NonNull
    @Override
    public CozinhaOutput toModel(@NonNull Cozinha cozinha) {
        return this.mapper.map(cozinha, CozinhaOutput.class)
                .add(linkTo(methodOn(CozinhaPesquisaController.class).cozinha(cozinha.getId())).withSelfRel())
                .add(linkTo(CozinhaPesquisaController.class).withSelfRel());
    }

    @NonNull
    @Override
    public CollectionModel<CozinhaOutput> toCollectionModel(@NonNull Iterable<? extends Cozinha> entities) {
        return super.toCollectionModel(entities)
                .add(linkTo(CozinhaPesquisaController.class).withSelfRel());
    }

    /*public List<CozinhaOutput> toModelList(List<Cozinha> cozinhas) {
        return cozinhas.stream()
                .map(this::toModel)
                .toList();
    }*/

    public Cozinha toDomain(CozinhaInput cozinhaInput) {
        return this.mapper.map(cozinhaInput, Cozinha.class);
    }

    public List<Cozinha> toCollectionDomain(List<CozinhaInput> cozinhasIdInput) {
        return cozinhasIdInput.stream()
                .map(this::toDomain)
                .toList();
    }

    public void copyToCozinhaDomain(CozinhaInput cozinhaInput, Cozinha cozinha) {
        this.mapper.map(cozinhaInput, cozinha);
    }
}
