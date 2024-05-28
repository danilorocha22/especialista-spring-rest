package com.dan.esr.core.assemblers;

import com.dan.esr.api.models.input.cozinha.CozinhaInput;
import com.dan.esr.api.models.output.cozinha.CozinhaOutput;
import com.dan.esr.domain.entities.Cozinha;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CozinhaAssembler {
    private final ModelMapper mapper;

    public CozinhaOutput toModel(Cozinha cozinha) {
        return this.mapper.map(cozinha, CozinhaOutput.class);
    }

    public List<CozinhaOutput> toModelList(List<Cozinha> cozinhas) {
        return cozinhas.stream()
                .map(this::toModel)
                .toList();
    }

    public Cozinha toDomain(CozinhaInput cozinhaInput) {
        return this.mapper.map(cozinhaInput, Cozinha.class);
    }

    public List<Cozinha> toCollectionDomain(List<CozinhaInput> cozinhasIdInput) {
        return cozinhasIdInput.stream()
                .map(this::toDomain)
                .toList();
    }

    public void copyToCozinhaDomain(CozinhaInput cozinhaInput, Cozinha cozinha) {
        this.mapper.map(cozinhaInput, cozinha);
    }
}
