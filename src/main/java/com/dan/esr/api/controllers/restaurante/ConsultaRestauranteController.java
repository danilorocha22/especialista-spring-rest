package com.dan.esr.api.controllers.restaurante;

import com.dan.esr.api.models.output.RestauranteOutput;
import com.dan.esr.api.models.output.RestauranteProdutosOutput;
import com.dan.esr.api.models.output.RestauranteSummaryOutput;
import com.dan.esr.core.assemblers.RestauranteModelAssembler;
import com.dan.esr.domain.entities.Restaurante;
import com.dan.esr.domain.services.restaurante.RestauranteConsultaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

import static com.dan.esr.core.util.ValidacaoCampoObrigatorioUtil.validarCampoObrigatorio;

@RequiredArgsConstructor
@RestController
@RequestMapping("/restaurantes")
public class ConsultaRestauranteController {
    private final RestauranteConsultaService restauranteConsulta;
    private final RestauranteModelAssembler restauranteModelAssembler;

    @GetMapping("/{id}")
    public RestauranteOutput buscarPorId(@PathVariable Long id) {
        validarCampoObrigatorio(id, "ID");
        Restaurante restauranteRegistro = this.restauranteConsulta.buscarPorId(id);
        return this.restauranteModelAssembler.toModel(restauranteRegistro);
    }

    @GetMapping("/{id}/produtos")
    public RestauranteProdutosOutput buscarComProdutos(@PathVariable Long id) {
        Restaurante restauranteRegistro = this.restauranteConsulta.buscarComProdutos(id);
        return this.restauranteModelAssembler.toModelProdutos(restauranteRegistro);
    }

    @GetMapping
    public List<RestauranteSummaryOutput> listarTodos() {
        return this.restauranteModelAssembler.toCollectionModel(this.restauranteConsulta.buscarTodos());
    }

    @GetMapping("/por-taxa")
    public List<RestauranteSummaryOutput> buscarValorFreteEntre(BigDecimal taxaInicial, BigDecimal taxaFinal) {
        validarCampoObrigatorio(taxaInicial, "Taxa Inicial");
        validarCampoObrigatorio(taxaFinal, "Taxa Final");
        List<Restaurante> restaurantes = this.restauranteConsulta.buscarFreteEntre(taxaInicial, taxaFinal);
        return this.restauranteModelAssembler.toCollectionModel(restaurantes);
    }

    @GetMapping("/por-nome-e-cozinha")
    public List<RestauranteSummaryOutput> buscarNomeContendoEcozinha(String nome, Long cozinhaId) {
        validarCampoObrigatorio(nome, "Nome");
        validarCampoObrigatorio(cozinhaId, "ID da cozinha");
        List<Restaurante> restaurantes = this.restauranteConsulta.buscarNomeContendoEcozinhaId(nome, cozinhaId);
        return this.restauranteModelAssembler.toCollectionModel(restaurantes);
    }

    @GetMapping("/por-nome-e-frete")
    public List<RestauranteSummaryOutput> buscarNomeContendoOuFreteEntre(
            String nome,
            BigDecimal freteInicial,
            BigDecimal freteFinal
    ) {
        List<Restaurante> restaurantes =
                this.restauranteConsulta.buscarNomeContendoOuFreteEntre(nome, freteInicial, freteFinal);

        return this.restauranteModelAssembler.toCollectionModel(restaurantes);
    }

    @GetMapping("/primeiro-por-nome")
    public RestauranteOutput buscarPrimeiroNomeContendo(String nome) {
        validarCampoObrigatorio(nome, "Nome");
        Restaurante restaurante = this.restauranteConsulta.buscarPrimeiroNomeContendo(nome);
        return this.restauranteModelAssembler.toModel(restaurante);
    }

    @GetMapping("/top2-por-nome")
    public List<RestauranteSummaryOutput> buscarTop2NomeContendo(String nome) {
        validarCampoObrigatorio(nome, "Nome");
        List<Restaurante> restaurantes = this.restauranteConsulta.buscarTop2NomeContendo(nome);
        return this.restauranteModelAssembler.toCollectionModel(restaurantes);
    }

    @GetMapping("/count-por-cozinha")
    public int quantidadePorCozinha(Long cozinhaId) {
        validarCampoObrigatorio(cozinhaId, "ID da cozinha");
        return this.restauranteConsulta.contarPorCozinhaId(cozinhaId);
    }

    @GetMapping("/com-frete-gratis")
    public List<RestauranteSummaryOutput> buscarNomeContendoEfreteGratis(String nome) {
        validarCampoObrigatorio(nome, "Nome");
        List<Restaurante> restaurantes = this.restauranteConsulta.buscarNomeContendoEfreteGratis(nome);
        return this.restauranteModelAssembler.toCollectionModel(restaurantes);
    }

    @GetMapping("/com-nome-semelhante")
    public List<RestauranteSummaryOutput> buscarNomeContendo(String nome) {
        validarCampoObrigatorio(nome, "Nome");
        List<Restaurante> restaurantes = this.restauranteConsulta.buscarNomeContendo(nome);
        return this.restauranteModelAssembler.toCollectionModel(restaurantes);
    }

    @GetMapping("/com-nome-igual")
    public RestauranteOutput buscarPorNomeIgual(String nome) {
        validarCampoObrigatorio(nome, "Nome");
        Restaurante restaurante = this.restauranteConsulta.buscarPorNomeIgual(nome);
        return this.restauranteModelAssembler.toModel(restaurante);
    }

    @GetMapping("/primeiro")
    public RestauranteOutput buscarPrimeiroRestaurante() {
        Restaurante restaurante = this.restauranteConsulta.buscarPrimeiroRestaurante();
        return this.restauranteModelAssembler.toModel(restaurante);
    }
}