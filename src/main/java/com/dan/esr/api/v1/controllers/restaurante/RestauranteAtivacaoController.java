package com.dan.esr.api.v1.controllers.restaurante;

import com.dan.esr.api.v1.models.output.restaurante.RestauranteOutput;
import com.dan.esr.api.v1.models.output.view.RestauranteView;
import com.dan.esr.api.v1.openapi.documentation.restaurante.RestauranteAtivacaoDocumentation;
import com.dan.esr.api.v1.assemblers.RestauranteModelAssembler;
import com.dan.esr.domain.entities.Restaurante;
import com.dan.esr.domain.services.restaurante.RestauranteAtivacaoService;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/restaurantes")
public class RestauranteAtivacaoController implements RestauranteAtivacaoDocumentation {
    private final RestauranteAtivacaoService ativacaoService;
    private final RestauranteModelAssembler restauranteModelAssembler;

    @Override
    @JsonView(RestauranteView.Status.class)
    @PutMapping(path = "/{id}/ativo", produces = APPLICATION_JSON_VALUE)
    public EntityModel<RestauranteOutput> ativacao(@PathVariable Long id) {
        Restaurante restauranteAtivado = this.ativacaoService.ativar(id);
        return EntityModel.of(
                restauranteModelAssembler.toModel(restauranteAtivado)
        );
    }

    @Override
    @JsonView(RestauranteView.Status.class)
    @DeleteMapping(path = "/{id}/ativo", produces = APPLICATION_JSON_VALUE)
    public EntityModel<RestauranteOutput> inativacao(@PathVariable Long id) {
        Restaurante restauranteDesativado = this.ativacaoService.inativar(id);
        return EntityModel.of(
                restauranteModelAssembler.toModel(restauranteDesativado)
        );
    }

    @Override
    @JsonView({RestauranteView.Aberto.class})
    @PutMapping(path = "/{id}/abertura", produces = APPLICATION_JSON_VALUE)
    public EntityModel<RestauranteOutput> abertura(@PathVariable Long id) {
        Restaurante restaurante = this.ativacaoService.abrir(id);
        return EntityModel.of(
                this.restauranteModelAssembler.toModel(restaurante)
        );
    }

    @Override
    @JsonView({RestauranteView.Aberto.class})
    @PutMapping(path = "/{id}/fechamento", produces = APPLICATION_JSON_VALUE)
    public EntityModel<RestauranteOutput> fechamento(@PathVariable Long id) {
        Restaurante restaurante = this.ativacaoService.fechar(id);
        return EntityModel.of(
                this.restauranteModelAssembler.toModel(restaurante)
        );
    }

    @Override
    @PutMapping("/ativacoes")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void ativacoes(@RequestBody List<Long> ids) {
        this.ativacaoService.ativacoes(ids);
    }

    @Override
    @DeleteMapping("/ativacoes")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desativacoes(@RequestBody List<Long> ids) {
        this.ativacaoService.desativacoes(ids);
    }
}