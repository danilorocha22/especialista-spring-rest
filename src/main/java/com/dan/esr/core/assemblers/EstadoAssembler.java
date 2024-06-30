package com.dan.esr.core.assemblers;

import com.dan.esr.api.models.input.estado.EstadoInput;
import com.dan.esr.api.models.output.estado.EstadoOutput;
import com.dan.esr.domain.entities.Estado;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class EstadoAssembler {
    private final ModelMapper mapper;

    public EstadoOutput toModel(Estado estado) {
        return this.mapper.map(estado, EstadoOutput.class);
    }

    public List<EstadoOutput> toModelList(List<Estado> estados) {
        return estados.stream()
                .map(this::toModel)
                .toList();
    }

    public Estado toDomain(EstadoInput estadoInput) {
        return this.mapper.map(estadoInput, Estado.class);
    }
}