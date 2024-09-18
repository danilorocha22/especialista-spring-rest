package com.dan.esr.api.v1.controllers.restaurante;

import com.dan.esr.api.v1.models.output.restaurante.RestauranteFormasPagamentoOutput;
import com.dan.esr.api.v1.openapi.documentation.restaurante.RestauranteFormaPagamentoDocumentation;
import com.dan.esr.api.v1.assemblers.RestauranteModelAssembler;
import com.dan.esr.core.security.CheckSecurity;
import com.dan.esr.domain.entities.Restaurante;
import com.dan.esr.domain.services.restaurante.RestauranteConsultaService;
import com.dan.esr.domain.services.restaurante.RestauranteFormaPagamentoService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import static com.dan.esr.api.v1.links.Links.linkToRestauranteAdicionarFormaPagamento;
import static com.dan.esr.api.v1.links.Links.linkToRestauranteRemoverFormaPagamento;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1/restaurantes/{restauranteId}/formas-pagamento", produces = APPLICATION_JSON_VALUE)
public class RestauranteFormaPagamentoController implements RestauranteFormaPagamentoDocumentation {
    private final RestauranteFormaPagamentoService restauranteFormaPagamentoService;
    private final RestauranteConsultaService restauranteConsulta;
    private final RestauranteModelAssembler restauranteModelAssembler;

    @Override
    @PutMapping("/{formasPagamentoId}")
    @CheckSecurity.Restaurantes.PodeGerenciarFuncionamento
    //@JsonView(RestauranteView.FormaPagamento.class)
    public EntityModel<RestauranteFormasPagamentoOutput> adicionarFormaPagamento(
            @PathVariable Long restauranteId,
            @PathVariable Long formasPagamentoId
    ) {
        Restaurante restaurante = this.restauranteFormaPagamentoService.adicionarFormaPagamento(
                restauranteId, formasPagamentoId);
        return EntityModel.of(
                this.restauranteModelAssembler.toModelFormasPagamento(restaurante)
        );
    }

    @Override
    @DeleteMapping("/{formaPagamentoId}")
    @CheckSecurity.Restaurantes.PodeGerenciarFuncionamento
    //@JsonView(RestauranteView.FormaPagamento.class)
    public EntityModel<RestauranteFormasPagamentoOutput> removerFormaPagamento(
            @PathVariable Long restauranteId,
            @PathVariable Long formaPagamentoId
    ) {
        Restaurante restaurante = restauranteFormaPagamentoService.removerFormaPagamento(restauranteId, formaPagamentoId);
        return EntityModel.of(
                this.restauranteModelAssembler.toModelFormasPagamento(restaurante)
                        .add(linkToRestauranteRemoverFormaPagamento(restauranteId, formaPagamentoId))
        );
    }

    @Override
    @GetMapping
    @CheckSecurity.Restaurantes.PodeConsultar
    //@JsonView(RestauranteView.FormaPagamento.class)
    public EntityModel<RestauranteFormasPagamentoOutput> listarFormasPagamento(@PathVariable Long restauranteId) {
        Restaurante restaurante = this.restauranteConsulta.buscarPor(restauranteId);
        return EntityModel.of(
                this.restauranteModelAssembler.toModelFormasPagamento(restaurante)
                        .add(linkToRestauranteAdicionarFormaPagamento(restauranteId))
        );
    }
}