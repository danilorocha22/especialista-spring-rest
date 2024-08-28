package com.dan.esr.api.v1.openapi.documentation.restaurante;

import com.dan.esr.api.exceptionhandler.Problem;
import com.dan.esr.api.v1.models.input.restaurante.RestauranteInput;
import com.dan.esr.api.v1.models.output.restaurante.RestauranteOutput;
import io.swagger.annotations.*;
import javax.servlet.http.HttpServletRequest;
import org.springframework.hateoas.EntityModel;

import java.util.Map;

@Api(tags = "Restaurantes")
public interface RestauranteCadastroDocumentation {

    @ApiOperation("Cadastra um restaurante")
    @ApiResponses(@ApiResponse(code = 200, message = "Restaurante cadastrado"))
    EntityModel<RestauranteOutput> novoRestaurante(
            @ApiParam(name = "corpo", value = "Representação de um novo restaurante")
            RestauranteInput restauranteInput
    );

    @ApiOperation("Atualiza um restaurante pelo ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Restaurante atualizado", response = Problem.class),
            @ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
    })
    EntityModel<RestauranteOutput> atualizarRestaurante(
            @ApiParam(value = "ID do restaurante", example = "1", required = true)
            Long id,
            @ApiParam(name = "corpo", value = "Representação de um restaurante com novos dados")
            RestauranteInput restauranteInput
    );

    @ApiOperation("Atualiza parcialmente um restaurante pelo ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Restaurante atualizado", response = Problem.class),
            @ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
    })
    EntityModel<RestauranteOutput> atualizarParcial(
            @ApiParam(value = "ID do restaurante", example = "1", required = true)
            Long id,
            @ApiParam(name = "campos", value = "Representação dos campos do restaurante para atualização", required = true)
            Map<String, Object> campos,
            HttpServletRequest request
    );

    @ApiOperation("Exclui um restaurante pelo ID")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Restaurante excluído", response = Problem.class),
            @ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
    })
    void excluir(@ApiParam(value = "ID do restaurante", example = "1", required = true) Long id);
}