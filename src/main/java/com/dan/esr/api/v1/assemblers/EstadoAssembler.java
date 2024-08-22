package com.dan.esr.api.v1.assemblers;

import com.dan.esr.api.v1.controllers.estado.EstadoController;
import com.dan.esr.api.v1.models.input.estado.EstadoInput;
import com.dan.esr.api.v1.models.output.estado.EstadoOutput;
import com.dan.esr.domain.entities.Estado;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import static com.dan.esr.api.v1.links.Links.linkToEstados;

@Component
public class EstadoAssembler extends RepresentationModelAssemblerSupport<Estado, EstadoOutput> {
    private final ModelMapper mapper;

    @Autowired
    public EstadoAssembler(ModelMapper mapper) {
        super(EstadoController.class, EstadoOutput.class);
        this.mapper = mapper;
    }

    @NonNull
    @Override
    public EstadoOutput toModel(@NonNull Estado estado) {
        EstadoOutput estadoOutput = createModelWithId(estado.getId(), estado);
        return estadoOutput.add(linkToEstados());

    }

    @NonNull
    @Override
    public CollectionModel<EstadoOutput> toCollectionModel(@NonNull Iterable<? extends Estado> entities) {
        return super.toCollectionModel(entities).add(linkToEstados());
    }

    public Estado toDomain(EstadoInput estadoInput) {
        return this.mapper.map(estadoInput, Estado.class);
    }
}