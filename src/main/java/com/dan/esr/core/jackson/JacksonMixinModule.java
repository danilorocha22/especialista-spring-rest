package com.dan.esr.core.jackson;

import com.dan.esr.api.models.mixin.RestauranteMixin;
import com.dan.esr.domain.entities.Restaurante;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.stereotype.Component;

@Component
public class JacksonMixinModule extends SimpleModule {

    public JacksonMixinModule() {
        setMixInAnnotation(Restaurante.class, RestauranteMixin.class);
    }

}
