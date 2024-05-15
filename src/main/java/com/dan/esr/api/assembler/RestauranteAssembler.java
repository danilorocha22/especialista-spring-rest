package com.dan.esr.api.assembler;

import com.dan.esr.api.models.input.RestauranteInput;
import com.dan.esr.api.models.output.RestauranteOutput;
import com.dan.esr.api.models.output.RestauranteProdutosOutput;
import com.dan.esr.api.models.output.RestauranteSummaryOutput;
import com.dan.esr.domain.entities.Cozinha;
import com.dan.esr.domain.entities.Restaurante;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@AllArgsConstructor
@Component
public class RestauranteAssembler {
    private ModelMapper mapper;


    public Restaurante toDomain(RestauranteInput restauranteInput) {
        return this.mapper.map(restauranteInput, Restaurante.class);
    }

    public void copyToRestauranteDomain(RestauranteInput restauranteInput, Restaurante restaurante) {
        /* Para evitar org.hibernate.HibernateException: identifier of an instance of Cozinha was
         *  altered from 1 to 2
         **/
        restaurante.setCozinha(new Cozinha());
        mapper.map(restauranteInput, restaurante);
    }

    public RestauranteSummaryOutput toModelSummary(Restaurante restaurante) {
        return this.mapper.map(restaurante, RestauranteSummaryOutput.class);
    }

    public RestauranteOutput toModel(Restaurante restaurante) {
        return this.mapper.map(restaurante, RestauranteOutput.class);
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