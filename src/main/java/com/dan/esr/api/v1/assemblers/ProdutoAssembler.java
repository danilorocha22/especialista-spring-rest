package com.dan.esr.api.v1.assemblers;

import com.dan.esr.api.v1.controllers.restaurante.RestauranteProdutoController;
import com.dan.esr.api.v1.models.input.produto.ProdutoInput;
import com.dan.esr.api.v1.models.output.produto.ProdutoOutput;
import com.dan.esr.api.v1.models.output.restaurante.RestauranteProdutosOutput;
import com.dan.esr.domain.entities.Produto;
import com.dan.esr.domain.entities.Restaurante;
import lombok.NonNull;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static com.dan.esr.api.v1.links.Links.*;

@Component
public class ProdutoAssembler extends RepresentationModelAssemblerSupport<Produto, ProdutoOutput> {
    private final ModelMapper mapper;

    @Autowired
    public ProdutoAssembler(ModelMapper mapper) {
        super(RestauranteProdutoController.class, ProdutoOutput.class);
        this.mapper = mapper;
    }

    @NonNull
    @Override
    public ProdutoOutput toModel(@NonNull Produto produto) {
        return (ProdutoOutput) this.mapper.map(produto, ProdutoOutput.class)
                .add(linkToProduto(produto.getRestaurante().getId(), produto.getId()))
                .add(linkToProdutos(produto.getRestaurante().getId()));
    }

    public RestauranteProdutosOutput toModelProdutos(Restaurante restaurante) {
        return (RestauranteProdutosOutput) this.mapper.map(restaurante, RestauranteProdutosOutput.class)
                .add(linkToRestaurante(restaurante.getId()))
                .add(linkToProdutos(restaurante.getId()));
    }

    public Produto toDomain(ProdutoInput produtoInput) {
        return mapper.map(produtoInput, Produto.class);
    }

    public void copyToDomain(ProdutoInput produtoInput, Produto produto) {
        mapper.map(produtoInput, produto);
    }
}