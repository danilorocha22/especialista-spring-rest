package com.dan.esr.api.openapi.documentation.cidade;

import com.dan.esr.api.exceptionhandler.Problem;
import com.dan.esr.api.models.input.cidade.CidadeInput;
import com.dan.esr.api.models.output.cidade.CidadeOutput;
import io.swagger.annotations.*;

import java.util.List;

@Api(tags = "Cidades")
public interface CidadeDocumentation {

    @ApiOperation("Busca uma cidade pelo ID")
    @ApiResponses({
            @ApiResponse(code = 400, message = "ID da cidade inválido", response = Problem.class),
            @ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class)
    })
    CidadeOutput cidade(@ApiParam(value = "ID de uma cidade", example = "1", required = true) Long id);


    @ApiOperation("Lista as cidades")
    List<CidadeOutput> cidades();

    @ApiOperation("Cadastra uma cidade")
    @ApiResponses(@ApiResponse(code = 200, message = "Cidade cadastrada"))
    CidadeOutput novaCidade(
            @ApiParam(name = "corpo", value = "Representação de uma nova cidade")
            CidadeInput cidadeInput
    );


    @ApiOperation("Atualiza uma cidade pelo ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Cidade atualizada", response = Problem.class),
            @ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class)
    })
    CidadeOutput atualizarCidade(
            @ApiParam(value = "ID de uma cidade", example = "1", required = true)
            Long id,
            @ApiParam(name = "corpo", value = "Representação de uma cidade com novos dados")
            CidadeInput cidadeInput
    );


    @ApiOperation("Exclui uma cidade pelo ID")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Cidade excluída", response = Problem.class),
            @ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class)
    })
    void excluirCidade(@ApiParam(value = "ID de uma cidade", example = "1", required = true) Long id);
}