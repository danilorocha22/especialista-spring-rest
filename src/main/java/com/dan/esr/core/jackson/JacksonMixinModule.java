package com.dan.esr.core.jackson;

import com.dan.esr.api.models.mixin.CidadeMixin;
import com.dan.esr.api.models.mixin.CozinhaMixin;
import com.dan.esr.api.models.mixin.RestauranteMixin;
import com.dan.esr.domain.entities.Cidade;
import com.dan.esr.domain.entities.Cozinha;
import com.dan.esr.domain.entities.Restaurante;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.stereotype.Component;

@Component
public class JacksonMixinModule extends SimpleModule {

    public JacksonMixinModule() {
        setMixInAnnotation(Restaurante.class, RestauranteMixin.class);
        setMixInAnnotation(Cozinha.class, CozinhaMixin.class);
        setMixInAnnotation(Cidade.class, CidadeMixin.class);
    }

}
