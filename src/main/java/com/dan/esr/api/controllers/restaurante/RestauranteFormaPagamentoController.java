package com.dan.esr.api.controllers.restaurante;

import com.dan.esr.api.models.output.restaurante.RestauranteOutput;
import com.dan.esr.api.models.output.view.RestauranteView;
import com.dan.esr.core.assemblers.RestauranteModelAssembler;
import com.dan.esr.domain.entities.Restaurante;
import com.dan.esr.domain.services.restaurante.RestauranteConsultaService;
import com.dan.esr.domain.services.restaurante.RestauranteFormaPagamentoService;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/restaurantes/{restauranteId}/formas-pagamento")
public class RestauranteFormaPagamentoController {
    private final RestauranteFormaPagamentoService restauranteFormaPagamentoService;
    private final RestauranteConsultaService restauranteConsulta;
    private final RestauranteModelAssembler restauranteModelAssembler;

    @PutMapping("/{formasPagamentoId}")
    @JsonView(RestauranteView.FormaPagamento.class)
    public RestauranteOutput adicionarFormaPagamento(
            @PathVariable Long restauranteId,
            @PathVariable Long formasPagamentoId
    ) {
        Restaurante restaurante = this.restauranteFormaPagamentoService.adicionarFormaPagamento(
                restauranteId, formasPagamentoId);
        return this.restauranteModelAssembler.toModel(restaurante);
    }

    @DeleteMapping("/{formaPagamentoId}")
    @JsonView(RestauranteView.FormaPagamento.class)
    public RestauranteOutput removerFormaPagamento(
            @PathVariable Long restauranteId,
            @PathVariable Long formaPagamentoId
    ) {
        Restaurante restaurante = restauranteFormaPagamentoService.removerFormaPagamento(
                restauranteId,
                formaPagamentoId
        );
        return this.restauranteModelAssembler.toModel(restaurante);
    }


    @GetMapping
    @JsonView(RestauranteView.FormaPagamento.class)
    public RestauranteOutput listar(@PathVariable Long restauranteId) {
        Restaurante restaurante = this.restauranteConsulta.buscarPor(restauranteId);
        return this.restauranteModelAssembler.toModel(restaurante);
    }
}