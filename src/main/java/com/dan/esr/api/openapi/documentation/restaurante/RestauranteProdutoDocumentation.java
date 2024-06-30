package com.dan.esr.api.openapi.documentation.restaurante;

import com.dan.esr.api.exceptionhandler.Problem;
import com.dan.esr.api.models.input.produto.ProdutoInput;
import com.dan.esr.api.models.output.produto.ProdutoOutput;
import com.dan.esr.api.models.output.restaurante.RestauranteProdutosOutput;
import io.swagger.annotations.*;

@Api("Produtos")
public interface RestauranteProdutoDocumentation {

    @ApiOperation("Busca um produto pelo ID do restaurante e do produto")
    @ApiResponses({
            @ApiResponse(code = 400, message = "ID inválido", response = Problem.class),
            @ApiResponse(code = 404, message = "Restaurante ou produto não encontrado", response = Problem.class)
    })
    ProdutoOutput produto(
            @ApiParam(value = "ID de um restaurante", example = "1", required = true)
            Long restauranteId,
            @ApiParam(value = "ID de um produto", example = "1", required = true)
            Long produtoId
    );

    @ApiOperation("Lista os produtos do restaurante")
    RestauranteProdutosOutput produtos(
            @ApiParam(value = "Se produto ativo ou não", example = "true")
            Boolean ativos,
            @ApiParam(value = "ID de um restaurante", example = "1", required = true)
            Long restauranteId
    );

    @ApiOperation("Cadastra um produto para um restaurante pelo ID")
    @ApiResponses(@ApiResponse(code = 200, message = "Produto cadastrado"))
    RestauranteProdutosOutput novoProduto(
            @ApiParam(value = "ID de um restaurante", example = "1", required = true)
            Long restauranteId,
            @ApiParam(name = "corpo", value = "Representação de um novo produto")
            ProdutoInput produtoInput
    );

    @ApiOperation("Atualiza um produto pelo seu ID e do restaurante")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Produto atualizado", response = Problem.class),
            @ApiResponse(code = 404, message = "Restaurante ou produto não encontrado", response = Problem.class)
    })
    RestauranteProdutosOutput atualizarProduto(
            @ApiParam(value = "ID de um restaurante", example = "1", required = true)
            Long restauranteId,
            @ApiParam(value = "ID de um produto", example = "1", required = true)
            Long produtoId,
            @ApiParam(name = "corpo", value = "Representação de um produto com novos dados")
            ProdutoInput produtoInput
    );
}