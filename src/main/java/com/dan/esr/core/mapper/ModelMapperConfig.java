package com.dan.esr.core.mapper;

import com.dan.esr.api.models.output.RestauranteOutput;
import com.dan.esr.domain.entities.Restaurante;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();

        mapper.createTypeMap(Restaurante.class, RestauranteOutput.class)
                .addMapping(Restaurante::getCozinha, RestauranteOutput::setCozinhaOutput);

        return mapper;
    }
}
