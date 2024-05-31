package com.dan.esr.core.assemblers;

import com.dan.esr.api.models.input.produto.ProdutoInput;
import com.dan.esr.api.models.output.produto.ProdutoOutput;
import com.dan.esr.domain.entities.Produto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ProdutoAssembler {
    private final ModelMapper mapper;

    public ProdutoOutput toModel(Produto produto) {
        return mapper.map(produto, ProdutoOutput.class);
    }

    public Produto toDomain(ProdutoInput produtoInput) {
        return mapper.map(produtoInput, Produto.class);
    }

    public List<ProdutoOutput> toCollectionModel(List<Produto> produtos) {
        return produtos.stream()
                .map(this::toModel)
                .toList();
    }

    public void copyToDomain(ProdutoInput produtoInput, Produto produto) {
        mapper.map(produtoInput, produto);
    }
}