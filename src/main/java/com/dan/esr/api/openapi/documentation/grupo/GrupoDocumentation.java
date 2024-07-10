package com.dan.esr.api.openapi.documentation.grupo;

import com.dan.esr.api.exceptionhandler.Problem;
import com.dan.esr.api.models.input.grupo.GrupoInput;
import com.dan.esr.api.models.output.grupo.GrupoOutput;
import io.swagger.annotations.*;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;

@Api(tags = "Grupos")
public interface GrupoDocumentation {

    @ApiOperation("Busca um grupo pelo ID")
    @ApiResponses({
            @ApiResponse(code = 400, message = "ID do grupo inválido", response = Problem.class),
            @ApiResponse(code = 404, message = "Grupo não encontrado", response = Problem.class)
    })
    EntityModel<GrupoOutput> grupo(@ApiParam(value = "ID de um grupo", example = "1") Long id);

    @ApiOperation("Lista os grupos")
    CollectionModel<GrupoOutput> grupos();

    @ApiOperation("Cadastra um grupo")
    @ApiResponses(@ApiResponse(code = 200, message = "Grupo cadastrado"))
    GrupoOutput novoGrupo(@ApiParam(name = "corpo", value = "Representação de um novo grupo") GrupoInput GrupoInput);

    @ApiOperation("Atualiza um grupo pelo ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Grupo atualizado", response = Problem.class),
            @ApiResponse(code = 404, message = "Grupo não encontrado", response = Problem.class)
    })
    GrupoOutput atualizarGrupo(
            @ApiParam(value = "ID de um grupo", example = "1")
            Long id,
            @ApiParam(name = "corpo", value = "Representação de um grupo com novos dados")
            GrupoInput grupoInput
    );

    @ApiOperation("Exclui uma cidade pelo ID")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Cidade excluída", response = Problem.class),
            @ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class)
    })
    void excluirGrupo(Long id);
}