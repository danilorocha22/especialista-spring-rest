package com.dan.esr.core.assemblers;

import com.dan.esr.api.models.input.endereco.EnderecoInput;
import com.dan.esr.domain.entities.Endereco;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EnderecoAssembler {
    private final ModelMapper mapper;

    public Endereco toDomain(EnderecoInput enderecoInput) {
        return this.mapper.map(enderecoInput, Endereco.class);
    }
}