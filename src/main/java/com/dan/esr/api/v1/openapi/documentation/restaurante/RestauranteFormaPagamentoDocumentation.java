package com.dan.esr.api.v1.openapi.documentation.restaurante;

import com.dan.esr.api.exceptionhandler.Problem;
import com.dan.esr.api.v1.models.output.restaurante.RestauranteFormasPagamentoOutput;
import io.swagger.annotations.*;
import org.springframework.hateoas.EntityModel;

@Api(tags = "Restaurantes")
public interface RestauranteFormaPagamentoDocumentation {

    @ApiOperation("Adiciona forma de pagamento a um restaurante")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Forma de pagamento adicionada", response = Problem.class),
            @ApiResponse(code = 400, message = "ID do restaurante ou da forma de pagamento inválido", response = Problem.class),
            @ApiResponse(code = 404, message = "Restaurante ou forma de pagamento não encontrada", response = Problem.class)
    })
    EntityModel<RestauranteFormasPagamentoOutput> adicionarFormaPagamento(
            @ApiParam(value = "ID de um restaurante", example = "1", required = true)
            Long restauranteId,
            @ApiParam(value = "ID de uma forma de pagamento", example = "1", required = true)
            Long formasPagamentoId
    );

    @ApiOperation("Exclui forma de pagamento de um restaurante")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Forma de pagamento removida", response = Problem.class),
            @ApiResponse(code = 400, message = "ID do restaurante ou da forma de pagamento inválido", response = Problem.class),
            @ApiResponse(code = 404, message = "Restaurante ou forma de pagamento não encontrada", response = Problem.class)
    })
    EntityModel<RestauranteFormasPagamentoOutput> removerFormaPagamento(
            @ApiParam(value = "ID de um restaurante", example = "1", required = true)
            Long restauranteId,
            @ApiParam(value = "ID de uma forma de pagamento", example = "1", required = true)
            Long formaPagamentoId
    );

    @ApiOperation("Lista as formas de pagamento do restaurante por ID")
    @ApiResponses({@ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)})
    EntityModel<RestauranteFormasPagamentoOutput> listarFormasPagamento(
            @ApiParam(value = "ID de um restaurante", example = "1", required = true) Long restauranteId
    );
}