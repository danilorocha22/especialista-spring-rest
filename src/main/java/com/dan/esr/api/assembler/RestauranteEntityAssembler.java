package com.dan.esr.api.assembler;

import com.dan.esr.api.models.input.RestauranteInput;
import com.dan.esr.domain.entities.Cozinha;
import com.dan.esr.domain.entities.Restaurante;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RestauranteEntityAssembler {

    @Autowired
    private ModelMapper mapper;

    private RestauranteEntityAssembler(){}

    public Restaurante toRestauranteDomain(RestauranteInput restauranteInput) {
        return this.mapper.map(restauranteInput, Restaurante.class);
    }

    public void copyToRestauranteDomain(RestauranteInput restauranteInput, Restaurante restaurante) {
        /* Para evitar org.hibernate.HibernateException: identifier of an instance of Cozinha was
        *  altered from 1 to 2
        **/
        restaurante.setCozinha(new Cozinha());
        mapper.map(restauranteInput, restaurante);
    }

}