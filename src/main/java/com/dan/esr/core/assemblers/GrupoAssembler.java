package com.dan.esr.core.assemblers;

import com.dan.esr.api.models.input.grupo.GrupoInput;
import com.dan.esr.api.models.output.GrupoOutput;
import com.dan.esr.domain.entities.Grupo;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GrupoAssembler {
    private final ModelMapper mapper;

    public GrupoOutput toModel(Grupo grupo) {
        return mapper.map(grupo, GrupoOutput.class);
    }

    public List<GrupoOutput> toCollectionModel(List<Grupo> grupos) {
        return grupos.stream()
                .map(this::toModel)
                .toList();
    }

    public Grupo toDomain(GrupoInput grupoInput) {
        return mapper.map(grupoInput, Grupo.class);
    }

    public void copyToDomain(GrupoInput grupoInput, Grupo grupo) {
        mapper.map(grupoInput, grupo);
    }
}
