package com.dan.esr.api.v1.openapi.documentation.cozinha;

import com.dan.esr.api.exceptionhandler.Problem;
import com.dan.esr.api.v1.models.output.cozinha.CozinhaOutput;
import com.dan.esr.api.v1.models.output.cozinha.CozinhasXML;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;

@Api(tags = "Cozinhas")
public interface CozinhaPesquisaDocumentation {

    @ApiOperation("Busca uma cozinha pelo ID")
    @ApiResponses({
            @ApiResponse(code = 400, message = "ID da cozinha inválido", response = Problem.class),
            @ApiResponse(code = 404, message = "Cozinha não encontrada", response = Problem.class)
    })
    EntityModel<CozinhaOutput> cozinha(Long id);

    @ApiOperation("Busca a primeira cozinha")
    @ApiResponses(@ApiResponse(code = 404, message = "Cozinha não encontrada", response = Problem.class))
    EntityModel<CozinhaOutput> primeiraCozinha();

    @ApiOperation("Busca a primeira cozinha com nome semelhante")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Nome da cozinha inválido", response = Problem.class),
            @ApiResponse(code = 404, message = "Cozinha não encontrada", response = Problem.class)
    })
    EntityModel<CozinhaOutput> primeiraCozinhaComNomeSemelhante(String nome);

    @ApiOperation("Busca a primeira cozinha com nome idêntico")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Nome da cozinha inválido", response = Problem.class),
            @ApiResponse(code = 404, message = "Cozinha não encontrada", response = Problem.class)
    })
    EntityModel<CozinhaOutput> cozinhaComNomeIgual(String nome);

    @ApiOperation("Lista as cozinhas com nome semelhante")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Nome da cozinha inválido", response = Problem.class),
            @ApiResponse(code = 404, message = "Cozinha não encontrada", response = Problem.class)
    })
    CollectionModel<CozinhaOutput> cozinhasComNomeSemelhante(String nome);

    @ApiOperation("Lista paginada das cozinhas")
    @ApiResponses(@ApiResponse(code = 404, message = "Cozinha não encontrada", response = Problem.class))
    PagedModel<CozinhaOutput> cozinhas(Pageable pageable);

    @ApiOperation("Verifica se determinada cozinha existe com nome idêntico")
    @ApiResponses(@ApiResponse(code = 400, message = "Nome da cozinha inválido", response = Problem.class))
    boolean existe(String nome);

    @ApiOperation("Lista paginada das cozinhas no formato XML")
    @ApiResponses(@ApiResponse(code = 404, message = "Cozinha não encontrada", response = Problem.class))
    CollectionModel<CozinhasXML> listarXml(Pageable pageable);
}
