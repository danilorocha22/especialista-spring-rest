package com.dan.esr.api.v1.openapi.documentation.usuario;

import com.dan.esr.api.exceptionhandler.Problem;
import io.swagger.annotations.*;

@Api(tags = "Usuários")
public interface UsuarioGruposDocumentation {

    @ApiOperation("Adiciona usuário ao grupo")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Usuário adicionado ao grupo", response = Problem.class),
            @ApiResponse(code = 404, message = "Usuário ou grupo não encontrado", response = Problem.class)
    })
    void adicionarGrupo(
            @ApiParam(value = "ID do usuário", example = "1", required = true)
            Long usuarioId,
            @ApiParam(value = "ID do grupo", example = "1", required = true)
            Long grupoId
    );

    @ApiOperation("Remove usuário do grupo")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Usuário removido do grupo", response = Problem.class),
            @ApiResponse(code = 404, message = "Usuário ou grupo não encontrado", response = Problem.class)
    })
    void removerGrupo(
            @ApiParam(value = "ID do usuário", example = "1", required = true)
            Long usuarioId,
            @ApiParam(value = "ID do grupo", example = "1", required = true)
            Long grupoId
    );
}