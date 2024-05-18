package com.dan.esr.core.assemblers;

import com.dan.esr.api.models.input.RestauranteInput;
import com.dan.esr.api.models.output.RestauranteOutput;
import com.dan.esr.api.models.output.RestauranteProdutosOutput;
import com.dan.esr.api.models.output.RestauranteStatusOutput;
import com.dan.esr.api.models.output.RestauranteSummaryOutput;
import com.dan.esr.domain.entities.Cozinha;
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

    public RestauranteStatusOutput toModelStatus(Restaurante restaurante) {
        return this.mapper.map(restaurante, RestauranteStatusOutput.class);
    }

    public RestauranteSummaryOutput toModelSummary(Restaurante restaurante) {
        return this.mapper.map(restaurante, RestauranteSummaryOutput.class);
    }

    public RestauranteInput toModelInput(Restaurante restaurante) {
        return this.mapper.map(restaurante, RestauranteInput.class);
    }

    public RestauranteProdutosOutput toModelProdutos(Restaurante restaurante) {
        return this.mapper.map(restaurante, RestauranteProdutosOutput.class);
    }

    public List<RestauranteSummaryOutput> toCollectionModel(List<Restaurante> restaurantes) {
        return restaurantes.stream()
                .map(this::toModelSummary)
                .toList();
    }
}