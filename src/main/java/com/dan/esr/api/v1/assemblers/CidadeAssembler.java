package com.dan.esr.api.v1.assemblers;

import com.dan.esr.api.v1.controllers.cidade.CidadeController;
import com.dan.esr.api.v1.models.input.cidade.CidadeInput;
import com.dan.esr.api.v1.models.output.cidade.CidadeOutput;
import com.dan.esr.domain.entities.Cidade;
import com.dan.esr.domain.entities.Estado;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import static com.dan.esr.api.v1.links.Links.*;

@Component
public class CidadeAssembler extends RepresentationModelAssemblerSupport<Cidade, CidadeOutput> {
    private final ModelMapper mapper;

    @Autowired
    public CidadeAssembler(ModelMapper mapper) {
        super(CidadeController.class, CidadeOutput.class);
        this.mapper = mapper;
    }

    @NonNull
    @Override
    public CidadeOutput toModel(@NonNull Cidade cidade) {
        CidadeOutput cidadeOutput = createModelWithId(cidade.getId(), cidade);
        this.mapper.map(cidade, cidadeOutput);
        cidadeOutput.getEstado()
                .add(linkToEstado(cidade.getEstado().getId()))
                .add(linkToEstados());
        return cidadeOutput;
    }

    @NonNull
    @Override
    public CollectionModel<CidadeOutput> toCollectionModel(@NonNull Iterable<? extends Cidade> entities) {
        return super.toCollectionModel(entities)
                .add(linkToCidades());
    }

    public Cidade toDomain(CidadeInput cidadeInput) {
        return this.mapper.map(cidadeInput, Cidade.class);
    }

    public void copyToCidadeDomain(CidadeInput cidadeInput, Cidade cidade) {
        /* Nova instância de Estado para evitar: org.hibernate.HibernateException: identifier of an instance of
           com.dan.esr.domain.entities.Estado was altered from 2 to 1 */
        cidade.setEstado(new Estado());
        this.mapper.map(cidadeInput, cidade);
    }
}