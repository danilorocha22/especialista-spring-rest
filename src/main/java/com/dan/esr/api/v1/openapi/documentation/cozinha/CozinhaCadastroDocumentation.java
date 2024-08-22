package com.dan.esr.api.v1.openapi.documentation.cozinha;

import com.dan.esr.api.exceptionhandler.Problem;
import com.dan.esr.api.v1.models.input.cozinha.CozinhaInput;
import com.dan.esr.api.v1.models.output.cozinha.CozinhaOutput;
import io.swagger.annotations.*;

@Api(tags = "Cozinhas")
public interface CozinhaCadastroDocumentation {

    @ApiOperation("Cadastra uma cozinha")
    @ApiResponses(@ApiResponse(code = 200, message = "Cozinha cadastrada"))
    CozinhaOutput novaCozinha(
            @ApiParam(name = "corpo", value = "Representação de uma nova cozinha")
            CozinhaInput cozinhaInput
    );

    @ApiOperation("Atualiza uma cozinha pelo ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Cozinha atualizada", response = Problem.class),
            @ApiResponse(code = 404, message = "Cozinha não encontrada", response = Problem.class)
    })
    CozinhaOutput atualizarCidade(
            @ApiParam(value = "ID de uma cozinha", example = "1")
            Long id,
            @ApiParam(name = "corpo", value = "Representação de uma cozinha com novos dados")
            CozinhaInput cozinhaInput);

    @ApiOperation("Exclui uma cozinha pelo ID")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Cozinha excluída", response = Problem.class),
            @ApiResponse(code = 404, message = "Cozinha não encontrada", response = Problem.class)
    })
    void excluirCozinha(Long id);
}
