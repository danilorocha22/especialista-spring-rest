package com.dan.esr.api.v1.openapi.documentation.restaurante;

import com.dan.esr.api.exceptionhandler.Problem;
import com.dan.esr.api.v1.models.output.restaurante.RestauranteOutput;
import io.swagger.annotations.*;
import org.springframework.hateoas.EntityModel;

import java.util.List;

@Api(tags = "Restaurantes")
public interface RestauranteAtivacaoDocumentation {

    @ApiOperation("Ativa um restaurante pelo ID")
    @ApiResponses({
            @ApiResponse(code = 400, message = "ID do restaurante inválido", response = Problem.class),
            @ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
    })
    EntityModel<RestauranteOutput> ativacao(@ApiParam(value = "ID de um restaurante", example = "1", required = true) Long id);

    @ApiOperation("Inativa um restaurante pelo ID")
    @ApiResponses({
            @ApiResponse(code = 400, message = "ID do restaurante inválido", response = Problem.class),
            @ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
    })
    EntityModel<RestauranteOutput> inativacao(@ApiParam(value = "ID de um restaurante", example = "1", required = true) Long id);

    @ApiOperation("Abre um restaurante pelo ID")
    @ApiResponses({
            @ApiResponse(code = 400, message = "ID do restaurante inválido", response = Problem.class),
            @ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
    })
    EntityModel<RestauranteOutput> abertura(@ApiParam(value = "ID de um restaurante", example = "1", required = true) Long id);

    @ApiOperation("Fecha um restaurante pelo ID")
    @ApiResponses({
            @ApiResponse(code = 400, message = "ID do restaurante inválido", response = Problem.class),
            @ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
    })
    EntityModel<RestauranteOutput> fechamento(@ApiParam(value = "ID de um restaurante", example = "1", required = true) Long id);

    @ApiOperation("Ativa múltiplos restaurantes")
    @ApiResponses({@ApiResponse(code = 200, message = "Restaurantes ativados com sucesso")})
    void ativacoes(
            @ApiParam(name = "corpo", value = "Lista de Ids de restaurantes", example = "[1,2,3]", required = true)
            List<Long> ids
    );

    @ApiOperation("Inativa múltiplos restaurantes")
    @ApiResponses({@ApiResponse(code = 200, message = "Restaurantes inativados com sucesso")})
    void desativacoes(
            @ApiParam(name = "corpo", value = "Lista de Ids de restaurantes", example = "[1,2,3]", required = true)
            List<Long> ids
    );
}