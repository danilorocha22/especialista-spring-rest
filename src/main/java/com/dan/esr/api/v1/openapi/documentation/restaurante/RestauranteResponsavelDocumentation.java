package com.dan.esr.api.v1.openapi.documentation.restaurante;

import com.dan.esr.api.exceptionhandler.Problem;
import com.dan.esr.api.v1.models.output.restaurante.RestauranteResponsaveisOutput;
import com.dan.esr.api.v1.models.output.usuario.UsuarioOutput;
import io.swagger.annotations.*;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;

@Api(tags = "Restaurantes")
public interface RestauranteResponsavelDocumentation {

    @ApiOperation("Lista os responsáveis de um restaurante pelo ID")
    CollectionModel<UsuarioOutput> buscarResponsaveis(
            @ApiParam(value = "ID de um restaurante", example = "1", required = true)
            Long restauranteId
    );

    @ApiOperation("Adiciona um responsável pelo ID a um restaurante pelo ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Responsável adicionado", response = Problem.class),
            @ApiResponse(code = 404, message = "Restaurante ou responsável não encontrado", response = Problem.class)
    })
    EntityModel<RestauranteResponsaveisOutput> adicionarResponsavel(
            @ApiParam(value = "ID de um restaurante", example = "1", required = true)
            Long restauranteId,
            @ApiParam(value = "ID de um responsável", example = "1", required = true)
            Long usuarioId
    );

    @ApiOperation("Remove um responsável pelo seu ID de um restaurante pelo ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Responsável removido", response = Problem.class),
            @ApiResponse(code = 404, message = "Restaurante ou responsável não encontrado", response = Problem.class)
    })
    EntityModel<RestauranteResponsaveisOutput> removerResponsavel(
            @ApiParam(value = "ID de um restaurante", example = "1", required = true)
            Long restauranteId,
            @ApiParam(value = "ID de um responsável", example = "1", required = true)
            Long usuarioId
    );
}