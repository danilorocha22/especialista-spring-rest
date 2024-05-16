package com.dan.esr.api.controllers;

import com.dan.esr.api.assemblers.RestauranteAssembler;
import com.dan.esr.api.models.output.RestauranteOutput;
import com.dan.esr.api.models.output.RestauranteProdutosOutput;
import com.dan.esr.api.models.output.RestauranteSummaryOutput;
import com.dan.esr.domain.entities.Restaurante;
import com.dan.esr.domain.services.RestauranteService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

import static com.dan.esr.core.util.ValidacaoCampoObrigatorioUtil.validarCampoObrigatorio;

@AllArgsConstructor
@RestController
@RequestMapping("/restaurantes")
public class RestauranteQueriesController {
    private RestauranteService restauranteService;
    private RestauranteAssembler restauranteAssembler;

    @GetMapping("/{id}")
    public RestauranteOutput buscarPorId(@PathVariable Long id) {
        validarCampoObrigatorio(id, "ID");
        Restaurante restauranteRegistro = this.restauranteService.buscarPorId(id);
        return this.restauranteAssembler.toModel(restauranteRegistro);
    }


    @GetMapping("/com-produtos/{id}")
    public RestauranteProdutosOutput buscarComProdutos(@PathVariable Long id) {
        validarCampoObrigatorio(id, "ID");
        Restaurante restauranteRegistro = this.restauranteService.buscarComProdutos(id);
        return this.restauranteAssembler.toModelProdutos(restauranteRegistro);
    }

    @GetMapping
    public List<RestauranteSummaryOutput> listarTodos() {
        return this.restauranteAssembler.toCollectionModel(this.restauranteService.buscarTodos());
                /*.stream()
                .sorted(comparingLong(RestauranteOutput::getId))
                .toList();*/
    }

    @GetMapping("/por-taxa")
    public List<RestauranteSummaryOutput> buscarValorFreteEntre(BigDecimal taxaInicial, BigDecimal taxaFinal) {
        validarCampoObrigatorio(taxaInicial, "Taxa Inicial");
        validarCampoObrigatorio(taxaFinal, "Taxa Final");
        List<Restaurante> restaurantes = this.restauranteService.buscarFreteEntre(taxaInicial, taxaFinal);
        return this.restauranteAssembler.toCollectionModel(restaurantes);
    }

    @GetMapping("/por-nome-e-cozinha")
    public List<RestauranteSummaryOutput> buscarNomeContendoEcozinha(String nome, Long cozinhaId) {
        validarCampoObrigatorio(nome, "Nome");
        validarCampoObrigatorio(cozinhaId, "ID da cozinha");
        List<Restaurante> restaurantes = this.restauranteService.buscarNomeContendoEcozinhaId(nome, cozinhaId);
        return this.restauranteAssembler.toCollectionModel(restaurantes);
    }

    @GetMapping("/por-nome-e-frete")
    public List<RestauranteSummaryOutput> buscarNomeContendoOuFreteEntre(
            String nome,
            BigDecimal freteInicial,
            BigDecimal freteFinal
    ) {
        List<Restaurante> restaurantes =
                this.restauranteService.buscarNomeContendoOuFreteEntre(nome, freteInicial, freteFinal);

        return this.restauranteAssembler.toCollectionModel(restaurantes);
    }

    @GetMapping("/primeiro-por-nome")
    public RestauranteOutput buscarPrimeiroNomeContendo(String nome) {
        validarCampoObrigatorio(nome, "Nome");
        Restaurante restaurante = this.restauranteService.buscarPrimeiroNomeContendo(nome);
        return this.restauranteAssembler.toModel(restaurante);
    }

    @GetMapping("/top2-por-nome")
    public List<RestauranteSummaryOutput> buscarTop2NomeContendo(String nome) {
        validarCampoObrigatorio(nome, "Nome");
        List<Restaurante> restaurantes = this.restauranteService.buscarTop2NomeContendo(nome);
        return this.restauranteAssembler.toCollectionModel(restaurantes);
    }

    @GetMapping("/count-por-cozinha")
    public int quantidadePorCozinha(Long cozinhaId) {
        validarCampoObrigatorio(cozinhaId, "ID da cozinha");
        return this.restauranteService.contarPorCozinhaId(cozinhaId);
    }

    @GetMapping("/com-frete-gratis")
    public List<RestauranteSummaryOutput> buscarNomeContendoEfreteGratis(String nome) {
        validarCampoObrigatorio(nome, "Nome");
        List<Restaurante> restaurantes = this.restauranteService.buscarNomeContendoEfreteGratis(nome);
        return this.restauranteAssembler.toCollectionModel(restaurantes);
    }

    @GetMapping("/com-nome-semelhante")
    public List<RestauranteSummaryOutput> buscarNomeContendo(String nome) {
        validarCampoObrigatorio(nome, "Nome");
        List<Restaurante> restaurantes = this.restauranteService.buscarNomeContendo(nome);
        return this.restauranteAssembler.toCollectionModel(restaurantes);
    }

    @GetMapping("/com-nome-igual")
    public RestauranteOutput buscarPorNomeIgual(String nome) {
        validarCampoObrigatorio(nome, "Nome");
        Restaurante restaurante = this.restauranteService.buscarPorNomeIgual(nome);
        return this.restauranteAssembler.toModel(restaurante);
    }

    @GetMapping("/primeiro")
    public RestauranteOutput buscarPrimeiroRestaurante() {
        Restaurante restaurante = this.restauranteService.buscarPrimeiroRestaurante();
        return this.restauranteAssembler.toModel(restaurante);
    }
}