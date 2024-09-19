package com.dan.esr.api.v1.openapi.documentation.usuario;

import com.dan.esr.api.exceptionhandler.Problem;
import com.dan.esr.api.v1.models.output.usuario.UsuarioGruposOutput;
import io.swagger.annotations.*;
import org.springframework.hateoas.EntityModel;

@Api(tags = "Usuários")
public interface UsuarioGruposDocumentation {

    @ApiOperation("Adiciona usuário ao grupo")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Usuário adicionado ao grupo", response = Problem.class),
            @ApiResponse(code = 404, message = "Usuário ou grupo não encontrado", response = Problem.class)
    })
    void adicionarUsuarioNoGrupo(
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
    void removerDoGrupo(
            @ApiParam(value = "ID do usuário", example = "1", required = true)
            Long usuarioId,
            @ApiParam(value = "ID do grupo", example = "1", required = true)
            Long grupoId
    );

    @ApiOperation("Lista os grupos do usuário")
    @ApiResponses(@ApiResponse(code = 404, message = "Usuário não encontrado", response = Problem.class))
    EntityModel<UsuarioGruposOutput> gruposDoUsuario(@ApiParam(value = "ID do usuário", example = "1", required = true) Long id);
}