package com.dan.esr.core.assemblers;

import com.dan.esr.api.models.input.restaurante.RestauranteInput;
import com.dan.esr.api.models.output.restaurante.RestauranteOutput;
import com.dan.esr.api.models.output.restaurante.RestauranteProdutosOutput;
import com.dan.esr.api.models.output.restaurante.RestauranteResponsaveisOutput;
import com.dan.esr.domain.entities.Restaurante;
import lombok.NonNull;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static com.dan.esr.api.helper.links.Links.*;

@Component
public class RestauranteModelAssembler extends RepresentationModelAssemblerSupport<Restaurante, RestauranteOutput> {
    private final ModelMapper mapper;

    @Autowired
    public RestauranteModelAssembler(ModelMapper mapper) {
        super(RestauranteResponsaveisOutput.class, RestauranteOutput.class);
        this.mapper = mapper;
    }

    @NonNull
    @Override
    public RestauranteOutput toModel(@NonNull Restaurante restaurante) {
        return this.mapper.map(restaurante, RestauranteOutput.class)
                .add(linkToRestaurante(restaurante.getId()))
                .add(linkToCozinha(restaurante.getCozinha().getId()))
                .add(linkToFormasPagamento(restaurante.getFormasDePagamento()))
                .add(linkToCidade(restaurante.getEndereco().getCidade().getId()))
                .add(linkToResponsaveisRestaurante(restaurante.getId()))
                .add(linkToRestaurantes());
    }

    public RestauranteResponsaveisOutput toModelComResponsaveis(Restaurante restaurante) {
        RestauranteResponsaveisOutput responsaveisOutput =
                this.mapper.map(restaurante, RestauranteResponsaveisOutput.class)
                        .add(linkToRestaurante(restaurante.getId()))
                        .add(linkToResponsaveisRestaurante(restaurante.getId()))
                        .add(linkToRestaurantes());

        responsaveisOutput.getUsuarios().forEach(responsavel -> responsavel
                .add(linkToUsuario(responsavel.getId(), "responsavel")));

        return responsaveisOutput;
    }

    public RestauranteProdutosOutput toModelProdutos(Restaurante restaurante) {
        return this.mapper.map(restaurante, RestauranteProdutosOutput.class);
    }

    public RestauranteInput toModelInput(Restaurante restaurante) {
        return this.mapper.map(restaurante, RestauranteInput.class);
    }

    @NonNull
    @Override
    public CollectionModel<RestauranteOutput> toCollectionModel(@NonNull Iterable<? extends Restaurante> entities) {
        return super.toCollectionModel(entities).add(linkToRestaurantes());
    }
}