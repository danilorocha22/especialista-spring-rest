package com.dan.esr.core.assemblers;

import com.dan.esr.api.models.input.restaurante.RestauranteInput;
import com.dan.esr.api.models.output.restaurante.RestauranteOutput;
import com.dan.esr.api.models.output.restaurante.RestauranteProdutosOutput;
import com.dan.esr.api.models.output.restaurante.RestauranteResponsaveisOutput;
import com.dan.esr.domain.entities.Restaurante;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class RestauranteModelAssembler {
    private ModelMapper mapper;

    public RestauranteOutput toModel(Restaurante restaurante) {
        return this.mapper.map(restaurante, RestauranteOutput.class);
    }

    public RestauranteInput toModelInput(Restaurante restaurante) {
        return this.mapper.map(restaurante, RestauranteInput.class);
    }

    public RestauranteResponsaveisOutput toModelComResponsaveis(Restaurante restaurante) {
        return this.mapper.map(restaurante, RestauranteResponsaveisOutput.class);
    }

    public RestauranteProdutosOutput toModelProdutos(Restaurante restaurante) {
        return this.mapper.map(restaurante, RestauranteProdutosOutput.class);
    }

    public List<RestauranteOutput> toCollectionModel(List<Restaurante> restaurantes) {
        return restaurantes.stream()
                .map(this::toModel)
                .toList();
    }
}