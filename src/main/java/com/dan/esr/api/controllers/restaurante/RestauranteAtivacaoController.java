package com.dan.esr.api.controllers.restaurante;

import com.dan.esr.api.models.output.restaurante.RestauranteOutput;
import com.dan.esr.api.models.output.view.RestauranteView;
import com.dan.esr.api.openapi.documentation.restaurante.RestauranteAtivacaoDocumentation;
import com.dan.esr.core.assemblers.RestauranteModelAssembler;
import com.dan.esr.domain.entities.Restaurante;
import com.dan.esr.domain.services.restaurante.RestauranteAtivacaoService;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping("/restaurantes")
public class RestauranteAtivacaoController implements RestauranteAtivacaoDocumentation {
    private final RestauranteAtivacaoService ativacaoService;
    private final RestauranteModelAssembler restauranteModelAssembler;

    @Override
    @JsonView(RestauranteView.Status.class)
    @PutMapping(path = "/{id}/ativo", produces = APPLICATION_JSON_VALUE)
    public RestauranteOutput ativo(@PathVariable Long id) {
        Restaurante restauranteAtivado = this.ativacaoService.ativar(id);
        return restauranteModelAssembler.toModel(restauranteAtivado);
    }

    @Override
    @JsonView(RestauranteView.Status.class)
    @DeleteMapping(path = "/{id}/ativo", produces = APPLICATION_JSON_VALUE)
    public RestauranteOutput inativo(@PathVariable Long id) {
        Restaurante restauranteDesativado = this.ativacaoService.inativar(id);
        return restauranteModelAssembler.toModel(restauranteDesativado);
    }

    @Override
    @JsonView({RestauranteView.Aberto.class})
    @PutMapping(path = "/{id}/abertura", produces = APPLICATION_JSON_VALUE)
    public RestauranteOutput abertura(@PathVariable Long id) {
        Restaurante restaurante = this.ativacaoService.abrir(id);
        return this.restauranteModelAssembler.toModel(restaurante);
    }

    @Override
    @JsonView({RestauranteView.Aberto.class})
    @PutMapping(path = "/{id}/fechamento", produces = APPLICATION_JSON_VALUE)
    public RestauranteOutput fechamento(@PathVariable Long id) {
        Restaurante restaurante = this.ativacaoService.fechar(id);
        return this.restauranteModelAssembler.toModel(restaurante);
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