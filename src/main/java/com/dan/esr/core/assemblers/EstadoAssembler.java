package com.dan.esr.core.assemblers;

import com.dan.esr.api.controllers.estado.EstadoController;
import com.dan.esr.api.models.input.estado.EstadoInput;
import com.dan.esr.api.models.output.estado.EstadoOutput;
import com.dan.esr.domain.entities.Estado;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class EstadoAssembler extends RepresentationModelAssemblerSupport<Estado, EstadoOutput> {
    private final ModelMapper mapper;

    @Autowired
    public EstadoAssembler( ModelMapper mapper) {
        super(EstadoController.class, EstadoOutput.class);
        this.mapper = mapper;
    }

    @NonNull
    @Override
    public EstadoOutput toModel(@NonNull Estado estado) {
        EstadoOutput estadoOutput = this.mapper.map(estado, EstadoOutput.class);
        estadoOutput.add(linkTo(methodOn(EstadoController.class).estado(estado.getId())).withSelfRel());
        return estadoOutput;
    }

    @NonNull
    @Override
    public CollectionModel<EstadoOutput> toCollectionModel(@NonNull Iterable<? extends Estado> entities) {
        return super.toCollectionModel(entities)
                .add(linkTo(methodOn(EstadoController.class).estados()).withSelfRel());
    }

    /*public List<EstadoOutput> toModelList(List<Estado> estados) {
        return estados.stream()
                .map(this::toModel)
                .toList();
    }*/

    public Estado toDomain(EstadoInput estadoInput) {
        return this.mapper.map(estadoInput, Estado.class);
    }
}