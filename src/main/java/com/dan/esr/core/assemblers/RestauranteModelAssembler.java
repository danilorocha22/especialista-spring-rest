package com.dan.esr.core.assemblers;

import com.dan.esr.api.controllers.restaurante.RestaurantePesquisaController;
import com.dan.esr.api.controllers.restaurante.RestauranteResponsavelController;
import com.dan.esr.api.controllers.usuario.UsuarioPesquisaController;
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

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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
                .add(linkTo(methodOn(RestaurantePesquisaController.class).restaurante(restaurante.getId()))
                        .withSelfRel())
                .add(linkTo(methodOn(RestaurantePesquisaController.class).restaurante(restaurante.getNome())).withSelfRel());
    }

    public RestauranteInput toModelInput(Restaurante restaurante) {
        return this.mapper.map(restaurante, RestauranteInput.class);
    }

    public RestauranteResponsaveisOutput toModelComResponsaveis(Restaurante restaurante) {
        RestauranteResponsaveisOutput responsaveisOutput =
                this.mapper.map(restaurante, RestauranteResponsaveisOutput.class)
                        .add(linkTo(methodOn(RestaurantePesquisaController.class).restaurante(restaurante.getId()))
                                .withSelfRel())
                        .add(linkTo(methodOn(RestauranteResponsavelController.class).buscarResponsaveis(restaurante.getId()))
                                .withSelfRel())
                        .add(linkTo(methodOn(RestaurantePesquisaController.class).restaurantes()).withSelfRel());

        responsaveisOutput.getUsuarios().forEach(responsavel -> responsavel
                .add(linkTo(methodOn(UsuarioPesquisaController.class).usuario(responsavel.getId()))
                        .withRel("responsavel")));

        return responsaveisOutput;
    }

    public RestauranteProdutosOutput toModelProdutos(Restaurante restaurante) {
        return this.mapper.map(restaurante, RestauranteProdutosOutput.class);
    }

    @NonNull
    @Override
    public CollectionModel<RestauranteOutput> toCollectionModel(@NonNull Iterable<? extends Restaurante> entities) {
        return super.toCollectionModel(entities)
                .add(linkTo(methodOn(RestaurantePesquisaController.class).restaurantes()).withSelfRel());
    }

    /*public List<RestauranteOutput> toCollectionModel(List<Restaurante> restaurantes) {
        return restaurantes.stream()
                .map(this::toModel)
                .toList();
    }*/
}