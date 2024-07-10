package com.dan.esr.core.assemblers;

import com.dan.esr.api.controllers.cozinha.CozinhaPesquisaController;
import com.dan.esr.api.models.input.cozinha.CozinhaInput;
import com.dan.esr.api.models.output.cozinha.CozinhaOutput;
import com.dan.esr.domain.entities.Cozinha;
import lombok.NonNull;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static com.dan.esr.api.helper.links.Links.linkToCozinha;
import static com.dan.esr.api.helper.links.Links.linkToCozinhas;

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
                .add(linkToCozinha(cozinha.getId()))
                .add(linkToCozinhas());
    }

    @NonNull
    @Override
    public CollectionModel<CozinhaOutput> toCollectionModel(@NonNull Iterable<? extends Cozinha> entities) {
        return super.toCollectionModel(entities).add(linkToCozinhas());
    }

    public Cozinha toDomain(CozinhaInput cozinhaInput) {
        return this.mapper.map(cozinhaInput, Cozinha.class);
    }

    public void copyToCozinhaDomain(CozinhaInput cozinhaInput, Cozinha cozinha) {
        this.mapper.map(cozinhaInput, cozinha);
    }
}