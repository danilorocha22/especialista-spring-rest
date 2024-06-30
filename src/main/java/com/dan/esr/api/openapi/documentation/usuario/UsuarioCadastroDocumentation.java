package com.dan.esr.api.openapi.documentation.usuario;

import com.dan.esr.api.exceptionhandler.Problem;
import com.dan.esr.api.models.input.usuario.UsuarioAtualizadoInput;
import com.dan.esr.api.models.input.usuario.UsuarioInput;
import com.dan.esr.api.models.input.usuario.UsuarioSenhaInput;
import com.dan.esr.api.models.output.usuario.UsuarioOutput;
import io.swagger.annotations.*;

@Api(tags = "Usuários")
public interface UsuarioCadastroDocumentation {

    @ApiOperation("Cadastra um usuário")
    @ApiResponses(@ApiResponse(code = 200, message = "Restaurante cadastrado"))
    UsuarioOutput novoUsuario(
            @ApiParam(name = "corpo", value = "Representação de um novo usuário")
            UsuarioInput usuarioInput
    );

    @ApiOperation("Atualiza um usuário pelo ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Usuário atualizado", response = Problem.class),
            @ApiResponse(code = 404, message = "Usuário não encontrado", response = Problem.class)
    })
    UsuarioOutput atualizarUsuario(
            @ApiParam(value = "ID do usuário", example = "1", required = true)
            Long usuarioId,
            @ApiParam(name = "corpo", value = "Representação de um usuário com novos dados")
            UsuarioAtualizadoInput usuarioAtualizado
    );

    @ApiOperation("Atualiza a senha do usuário")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Usuário atualizado", response = Problem.class),
            @ApiResponse(code = 404, message = "Usuário não encontrado", response = Problem.class)
    })
    void atualizarSenha(
            @ApiParam(value = "ID do usuário", example = "1", required = true)
            Long usuarioId,
            @ApiParam(name = "corpo", value = "Representação de um usuário com nova senha")
            UsuarioSenhaInput usuarioNovaSenha
    );

    @ApiOperation("Exclui um usuário pelo ID")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Usuário excluído", response = Problem.class),
            @ApiResponse(code = 404, message = "Usuário não encontrado", response = Problem.class)
    })
    void excluir(@ApiParam(value = "ID do usuário", example = "1", required = true) Long id);
}