package com.dan.esr.core.assemblers;

import com.dan.esr.api.models.input.restaurante.RestauranteInput;
import com.dan.esr.api.models.output.restaurante.RestauranteProdutosOutput;
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

    /**
     * Copia dados de RestauranteInput para Restaurante, resetando
     * Cozinha e Cidade para evitar problemas de alteração de identificador:
     * org.hibernate.HibernateException: identifier of an instance of Cozinha/Cidade was altered from 1 to 2
     */
    public void copyToRestaurante(RestauranteInput restauranteInput, Restaurante restaurante) {
        resetCozinha(restaurante);
        resetCidade(restaurante);
        mapper.map(restauranteInput, restaurante);
    }

    private static void resetCozinha(Restaurante restaurante) {
        restaurante.setCozinha(new Cozinha());
    }

    private static void resetCidade(Restaurante restaurante) {
        if (restaurante.getEndereco() != null) {
            restaurante.getEndereco().setCidade(new Cidade());
        }
    }
}