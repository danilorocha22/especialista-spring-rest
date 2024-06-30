package com.dan.esr.api.openapi.documentation.pedido;

import com.dan.esr.api.exceptionhandler.Problem;
import com.dan.esr.api.models.input.pedido.PedidoInput;
import com.dan.esr.api.models.output.pedido.PedidoOutput;
import com.dan.esr.api.models.output.pedido.PedidoStatusOutput;
import io.swagger.annotations.*;

@Api(tags = "Pedidos")
public interface PedidoGerenciamentoDocumentation {

    @ApiOperation("Registra um pedido")
    @ApiResponses(@ApiResponse(code = 201, message = "Pedido registrado"))
    PedidoOutput novoPedido(
            @ApiParam(name = "corpo", value = "Representação de um novo pedido")
            PedidoInput pedidoInput
    );

    @ApiOperation("Confirma o recebimento de um pedido pelo código")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Pedido confirmado", response = Problem.class),
            @ApiResponse(code = 404, message = "Pedido não encontrado", response = Problem.class)
    })
    PedidoStatusOutput confirmacao(
            @ApiParam(value = "Código de um pedido", example = "3b75fd6e-4a14-4721-8b19-b563c725302e", required = true)
            String codigoPedido
    );

    @ApiOperation("Confirma a entrega de um pedido pelo código")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Pedido entregue", response = Problem.class),
            @ApiResponse(code = 404, message = "Pedido não encontrado", response = Problem.class)
    })
    PedidoStatusOutput entrega(
            @ApiParam(value = "Código de um pedido", example = "3b75fd6e-4a14-4721-8b19-b563c725302e", required = true)
            String codigoPedido
    );

    @ApiOperation("Confirma o cancelamento de um pedido pelo código")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Pedido cancelado", response = Problem.class),
            @ApiResponse(code = 404, message = "Pedido não encontrado", response = Problem.class)
    })
    PedidoStatusOutput cancelamento(
            @ApiParam(value = "Código de um pedido", example = "3b75fd6e-4a14-4721-8b19-b563c725302e", required = true)
            String codigoPedido
    );
}