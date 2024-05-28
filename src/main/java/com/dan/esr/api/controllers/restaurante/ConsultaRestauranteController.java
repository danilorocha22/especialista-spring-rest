package com.dan.esr.api.controllers.restaurante;

import com.dan.esr.api.models.output.produto.ProdutoOutput;
import com.dan.esr.api.models.output.restaurante.RestauranteOutput;
import com.dan.esr.api.models.output.restaurante.RestauranteProdutosOutput;
import com.dan.esr.api.models.output.restaurante.RestauranteResponsaveisOutput;
import com.dan.esr.api.models.output.view.RestauranteView;
import com.dan.esr.core.assemblers.ProdutoAssembler;
import com.dan.esr.core.assemblers.RestauranteModelAssembler;
import com.dan.esr.domain.entities.Produto;
import com.dan.esr.domain.entities.Restaurante;
import com.dan.esr.domain.services.restaurante.RestauranteConsultaService;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.RequiredArgsConstructor;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/restaurantes")
public class ConsultaRestauranteController {
    private final RestauranteConsultaService restauranteConsulta;
    private final RestauranteModelAssembler restauranteModelAssembler;
    private final ProdutoAssembler produtoAssembler;

    @GetMapping("/{id}")
    public RestauranteOutput buscarPorId(@PathVariable Long id) {
        Restaurante restaurante = this.restauranteConsulta.buscarPor(id);
        return this.restauranteModelAssembler.toModel(restaurante);
    }

    @GetMapping("/{restauranteId}/produtos")
    public RestauranteProdutosOutput buscarProdutos(@PathVariable("restauranteId") Long id) {
        Restaurante restaurante = this.restauranteConsulta.buscarComProdutos(id);
        return this.restauranteModelAssembler.toModelComProdutos(restaurante);
    }

    @GetMapping("/{restauranteId}/responsaveis")
    public RestauranteResponsaveisOutput buscarResponsaveis(@PathVariable("restauranteId") Long id) {
        Restaurante restaurante = this.restauranteConsulta.buscarComResponsaveis(id);
        return this.restauranteModelAssembler.toModelComResponsaveis(restaurante);
    }

    @GetMapping("/{restauranteId}/produtos/{produtoId}")
    public ProdutoOutput buscarProduto(
            @PathVariable Long restauranteId,
            @PathVariable Long produtoId
    ) {
        Restaurante restaurante = this.restauranteConsulta.buscarPor(restauranteId);
        Produto produto = this.restauranteConsulta.buscarProduto(restaurante.getId(), produtoId);
        return this.produtoAssembler.toModel(produto);
    }

    /*@GetMapping
    @JsonView(RestauranteView.Resumo.class)
    public List<RestauranteOutput> todos() {
        return this.restauranteModelAssembler.toCollectionModel(this.restauranteConsulta.buscarTodos());
    }

    @GetMapping(params = "projecao=completo")
    public List<RestauranteOutput> todosCompleto() {
        return this.restauranteModelAssembler.toCollectionModel(this.restauranteConsulta.buscarTodos());
    }*/

    @GetMapping
    public MappingJacksonValue todos(@RequestParam(required = false) String projecao) {
        List<Restaurante> restaurantes = this.restauranteConsulta.buscarTodos();
        List<RestauranteOutput> restaurantesModel = restauranteModelAssembler.toCollectionModel(restaurantes);
        MappingJacksonValue restaurantesWrapper = new MappingJacksonValue(restaurantesModel);
        restaurantesWrapper.setSerializationView(RestauranteView.Resumo.class);

        if ("status".equals(projecao)) {
            restaurantesWrapper.setSerializationView(RestauranteView.Status.class);
        } else if ("completo".equals(projecao)) {
            restaurantesWrapper.setSerializationView(null);
        }

        return restaurantesWrapper;
    }

    @GetMapping("/por-taxa")
    @JsonView(RestauranteView.Resumo.class)
    public List<RestauranteOutput> buscarValorFreteEntre(BigDecimal taxaInicial, BigDecimal taxaFinal) {
        List<Restaurante> restaurantes = this.restauranteConsulta.buscarFreteEntre(taxaInicial, taxaFinal);
        return this.restauranteModelAssembler.toCollectionModel(restaurantes);
    }

    @GetMapping("/por-nome-e-cozinha")
    @JsonView(RestauranteView.Resumo.class)
    public List<RestauranteOutput> buscarNomeContendoEcozinha(String nome, Long cozinhaId) {
        List<Restaurante> restaurantes = this.restauranteConsulta.buscarNomeContendoEcozinhaId(nome, cozinhaId);
        return this.restauranteModelAssembler.toCollectionModel(restaurantes);
    }

    @GetMapping("/por-nome-e-frete")
    @JsonView(RestauranteView.Resumo.class)
    public List<RestauranteOutput> buscarNomeContendoOuFreteEntre(
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
        Restaurante restaurante = this.restauranteConsulta.buscarPrimeiroNomeContendo(nome);
        return this.restauranteModelAssembler.toModel(restaurante);
    }

    @GetMapping("/top2-por-nome")
    @JsonView(RestauranteView.Resumo.class)
    public List<RestauranteOutput> buscarTop2NomeContendo(String nome) {
        List<Restaurante> restaurantes = this.restauranteConsulta.buscarTop2NomeContendo(nome);
        return this.restauranteModelAssembler.toCollectionModel(restaurantes);
    }

    @GetMapping("/count-por-cozinha")
    public int quantidadePorCozinha(Long cozinhaId) {
        return this.restauranteConsulta.contarPorCozinhaId(cozinhaId);
    }

    @GetMapping("/com-frete-gratis")
    @JsonView(RestauranteView.Resumo.class)
    public List<RestauranteOutput> buscarNomeContendoEfreteGratis(String nome) {
        List<Restaurante> restaurantes = this.restauranteConsulta.buscarNomeContendoEfreteGratis(nome);
        return this.restauranteModelAssembler.toCollectionModel(restaurantes);
    }

    @GetMapping("/com-nome-semelhante")
    @JsonView(RestauranteView.Resumo.class)
    public List<RestauranteOutput> buscarNomeContendo(String nome) {
        List<Restaurante> restaurantes = this.restauranteConsulta.buscarNomeContendo(nome);
        return this.restauranteModelAssembler.toCollectionModel(restaurantes);
    }

    @GetMapping("/com-nome-igual")
    public RestauranteOutput buscarPorNomeIgual(String nome) {
        Restaurante restaurante = this.restauranteConsulta.buscarPorNomeIgual(nome);
        return this.restauranteModelAssembler.toModel(restaurante);
    }

    @GetMapping("/primeiro")
    public RestauranteOutput buscarPrimeiroRestaurante() {
        Restaurante restaurante = this.restauranteConsulta.buscarPrimeiroRestaurante();
        return this.restauranteModelAssembler.toModel(restaurante);
    }
}