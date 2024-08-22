package com.dan.esr.core.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.mediatype.hal.HalConfiguration;
import org.springframework.http.MediaType;

@Configuration
public class DanHalConfiguration {

    //Configuração usada quando se está fazendo o versionamento da API por media type
    @Bean
    public HalConfiguration globalPolicy() {
        return new HalConfiguration()
                .withMediaType(MediaType.APPLICATION_JSON)
                .withMediaType(DanFoodMediaTypes.V1_APPLICATION_JSON);
    }
}