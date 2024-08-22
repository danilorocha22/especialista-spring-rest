package com.dan.esr.api.v1.assemblers;

import com.dan.esr.api.v1.controllers.restaurante.RestauranteFotoProdutoController;
import com.dan.esr.api.v1.models.input.produto.FotoProdutoInput;
import com.dan.esr.api.v1.models.output.produto.FotoProdutoOutput;
import com.dan.esr.domain.entities.FotoProduto;
import lombok.NonNull;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class FotoProdutoAssembler extends RepresentationModelAssemblerSupport<FotoProduto, FotoProdutoOutput> {
    private final ModelMapper mapper;

    @Autowired
    public FotoProdutoAssembler(ModelMapper mapper) {
        super(RestauranteFotoProdutoController.class, FotoProdutoOutput.class);
        this.mapper = mapper;
    }

    @NonNull
    @Override
    public FotoProdutoOutput toModel(@NonNull FotoProduto foto) {
        FotoProdutoOutput fotoProdutoOutput = createModelWithId(foto.getId(), foto);
        this.mapper.map(foto, fotoProdutoOutput);
        return fotoProdutoOutput;
    }

    public FotoProduto toDomain(FotoProdutoInput input) {
        return this.mapper.map(input, FotoProduto.class);
    }
}