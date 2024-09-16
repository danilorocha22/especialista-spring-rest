package com.dan.esr.api.v1.openapi.documentation.pedido;

import com.dan.esr.api.exceptionhandler.Problem;
import com.dan.esr.api.v1.models.output.pedido.PedidoOutput;
import com.dan.esr.domain.filter.PedidoFiltro;
import io.swagger.annotations.*;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

@Api(tags = "Pedidos")
public interface PedidoPesquisaDocumentation {

    @ApiOperation("Busca um pedido por código")
    @ApiResponses(@ApiResponse(code = 404, message = "Pedido não encontrado", response = Problem.class))
    PedidoOutput pedido(
            @ApiParam(value = "Código de um pedido", example = "f9981ca4-5a5e-4da3-af04-933861df3e55", required = true)
            String codigoPedido
    );

    @ApiOperation("Pesquisa páginada dos pedidos")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "Nomes das propriedades para filtrar na resposta, separados por vírgula",
                    name = "campos", paramType = "query", type = "string")
    })
    ResponseEntity<?> pesquisaComplexa(PedidoFiltro filtro, Pageable pageable);

    @ApiOperation("Pesquisa os pedidos")
    @ApiImplicitParams({@ApiImplicitParam(
            name = "campos",
            paramType = "query",
            type = "string",
            value = "Nomes das propriedades para filtrar na resposta, separados por vírgula"
    )})
    ResponseEntity<?> pedidos(String campos);
}