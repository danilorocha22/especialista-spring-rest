package com.dan.esr.api.controllers.restaurante;

import com.dan.esr.api.models.output.restaurante.RestauranteOutput;
import com.dan.esr.api.models.output.view.RestauranteView;
import com.dan.esr.core.assemblers.RestauranteModelAssembler;
import com.dan.esr.domain.entities.Restaurante;
import com.dan.esr.domain.services.restaurante.RestauranteAtivacaoService;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/restaurantes")
public class RestauranteAtivacaoController {
    private final RestauranteAtivacaoService ativacaoService;
    private final RestauranteModelAssembler restauranteModelAssembler;

    @PutMapping("/{id}/ativo")
    @JsonView(RestauranteView.Status.class)
    public RestauranteOutput ativo(@PathVariable Long id) {
        Restaurante restauranteAtivado = this.ativacaoService.ativar(id);
        return restauranteModelAssembler.toModel(restauranteAtivado);
    }

    @DeleteMapping("/{id}/ativo")
    @JsonView(RestauranteView.Status.class)
    public RestauranteOutput inativo(@PathVariable Long id) {
        Restaurante restauranteDesativado = this.ativacaoService.inativar(id);
        return restauranteModelAssembler.toModel(restauranteDesativado);
    }

    @PutMapping("/{id}/abertura")
    @JsonView({RestauranteView.Aberto.class})
    public RestauranteOutput abertura(@PathVariable Long id) {
        Restaurante restaurante = this.ativacaoService.abrir(id);
        return this.restauranteModelAssembler.toModel(restaurante);
    }

    @PutMapping("/{id}/fechamento")
    @JsonView({RestauranteView.Aberto.class})
    public RestauranteOutput fechamento(@PathVariable Long id) {
        Restaurante restaurante = this.ativacaoService.fechar(id);
        return this.restauranteModelAssembler.toModel(restaurante);
    }

    @PutMapping("/ativacoes")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void ativacoes(@RequestBody List<Long> ids) {
        this.ativacaoService.ativacoes(ids);
    }

    @DeleteMapping("/ativacoes")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desativacoes(@RequestBody List<Long> ids) {
        this.ativacaoService.desativacoes(ids);
    }
}