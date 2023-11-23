package com.dan.esr.api.assembler;

import com.dan.esr.api.models.input.CozinhaIdInput;
import com.dan.esr.domain.entities.Cozinha;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public final class CozinhaEntityAssembler {

    private ModelMapper mapper;

    private CozinhaEntityAssembler(){}

    public Cozinha toRestauranteDomain(CozinhaIdInput cozinhaIdInput) {
        return this.mapper.map(cozinhaIdInput, Cozinha.class);
    }

}