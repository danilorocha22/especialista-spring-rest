package com.dan.esr.api.v1.openapi.documentation.restaurante;

import com.dan.esr.api.exceptionhandler.Problem;
import com.dan.esr.api.v1.models.output.restaurante.RestauranteOutput;
import com.dan.esr.api.v1.openapi.models.RestauranteResumoModelOpenApi;
import io.swagger.annotations.*;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.converter.json.MappingJacksonValue;

import java.math.BigDecimal;

@Api(tags = "Restaurantes")
public interface RestaurantePesquisaDocumentation {

    @ApiOperation("Busca um restaurante por ID")
    @ApiResponses(@ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class))
    EntityModel<RestauranteOutput> restaurante(@ApiParam(value = "Código do pedido", example = "1", required = true) Long id);

    @ApiOperation(value = "Lista restaurantes", response = RestauranteResumoModelOpenApi.class)
    @ApiImplicitParams({ @ApiImplicitParam(value = "Nome da projeção de restaurantes",
                    name = "projecao", paramType = "query", dataType = "java.lang.String")})
    MappingJacksonValue restaurante(@ApiParam(value = "Código de um pedido", example = "1") String projecao);

    @ApiOperation("Lista restaurantes por taxa frente inicial e final")
    @ApiResponses(@ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class))
    CollectionModel<RestauranteOutput> restaurantesComTaxaFreteEntre(BigDecimal taxaInicial, BigDecimal taxaFinal);

    @ApiOperation("Lista restaurantes por nome e ID da cozinha")
    @ApiResponses(@ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class))
    CollectionModel<RestauranteOutput> restaurantesComNomeSemelhanteEcozinha(
            @ApiParam(value = "Nome do restaurante", example = "Thai Gourmet", required = true)
            String nome,
            @ApiParam(value = "ID da cozinha", example = "1", required = true)
            Long cozinhaId
    );

    @ApiOperation("Lista restaurantes por nome e taxa frete inicial e final")
    @ApiResponses(@ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class))
    CollectionModel<RestauranteOutput> restauranteComNomeSemelhanteOuTaxaFreteEntre(
            String nome,
            BigDecimal freteInicial,
            BigDecimal freteFinal
    );

    @ApiOperation("Busca restaurante por nome")
    @ApiResponses(@ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class))
    EntityModel<RestauranteOutput> primeiroRestauranteComNomeSemelhante(
            @ApiParam(value = "Nome do restaurante", example = "Tio Brás Restaurantes", required = true)
            String nome
    );

    @ApiOperation("Lista os dois primeiros restaurantes por nome")
    @ApiResponses(@ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class))
    CollectionModel<RestauranteOutput> restaurantesTop2ComNomeSemelhante(
            @ApiParam(value = "Nome do restaurante", example = "Tio Brás", required = true)
            String nome
    );

    @ApiOperation("Quantidade de restaurantes por ID da cozinha")
    int quantidadePorCozinha(
            @ApiParam(value = "ID da cozinha", example = "1", required = true)
            Long cozinhaId
    );

    @ApiOperation("Lista restaurantes por nome e com frete grátis")
    @ApiResponses(@ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class))
    CollectionModel<RestauranteOutput> restaurantesComNomeSemelhanteEfreteGratis(
            @ApiParam(value = "Nome do restaurante", example = "Tio Brás Restaurantes", required = true)
            String nome
    );

    @ApiOperation("Lista restaurantes por nome")
    @ApiResponses(@ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class))
    CollectionModel<RestauranteOutput> restaurantesComNomSemelhante(
            @ApiParam(value = "Nome do restaurante", example = "Tio Brás Restaurantes", required = true)
            String nome
    );

    @ApiOperation("Busca restaurante com nome igual")
    @ApiResponses(@ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class))
    EntityModel<RestauranteOutput> restauranteComNomeIgual(
            @ApiParam(value = "Nome do restaurante", example = "Tio Brás Restaurantes", required = true)
            String nome
    );

    @ApiOperation("Busca primeiro restaurante")
    @ApiResponses(@ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class))
    EntityModel<RestauranteOutput> buscarPrimeiroRestaurante();
}
