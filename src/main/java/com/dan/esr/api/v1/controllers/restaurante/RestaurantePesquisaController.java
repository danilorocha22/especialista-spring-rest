package com.dan.esr.api.v1.controllers.restaurante;

import com.dan.esr.api.v1.assemblers.RestauranteModelAssembler;
import com.dan.esr.api.v1.models.output.restaurante.RestauranteOutput;
import com.dan.esr.api.v1.models.output.view.RestauranteView;
import com.dan.esr.api.v1.openapi.documentation.restaurante.RestaurantePesquisaDocumentation;
import com.dan.esr.core.security.CheckSecurity;
import com.dan.esr.domain.entities.Restaurante;
import com.dan.esr.domain.services.restaurante.RestauranteConsultaService;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1/restaurantes", produces = APPLICATION_JSON_VALUE)
public class RestaurantePesquisaController implements RestaurantePesquisaDocumentation {
    private final RestauranteConsultaService restauranteConsulta;
    private final RestauranteModelAssembler restauranteModelAssembler;

    @Override
    @GetMapping("/{id}")
    @CheckSecurity.Restaurantes.Consultar
    public EntityModel<RestauranteOutput> restaurante(@PathVariable Long id) {
        Restaurante restaurante = this.restauranteConsulta.buscarPor(id);
        return EntityModel.of(
                this.restauranteModelAssembler.toModel(restaurante)
        );
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

    @GetMapping()
    @CheckSecurity.Restaurantes.Consultar
    @JsonView(RestauranteView.Resumo.class)
    public CollectionModel<RestauranteOutput> restaurantes() {
        List<Restaurante> restaurantes = this.restauranteConsulta.buscarTodos();
        return this.restauranteModelAssembler.toCollectionModel(restaurantes);
    }

    @Override
    @CheckSecurity.Restaurantes.Consultar
    @GetMapping(params = "projecao=resumo")
    public MappingJacksonValue restaurante(@RequestParam(required = false) String projecao) {
        List<Restaurante> restaurantes = this.restauranteConsulta.buscarTodos();
        CollectionModel<RestauranteOutput> collectionModel = restauranteModelAssembler.toCollectionModel(restaurantes);
        MappingJacksonValue restaurantesWrapper = new MappingJacksonValue(collectionModel);
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
    @CheckSecurity.Restaurantes.Consultar
    @JsonView(RestauranteView.Resumo.class)
    public CollectionModel<RestauranteOutput> restaurantesComTaxaFreteEntre(BigDecimal taxaInicial, BigDecimal taxaFinal) {
        List<Restaurante> restaurantes = this.restauranteConsulta.buscarFreteEntre(taxaInicial, taxaFinal);
        return this.restauranteModelAssembler.toCollectionModel(restaurantes);
    }

    @Override
    @GetMapping("/por-nome-e-cozinha")
    @CheckSecurity.Restaurantes.Consultar
    @JsonView(RestauranteView.Resumo.class)
    public CollectionModel<RestauranteOutput> restaurantesComNomeSemelhanteEcozinha(String nome, Long cozinhaId) {
        List<Restaurante> restaurantes = this.restauranteConsulta.buscarNomeContendoEcozinhaId(nome, cozinhaId);
        return this.restauranteModelAssembler.toCollectionModel(restaurantes);
    }

    @Override
    @GetMapping("/por-nome-e-frete")
    @CheckSecurity.Restaurantes.Consultar
    @JsonView(RestauranteView.Resumo.class)
    public CollectionModel<RestauranteOutput> restauranteComNomeSemelhanteOuTaxaFreteEntre(
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
    @CheckSecurity.Restaurantes.Consultar
    public EntityModel<RestauranteOutput> primeiroRestauranteComNomeSemelhante(String nome) {
        Restaurante restaurante = this.restauranteConsulta.buscarPrimeiroNomeContendo(nome);
        return EntityModel.of(
                this.restauranteModelAssembler.toModel(restaurante)
        );
    }

    @Override
    @GetMapping("/top2-por-nome")
    @CheckSecurity.Restaurantes.Consultar
    @JsonView(RestauranteView.Resumo.class)
    public CollectionModel<RestauranteOutput> restaurantesTop2ComNomeSemelhante(String nome) {
        return this.restauranteModelAssembler.toCollectionModel(
                this.restauranteConsulta.buscarTop2NomeContendo(nome)
        );
    }

    @Override
    @GetMapping("/count-por-cozinha")
    @CheckSecurity.Restaurantes.Consultar
    public int quantidadePorCozinha(Long cozinhaId) {
        return this.restauranteConsulta.contarPorCozinhaId(cozinhaId);
    }

    @Override
    @GetMapping("/com-frete-gratis")
    @CheckSecurity.Restaurantes.Consultar
    @JsonView(RestauranteView.Resumo.class)
    public CollectionModel<RestauranteOutput> restaurantesComNomeSemelhanteEfreteGratis(String nome) {
        return this.restauranteModelAssembler.toCollectionModel(
                this.restauranteConsulta.buscarNomeContendoEfreteGratis(nome)
        );
    }

    @Override
    @GetMapping("/com-nome-semelhante")
    @CheckSecurity.Restaurantes.Consultar
    @JsonView(RestauranteView.Resumo.class)
    public CollectionModel<RestauranteOutput> restaurantesComNomSemelhante(String nome) {
        return this.restauranteModelAssembler.toCollectionModel(
                this.restauranteConsulta.buscarNomeContendo(nome)
        );
    }

    @Override
    @GetMapping("/com-nome-igual")
    @CheckSecurity.Restaurantes.Consultar
    public EntityModel<RestauranteOutput> restauranteComNomeIgual(String nome) {
        Restaurante restaurante = this.restauranteConsulta.buscarPorNomeIgual(nome);
        return EntityModel.of(
                this.restauranteModelAssembler.toModel(restaurante)
        );
    }

    @Override
    @GetMapping("/primeiro")
    @CheckSecurity.Restaurantes.Consultar
    public EntityModel<RestauranteOutput> buscarPrimeiroRestaurante() {
        Restaurante restaurante = this.restauranteConsulta.buscarPrimeiroRestaurante();
        return EntityModel.of(
                this.restauranteModelAssembler.toModel(restaurante)
        );
    }
}