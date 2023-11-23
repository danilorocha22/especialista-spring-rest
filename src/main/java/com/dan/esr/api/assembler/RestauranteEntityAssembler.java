package com.dan.esr.api.assembler;

import com.dan.esr.api.models.input.RestauranteInput;
import com.dan.esr.domain.entities.Cozinha;
import com.dan.esr.domain.entities.Restaurante;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public final class RestauranteEntityAssembler {

    private ModelMapper mapper;

    private RestauranteEntityAssembler(){}

    public Restaurante toRestauranteDomain(RestauranteInput restauranteInput) {
        return this.mapper.map(restauranteInput, Restaurante.class);
    }

}