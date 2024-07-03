package com.dan.esr.api.openapi.documentation.estado;

import com.dan.esr.api.exceptionhandler.Problem;
import com.dan.esr.api.models.input.estado.EstadoInput;
import com.dan.esr.api.models.output.estado.EstadoOutput;
import io.swagger.annotations.*;
import org.springframework.hateoas.EntityModel;

import java.util.List;

@Api("Estados")
public interface EstadoDocumentation {

    @ApiOperation("Busca um estado pelo ID")
    @ApiResponses({
            @ApiResponse(code = 400, message = "ID do estado inválido", response = Problem.class),
            @ApiResponse(code = 404, message = "Estado não encontrado", response = Problem.class)
    })
    EntityModel<EstadoOutput> estado(@ApiParam(value = "ID de um estado", example = "1", required = true) Long id);

    @ApiOperation("Lista os estados")
    List<EstadoOutput> estados();

    @ApiOperation("Cadastra um estado")
    @ApiResponses(@ApiResponse(code = 200, message = "Estado cadastrado"))
    EstadoOutput novoEstado(EstadoInput estado);

    @ApiOperation("Atualiza um estado pelo ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Estado atualizado", response = Problem.class),
            @ApiResponse(code = 404, message = "Estado não encontrado", response = Problem.class)
    })
    EstadoOutput atualizarEstado(
            @ApiParam(value = "ID de um estado", example = "1", required = true)
            Long id,
            @ApiParam(name = "corpo", value = "Representação de um estado com novos dados")
            EstadoInput estado
    );

    @ApiOperation("Exclui um estado pelo ID")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Estado excluído", response = Problem.class),
            @ApiResponse(code = 404, message = "Estado não encontrado", response = Problem.class)
    })
    void excluirEstado(@ApiParam(value = "ID de um estado", example = "1", required = true) Long id);
}