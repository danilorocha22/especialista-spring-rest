package com.dan.esr.core.assemblers;

import com.dan.esr.api.controllers.cidade.CidadeController;
import com.dan.esr.api.controllers.estado.EstadoController;
import com.dan.esr.api.models.input.cidade.CidadeInput;
import com.dan.esr.api.models.output.cidade.CidadeOutput;
import com.dan.esr.domain.entities.Cidade;
import com.dan.esr.domain.entities.Estado;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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
        cidadeOutput.getEstado().add(linkTo(methodOn(EstadoController.class).estado(cidade.getEstado().getId()))
                .withSelfRel());
        cidadeOutput.getEstado().add(linkTo(methodOn(EstadoController.class).estados()).withSelfRel());
        return cidadeOutput;
    }

    @NonNull
    @Override
    public CollectionModel<CidadeOutput> toCollectionModel(@NonNull Iterable<? extends Cidade> entities) {
        return super.toCollectionModel(entities)
                .add(linkTo(CidadeController.class).withSelfRel());
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