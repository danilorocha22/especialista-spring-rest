package com.dan.esr.api.openapi.documentation.cozinha;

import com.dan.esr.api.exceptionhandler.Problem;
import com.dan.esr.api.models.output.cozinha.CozinhaOutput;
import com.dan.esr.api.models.output.cozinha.CozinhasXML;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Api(tags = "Cozinhas")
public interface CozinhaPesquisaDocumentation {

    @ApiOperation("Busca uma cozinha pelo ID")
    @ApiResponses({
            @ApiResponse(code = 400, message = "ID da cozinha inválido", response = Problem.class),
            @ApiResponse(code = 404, message = "Cozinha não encontrada", response = Problem.class)
    })
    CozinhaOutput cozinha(Long id);

    @ApiOperation("Busca a primeira cozinha")
    @ApiResponses(@ApiResponse(code = 404, message = "Cozinha não encontrada", response = Problem.class))
    CozinhaOutput primeiraCozinha();

    @ApiOperation("Busca a primeira cozinha com nome semelhante")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Nome da cozinha inválido", response = Problem.class),
            @ApiResponse(code = 404, message = "Cozinha não encontrada", response = Problem.class)
    })
    CozinhaOutput primeiraCozinhaComNomeSemelhante(String nome);

    @ApiOperation("Busca a primeira cozinha com nome idêntico")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Nome da cozinha inválido", response = Problem.class),
            @ApiResponse(code = 404, message = "Cozinha não encontrada", response = Problem.class)
    })
    CozinhaOutput cozinhaComNomeIgual(String nome);

    @ApiOperation("Lista as cozinhas com nome semelhante")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Nome da cozinha inválido", response = Problem.class),
            @ApiResponse(code = 404, message = "Cozinha não encontrada", response = Problem.class)
    })
    List<CozinhaOutput> cozinhasComNomeSemelhante(String nome);

    @ApiOperation("Lista paginada das cozinhas")
    @ApiResponses(@ApiResponse(code = 404, message = "Cozinha não encontrada", response = Problem.class))
    Page<CozinhaOutput> cozinhas(Pageable pageable);

    @ApiOperation("Verifica se determinada cozinha existe com nome idêntico")
    @ApiResponses(@ApiResponse(code = 400, message = "Nome da cozinha inválido", response = Problem.class))
    boolean existe(String nome);

    @ApiOperation("Lista paginada das cozinhas no formato XML")
    @ApiResponses(@ApiResponse(code = 404, message = "Cozinha não encontrada", response = Problem.class))
    CozinhasXML listarXml(Pageable pageable);
}
