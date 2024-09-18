package com.dan.esr.api.v1.controllers.restaurante;

import com.dan.esr.api.v1.models.output.restaurante.RestauranteResponsaveisOutput;
import com.dan.esr.api.v1.models.output.usuario.UsuarioOutput;
import com.dan.esr.api.v1.openapi.documentation.restaurante.RestauranteResponsavelDocumentation;
import com.dan.esr.api.v1.assemblers.RestauranteModelAssembler;
import com.dan.esr.api.v1.assemblers.UsuarioAssembler;
import com.dan.esr.core.security.CheckSecurity;
import com.dan.esr.domain.entities.Restaurante;
import com.dan.esr.domain.services.restaurante.RestauranteResponsavelService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import static com.dan.esr.api.v1.links.Links.linkToRestauranteAdicionarResponsavel;
import static com.dan.esr.api.v1.links.Links.linkToRestauranteResponsaveis;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1/restaurantes/{restauranteId}/responsaveis", produces = APPLICATION_JSON_VALUE)
public class RestauranteResponsavelController implements RestauranteResponsavelDocumentation {
    private final RestauranteResponsavelService responsavelService;
    private final RestauranteModelAssembler restauranteModelAssembler;
    private final UsuarioAssembler usuarioAssembler;

    @Override
    @GetMapping
    @CheckSecurity.Restaurantes.PodeConsultar
    public CollectionModel<UsuarioOutput> buscarResponsaveis(@PathVariable Long restauranteId) {
        Restaurante restaurante = this.responsavelService.buscarComResponsaveis(restauranteId);
        return this.usuarioAssembler.toCollectionModel(restaurante.getUsuariosResponsaveis())
                .removeLinks()
                .add(linkToRestauranteResponsaveis(restauranteId))
                .add(linkToRestauranteAdicionarResponsavel(restauranteId));
    }

    @Override
    @PutMapping("/{usuarioId}")
    @CheckSecurity.Restaurantes.PodeGerenciarCadastro
    public EntityModel<RestauranteResponsaveisOutput> adicionarResponsavel(
            @PathVariable Long restauranteId,
            @PathVariable Long usuarioId
    ) {
        Restaurante restaurante = this.responsavelService.adicionarResponsavel(restauranteId, usuarioId);
        return EntityModel.of(
                this.restauranteModelAssembler.toModelResponsaveis(restaurante)
        );
    }

    @Override
    @DeleteMapping("/{usuarioId}")
    @CheckSecurity.Restaurantes.PodeGerenciarCadastro
    public EntityModel<RestauranteResponsaveisOutput> removerResponsavel(
            @PathVariable Long restauranteId,
            @PathVariable Long usuarioId
    ) {
        Restaurante restaurante = this.responsavelService.removerResponsavel(restauranteId, usuarioId);
        return EntityModel.of(
                this.restauranteModelAssembler.toModelResponsaveis(restaurante)
        );
    }
}