package com.dan.esr.api.v1.assemblers;

import com.dan.esr.api.v1.controllers.restaurante.RestaurantePesquisaController;
import com.dan.esr.api.v1.models.input.restaurante.RestauranteInput;
import com.dan.esr.api.v1.models.output.restaurante.RestauranteFormasPagamentoOutput;
import com.dan.esr.api.v1.models.output.restaurante.RestauranteOutput;
import com.dan.esr.api.v1.models.output.restaurante.RestauranteResponsaveisOutput;
import com.dan.esr.domain.entities.Restaurante;
import lombok.NonNull;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static com.dan.esr.api.v1.links.Links.*;

@Component
public class RestauranteModelAssembler extends RepresentationModelAssemblerSupport<Restaurante, RestauranteOutput> {
    private final ModelMapper mapper;

    @Autowired
    public RestauranteModelAssembler(ModelMapper mapper) {
        super(RestaurantePesquisaController.class, RestauranteOutput.class);
        this.mapper = mapper;
    }

    @NonNull
    @Override
    public RestauranteOutput toModel(@NonNull Restaurante restaurante) {
        return this.mapper.map(restaurante, RestauranteOutput.class)
                .add(linkToRestaurante(restaurante.getId()))
                .add(linkToCozinha(restaurante.getCozinha().getId()))
                .add(linkToFormasPagamento(restaurante.getFormasPagamento()))
                .add(linkToCidade(restaurante.getEndereco().getCidade().getId()))
                .add(linkToRestauranteResponsaveis(restaurante.getId()))
                .addIf(restaurante.isFechado(), () -> linkToRestauranteAberto(restaurante.getId()))
                .addIf(restaurante.isAberto(), () -> linkToRestauranteFechado(restaurante.getId()))
                .addIf(restaurante.isInativo(), () -> linkToRestauranteAtivado(restaurante.getId()))
                .addIf(restaurante.isAtivo(), () -> linkToRestauranteInativado(restaurante.getId()))
                .add(linkToRestaurantes());
    }

    public RestauranteResponsaveisOutput toModelResponsaveis(Restaurante restaurante) {
        return this.mapper.map(restaurante, RestauranteResponsaveisOutput.class)
                .add(linkToRestaurante(restaurante.getId()))
                .add(linkToRestauranteResponsaveis(restaurante.getId()))
                .add(linkToRestaurantes())
                .add(linkToUsuarios(restaurante.getUsuariosResponsaveis().stream().toList()));
    }

    public RestauranteFormasPagamentoOutput toModelFormasPagamento(Restaurante restaurante) {
        return this.mapper.map(restaurante, RestauranteFormasPagamentoOutput.class)
                .add(linkToRestaurante(restaurante.getId()))
                .add(linkToFormasPagamento(restaurante.getFormasPagamento()))
                .add(linkToRestauranteFormasPagamento(restaurante.getId()));
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