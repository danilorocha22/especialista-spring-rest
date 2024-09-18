package com.dan.esr.api.v1.controllers.restaurante;

import com.dan.esr.api.v1.models.output.restaurante.RestauranteOutput;
import com.dan.esr.api.v1.models.output.view.RestauranteView;
import com.dan.esr.api.v1.openapi.documentation.restaurante.RestauranteAtivacaoDocumentation;
import com.dan.esr.api.v1.assemblers.RestauranteModelAssembler;
import com.dan.esr.core.security.CheckSecurity;
import com.dan.esr.domain.entities.Restaurante;
import com.dan.esr.domain.services.restaurante.RestauranteAtivacaoService;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/restaurantes")
public class RestauranteAtivacaoController implements RestauranteAtivacaoDocumentation {
    private final RestauranteAtivacaoService ativacaoService;
    private final RestauranteModelAssembler restauranteModelAssembler;

    @Override
    @CheckSecurity.Restaurantes.PodeGerenciarCadastro
    @JsonView(RestauranteView.Status.class)
    @PutMapping(path = "/{id}/ativo", produces = APPLICATION_JSON_VALUE)
    public EntityModel<RestauranteOutput> ativacao(@PathVariable Long id) {
        Restaurante restauranteAtivado = this.ativacaoService.ativar(id);
        return EntityModel.of(
                restauranteModelAssembler.toModel(restauranteAtivado)
        );
    }

    @Override
    @CheckSecurity.Restaurantes.PodeGerenciarCadastro
    @JsonView(RestauranteView.Status.class)
    @DeleteMapping(path = "/{id}/ativo", produces = APPLICATION_JSON_VALUE)
    public EntityModel<RestauranteOutput> inativacao(@PathVariable Long id) {
        Restaurante restauranteDesativado = this.ativacaoService.inativar(id);
        return EntityModel.of(
                restauranteModelAssembler.toModel(restauranteDesativado)
        );
    }

    @Override
    @ResponseStatus(NO_CONTENT)
    @PutMapping("/ativacoes")
    @CheckSecurity.Restaurantes.PodeGerenciarCadastro
    public void ativacoes(@RequestBody List<Long> ids) {
        this.ativacaoService.ativacoes(ids);
    }

    @Override
    @ResponseStatus(NO_CONTENT)
    @DeleteMapping("/ativacoes")
    @CheckSecurity.Restaurantes.PodeGerenciarCadastro
    public void desativacoes(@RequestBody List<Long> ids) {
        this.ativacaoService.desativacoes(ids);
    }

    @Override
    @ResponseStatus(NO_CONTENT)
    @JsonView({RestauranteView.Aberto.class})
    @CheckSecurity.Restaurantes.PodeGerenciarFuncionamento
    @PutMapping(path = "/{id}/abertura", produces = APPLICATION_JSON_VALUE)
    public EntityModel<RestauranteOutput> abertura(@PathVariable Long id) {
        Restaurante restaurante = this.ativacaoService.abrir(id);
        return EntityModel.of(
                this.restauranteModelAssembler.toModel(restaurante)
        );
    }

    @Override
    @ResponseStatus(NO_CONTENT)
    @JsonView({RestauranteView.Aberto.class})
    @CheckSecurity.Restaurantes.PodeGerenciarFuncionamento
    @PutMapping(path = "/{id}/fechamento", produces = APPLICATION_JSON_VALUE)
    public EntityModel<RestauranteOutput> fechamento(@PathVariable Long id) {
        Restaurante restaurante = this.ativacaoService.fechar(id);
        return EntityModel.of(
                this.restauranteModelAssembler.toModel(restaurante)
        );
    }
}