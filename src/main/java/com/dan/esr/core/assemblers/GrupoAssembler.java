package com.dan.esr.core.assemblers;

import com.dan.esr.api.controllers.grupo.GrupoController;
import com.dan.esr.api.models.input.grupo.GrupoInput;
import com.dan.esr.api.models.output.grupo.GrupoOutput;
import com.dan.esr.api.models.output.grupo.GrupoPermissoesOutput;
import com.dan.esr.domain.entities.Grupo;
import lombok.NonNull;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static com.dan.esr.api.helper.links.Links.linkToGrupo;
import static com.dan.esr.api.helper.links.Links.linkToGrupos;

@Component
public class GrupoAssembler extends RepresentationModelAssemblerSupport<Grupo, GrupoOutput> {
    private final ModelMapper mapper;

    @Autowired
    public GrupoAssembler(ModelMapper mapper) {
        super(GrupoController.class, GrupoOutput.class);
        this.mapper = mapper;
    }

    @NonNull
    @Override
    public GrupoOutput toModel(@NonNull Grupo grupo) {
        return mapper.map(grupo, GrupoOutput.class)
                .add(linkToGrupo(grupo.getId()))
                .add(linkToGrupos());
    }

    @NonNull
    @Override
    public CollectionModel<GrupoOutput> toCollectionModel(@NonNull Iterable<? extends Grupo> entities) {
        return super.toCollectionModel(entities).add(linkToGrupos());
    }

    public GrupoPermissoesOutput toModelGrupoPermissoes(Grupo grupo) {
        return mapper.map(grupo, GrupoPermissoesOutput.class);
    }

    public Grupo toDomain(GrupoInput grupoInput) {
        return mapper.map(grupoInput, Grupo.class);
    }

    public void copyToDomain(GrupoInput grupoInput, Grupo grupo) {
        mapper.map(grupoInput, grupo);
    }
}