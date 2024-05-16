package com.dan.esr.core.mapper;

import com.dan.esr.api.models.output.*;
import com.dan.esr.domain.entities.Cidade;
import com.dan.esr.domain.entities.Restaurante;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();

        //Configurando o mapeamento de Restaurante para RestauranteOutput
        mapper.createTypeMap(Restaurante.class, RestauranteSummaryOutput.class)
                .addMapping(Restaurante::getCozinha, RestauranteSummaryOutput::setCozinhaOutput);

        //Configurando o mapeamento de Restaurante para RestauranteFormasDePagamentosOutput
        mapper.createTypeMap(Restaurante.class, RestauranteOutput.class)
                .addMapping(Restaurante::getFormasDePagamento, RestauranteOutput::setFormasDePagamento);

        //Configurando o mapeamento de Restaurante para RestauranteProdutosOutput
        mapper.createTypeMap(Restaurante.class, RestauranteProdutosOutput.class)
                .addMapping(Restaurante::getProdutos, RestauranteProdutosOutput::setProdutoOutputs);

        //Configurando o mapeamento de Cidade para CidadeNomeOutput
        mapper.createTypeMap(Cidade.class, CidadeNomeOutput.class)
                .addMappings(mapping -> mapping.map(Cidade::getCidadeUF, CidadeNomeOutput::setNome));



        return mapper;
    }
}
