package com.dan.esr.api.openapi.documentation.usuario;

import com.dan.esr.api.exceptionhandler.Problem;
import com.dan.esr.api.models.output.usuario.UsuarioGruposOutput;
import com.dan.esr.api.models.output.usuario.UsuarioOutput;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Api(tags = "Usuários")
public interface UsuarioPesquisaDocumentation {

    @ApiOperation("Busca um usuário por ID")
    @ApiResponses(@ApiResponse(code = 404, message = "Usuário não encontrado", response = Problem.class))
    UsuarioOutput usuario(@ApiParam(value = "ID do usuário", example = "1", required = true) Long id);

    @ApiOperation("Lista os usuário")
    List<UsuarioOutput> usuarios();

    @ApiOperation("Busca o primeiro usuário")
    @ApiResponses(@ApiResponse(code = 404, message = "Usuário não encontrado", response = Problem.class))
    UsuarioOutput primeiroUsuario();

    @ApiOperation("Busca primeiro usuário com nome semelhante")
    @ApiResponses(@ApiResponse(code = 404, message = "Usuário não encontrado", response = Problem.class))
    UsuarioOutput primeiroUsuarioComNomeSemelhante(String nome);

    @ApiOperation("Lista os grupos do usuário")
    @ApiResponses(@ApiResponse(code = 404, message = "Usuário não encontrado", response = Problem.class))
    UsuarioGruposOutput usuarioGrupos(@ApiParam(value = "ID do usuário", example = "1", required = true) Long id);
}