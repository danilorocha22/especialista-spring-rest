package com.dan.esr.core.assemblers;

import com.dan.esr.api.models.input.RestauranteInput;
import com.dan.esr.api.models.output.RestauranteProdutosOutput;
import com.dan.esr.domain.entities.*;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class RestauranteEntityAssembler {
    private ModelMapper mapper;

    public Restaurante toDomain(RestauranteInput restauranteInput) {
        return this.mapper.map(restauranteInput, Restaurante.class);
    }

    public Restaurante toDomain(RestauranteProdutosOutput restauranteOutput) {
        return this.mapper.map(restauranteOutput, Restaurante.class);
    }

    public void copyToRestauranteDomain(RestauranteInput restauranteInput, Restaurante restaurante) {
        /* Nova Cozinha, para evitar org.hibernate.HibernateException: identifier of an instance of Cozinha was
           altered from 1 to 2 */
        if (restaurante.getEndereco() != null) {
            restaurante.getEndereco().setCidade(new Cidade());
        }

        restaurante.setCozinha(new Cozinha());
        mapper.map(restauranteInput, restaurante);
    }

}