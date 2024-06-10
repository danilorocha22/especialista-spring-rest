package com.dan.esr.core.assemblers;

import com.dan.esr.api.models.input.produto.FotoProdutoInput;
import com.dan.esr.api.models.output.produto.FotoProdutoOutput;
import com.dan.esr.domain.entities.FotoProduto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FotoProdutoAssembler {

    private final ModelMapper mapper;

    public FotoProdutoOutput toModel(FotoProduto foto) {
        return this.mapper.map(foto, FotoProdutoOutput.class);
    }

    public FotoProduto toDomain(FotoProdutoInput input) {
        return this.mapper.map(input, FotoProduto.class);
    }
}