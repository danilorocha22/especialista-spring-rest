package com.dan.esr.api.assembler;

import com.dan.esr.api.models.output.RestauranteOutput;
import com.dan.esr.domain.entities.Restaurante;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@AllArgsConstructor
@Component
public class RestauranteModelAssembler {

    private ModelMapper mapper;

    public RestauranteOutput toModel(Restaurante restaurante) {
        return this.mapper.map(restaurante, RestauranteOutput.class);
    }

    public List<RestauranteOutput> toCollectionModel(List<Restaurante> restaurantes) {
        return restaurantes.stream()
                .map(this::toModel)
                .toList();
    }

}
