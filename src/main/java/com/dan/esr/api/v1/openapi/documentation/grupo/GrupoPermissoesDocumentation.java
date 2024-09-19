package com.dan.esr.api.v1.openapi.documentation.grupo;

import com.dan.esr.api.exceptionhandler.Problem;
import com.dan.esr.api.v1.models.output.grupo.GrupoPermissoesOutput;
import io.swagger.annotations.*;

@Api(tags = "Grupos")
public interface GrupoPermissoesDocumentation {

    @ApiOperation("Lista as permissões de um grupo")
    @ApiResponses({
            @ApiResponse(code = 400, message = "ID da permissão do grupo inválido", response = Problem.class),
            @ApiResponse(code = 404, message = "Permissão de grupo não encontrada", response = Problem.class)
    })
    GrupoPermissoesOutput permissoesDoGrupo(@ApiParam(value = "ID de uma permissão de grupo", example = "1") Long id);

    @ApiOperation("Adiciona uma permissão a um grupo")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Permissão adicionada ao grupo"),
            @ApiResponse(code = 404, message = "Grupo ou permissão não encontrado", response = Problem.class),
    })
    void adicionarPermissao(
            @ApiParam(value = "ID do grupo", example = "1")
            Long grupoId,
            @ApiParam(value = "ID da permissão", example = "1")
            Long permissaoId
    );

    @ApiOperation("Remove uma permissão do grupo")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Permissão removida do grupo"),
            @ApiResponse(code = 404, message = "Grupo ou permissão não encontrado", response = Problem.class),
    })
    void removerPermissao(
            @ApiParam(value = "ID do grupo", example = "1")
            Long grupoId,
            @ApiParam(value = "ID da permissão", example = "1")
            Long permissaoId
    );
}