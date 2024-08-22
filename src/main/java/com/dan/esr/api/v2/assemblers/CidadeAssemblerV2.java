package com.dan.esr.api.v2.assemblers;

import com.dan.esr.api.v2.controllers.cidade.CidadeControllerV2;
import com.dan.esr.api.v2.models.input.CidadeInputV2;
import com.dan.esr.api.v2.models.output.CidadeOutputV2;
import com.dan.esr.domain.entities.Cidade;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import static com.dan.esr.api.v2.links.LinksV2.linkToCidades;

@Component
public class CidadeAssemblerV2 extends RepresentationModelAssemblerSupport<Cidade, CidadeOutputV2> {
    private final ModelMapper mapper;

    @Autowired
    public CidadeAssemblerV2(ModelMapper mapper) {
        super(CidadeControllerV2.class, CidadeOutputV2.class);
        this.mapper = mapper;
    }

    @NonNull
    @Override
    public CidadeOutputV2 toModel(@NonNull Cidade cidade) {
        CidadeOutputV2 cidadeOutput = createModelWithId(cidade.getId(), cidade);
        this.mapper.map(cidade, cidadeOutput);
        return cidadeOutput;
    }

    @NonNull
    @Override
    public CollectionModel<CidadeOutputV2> toCollectionModel(@NonNull Iterable<? extends Cidade> entities) {
        return super.toCollectionModel(entities)
                .add(linkToCidades());
    }

    public Cidade toDomain(CidadeInputV2 cidadeInput) {
        return this.mapper.map(cidadeInput, Cidade.class);
    }

    public void copyToCidadeDomain(CidadeInputV2 cidadeInput, Cidade cidade) {
        this.mapper.map(cidadeInput, cidade);
    }
}