package com.dan.esr.api.assembler;

import com.dan.esr.api.models.output.CozinhaOutput;
import com.dan.esr.api.models.output.RestauranteOutput;
import com.dan.esr.domain.entities.Cozinha;
import com.dan.esr.domain.entities.Restaurante;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@AllArgsConstructor
@Component
public class CozinhaModelAssembler {

    private ModelMapper mapper;

    public CozinhaOutput toModel(Cozinha cozinha) {
        return this.mapper.map(cozinha, CozinhaOutput.class);
    }

    public List<CozinhaOutput> toCollectionModel(List<Cozinha> cozinhas) {
        return cozinhas.stream()
                .map(this::toModel)
                .toList();
    }

}
