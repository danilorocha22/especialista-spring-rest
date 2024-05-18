package com.dan.esr.core.assemblers;

import com.dan.esr.api.models.input.CozinhaInput;
import com.dan.esr.api.models.output.CozinhaOutput;
import com.dan.esr.domain.entities.Cozinha;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@AllArgsConstructor
@Component
public class CozinhaAssembler {
    private ModelMapper mapper;

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
