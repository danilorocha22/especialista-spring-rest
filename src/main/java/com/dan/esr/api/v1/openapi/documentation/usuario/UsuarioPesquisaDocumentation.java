package com.dan.esr.api.v1.openapi.documentation.usuario;

import com.dan.esr.api.exceptionhandler.Problem;
import com.dan.esr.api.v1.models.output.usuario.UsuarioOutput;
import io.swagger.annotations.*;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;

@Api(tags = "Usuários")
public interface UsuarioPesquisaDocumentation {

    @ApiOperation("Busca um usuário por ID")
    @ApiResponses(@ApiResponse(code = 404, message = "Usuário não encontrado", response = Problem.class))
    EntityModel<UsuarioOutput> usuario(@ApiParam(value = "ID do usuário", example = "1", required = true) Long id);

    @ApiOperation("Lista os usuário")
    CollectionModel<UsuarioOutput> usuarios();

    @ApiOperation("Busca o primeiro usuário")
    @ApiResponses(@ApiResponse(code = 404, message = "Usuário não encontrado", response = Problem.class))
    UsuarioOutput primeiroUsuario();

    @ApiOperation("Busca primeiro usuário com nome semelhante")
    @ApiResponses(@ApiResponse(code = 404, message = "Usuário não encontrado", response = Problem.class))
    UsuarioOutput primeiroUsuarioComNomeSemelhante(String nome);

}