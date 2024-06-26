package com.dan.esr.api.controllers.restaurante;

import com.dan.esr.api.models.output.restaurante.RestauranteOutput;
import com.dan.esr.api.models.output.view.RestauranteView;
import com.dan.esr.api.openapi.documentation.restaurante.RestaurantePesquisaDocumentation;
import com.dan.esr.core.assemblers.RestauranteModelAssembler;
import com.dan.esr.domain.entities.Restaurante;
import com.dan.esr.domain.services.restaurante.RestauranteConsultaService;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.RequiredArgsConstructor;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/restaurantes", produces = APPLICATION_JSON_VALUE)
public class RestaurantePesquisaController implements RestaurantePesquisaDocumentation {
    private final RestauranteConsultaService restauranteConsulta;
    private final RestauranteModelAssembler restauranteModelAssembler;

    @Override
    @GetMapping("/{id}")
    public RestauranteOutput restaurante(@PathVariable Long id) {
        Restaurante restaurante = this.restauranteConsulta.buscarPor(id);
        return this.restauranteModelAssembler.toModel(restaurante);
    }

   /* @GetMapping
    @JsonView(RestauranteView.Resumo.class)
    public ResponseEntity<List<RestauranteOutput>> restaurantes() {
        List<RestauranteOutput> collectionModel = this.restauranteModelAssembler.toCollectionModel(
                this.restauranteConsulta.buscarTodos());

        return ResponseEntity.ok()
                .header(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*")
                .body(collectionModel);
    }

    @GetMapping(params = "projecao=completo")
    public List<RestauranteOutput> todosCompleto() {
        return this.restauranteModelAssembler.toCollectionModel(this.restauranteConsulta.buscarTodos());
    }*/

    @Override
    @GetMapping
    public MappingJacksonValue restaurante(@RequestParam(required = false) String projecao) {
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

    @Override
    @GetMapping("/por-taxa")
    @JsonView(RestauranteView.Resumo.class)
    public List<RestauranteOutput> restaurantesComTaxaFreteEntre(BigDecimal taxaInicial, BigDecimal taxaFinal) {
        List<Restaurante> restaurantes = this.restauranteConsulta.buscarFreteEntre(taxaInicial, taxaFinal);
        return this.restauranteModelAssembler.toCollectionModel(restaurantes);
    }

    @Override
    @GetMapping("/por-nome-e-cozinha")
    @JsonView(RestauranteView.Resumo.class)
    public List<RestauranteOutput> restaurantesComNomeSemelhanteEcozinha(String nome, Long cozinhaId) {
        List<Restaurante> restaurantes = this.restauranteConsulta.buscarNomeContendoEcozinhaId(nome, cozinhaId);
        return this.restauranteModelAssembler.toCollectionModel(restaurantes);
    }

    @Override
    @GetMapping("/por-nome-e-frete")
    @JsonView(RestauranteView.Resumo.class)
    public List<RestauranteOutput> restauranteComNomeSemelhanteOuTaxaFreteEntre(
            String nome,
            BigDecimal freteInicial,
            BigDecimal freteFinal
    ) {
        List<Restaurante> restaurantes =
                this.restauranteConsulta.buscarNomeContendoOuFreteEntre(nome, freteInicial, freteFinal);

        return this.restauranteModelAssembler.toCollectionModel(restaurantes);
    }

    @Override
    @GetMapping("/primeiro-por-nome")
    public RestauranteOutput primeiroRestauranteComNomeSemelhante(String nome) {
        Restaurante restaurante = this.restauranteConsulta.buscarPrimeiroNomeContendo(nome);
        return this.restauranteModelAssembler.toModel(restaurante);
    }

    @Override
    @GetMapping("/top2-por-nome")
    @JsonView(RestauranteView.Resumo.class)
    public List<RestauranteOutput> restaurantesTop2ComNomeSemelhante(String nome) {
        List<Restaurante> restaurantes = this.restauranteConsulta.buscarTop2NomeContendo(nome);
        return this.restauranteModelAssembler.toCollectionModel(restaurantes);
    }

    @Override
    @GetMapping("/count-por-cozinha")
    public int quantidadePorCozinha(Long cozinhaId) {
        return this.restauranteConsulta.contarPorCozinhaId(cozinhaId);
    }

    @Override
    @GetMapping("/com-frete-gratis")
    @JsonView(RestauranteView.Resumo.class)
    public List<RestauranteOutput> restaurantesComNomeSemelhanteEfreteGratis(String nome) {
        List<Restaurante> restaurantes = this.restauranteConsulta.buscarNomeContendoEfreteGratis(nome);
        return this.restauranteModelAssembler.toCollectionModel(restaurantes);
    }

    @Override
    @GetMapping("/com-nome-semelhante")
    @JsonView(RestauranteView.Resumo.class)
    public List<RestauranteOutput> restaurantesComNomSemelhante(String nome) {
        List<Restaurante> restaurantes = this.restauranteConsulta.buscarNomeContendo(nome);
        return this.restauranteModelAssembler.toCollectionModel(restaurantes);
    }

    @Override
    @GetMapping("/com-nome-igual")
    public RestauranteOutput restauranteComNomeIgual(String nome) {
        Restaurante restaurante = this.restauranteConsulta.buscarPorNomeIgual(nome);
        return this.restauranteModelAssembler.toModel(restaurante);
    }

    @Override
    @GetMapping("/primeiro")
    public RestauranteOutput buscarPrimeiroRestaurante() {
        Restaurante restaurante = this.restauranteConsulta.buscarPrimeiroRestaurante();
        return this.restauranteModelAssembler.toModel(restaurante);
    }
}