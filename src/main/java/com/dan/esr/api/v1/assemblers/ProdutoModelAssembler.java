package com.dan.esr.api.v1.assemblers;

import com.dan.esr.api.v1.models.output.produto.ProdutoOutput;
import com.dan.esr.domain.entities.Produto;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@AllArgsConstructor
@Component
public class ProdutoModelAssembler {
    private ModelMapper mapper;

    public ProdutoOutput toModel(Produto produto) {
        return mapper.map(produto, ProdutoOutput.class);
    }

    public List<ProdutoOutput> toCollectionDTO(List<Produto> produtos) {
        return produtos.stream()
                .map(this::toModel)
                .toList();
    }
}
