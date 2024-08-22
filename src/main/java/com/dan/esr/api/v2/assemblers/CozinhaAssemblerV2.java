package com.dan.esr.api.v2.assemblers;

import com.dan.esr.api.v2.controllers.cozinha.CozinhaPesquisaControllerV2;
import com.dan.esr.api.v2.models.input.CozinhaInputV2;
import com.dan.esr.api.v2.models.output.CozinhaOutputV2;
import com.dan.esr.domain.entities.Cozinha;
import lombok.NonNull;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static com.dan.esr.api.v2.links.LinksV2.linkToCozinha;
import static com.dan.esr.api.v2.links.LinksV2.linkToCozinhas;

@Component
public class CozinhaAssemblerV2 extends RepresentationModelAssemblerSupport<Cozinha, CozinhaOutputV2> {
    private final ModelMapper mapper;

    @Autowired
    public CozinhaAssemblerV2(ModelMapper mapper) {
        super(CozinhaPesquisaControllerV2.class, CozinhaOutputV2.class);
        this.mapper = mapper;
    }

    @NonNull
    @Override
    public CozinhaOutputV2 toModel(@NonNull Cozinha cozinha) {
        return this.mapper.map(cozinha, CozinhaOutputV2.class)
                .add(linkToCozinha(cozinha.getId()))
                .add(linkToCozinhas());
    }

    @NonNull
    @Override
    public CollectionModel<CozinhaOutputV2> toCollectionModel(@NonNull Iterable<? extends Cozinha> entities) {
        return super.toCollectionModel(entities).add(linkToCozinhas());
    }

    public Cozinha toDomain(CozinhaInputV2 cozinhaInput) {
        return this.mapper.map(cozinhaInput, Cozinha.class);
    }

    public void copyToCozinhaDomain(CozinhaInputV2 cozinhaInput, Cozinha cozinha) {
        this.mapper.map(cozinhaInput, cozinha);
    }
}