package com.dan.esr.api.openapi.documentation.restaurante;

import com.dan.esr.api.exceptionhandler.Problem;
import com.dan.esr.api.models.input.produto.FotoProdutoInput;
import com.dan.esr.api.models.output.produto.FotoProdutoOutput;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Api(tags = "Produtos")
public interface RestauranteFotoProdutoDocumentation {

    @ApiOperation(value = "Busca a foto do produto de um restaurante",
            produces = "application/json, image/jpeg, image/png")
    @ApiResponses({
            @ApiResponse(code = 400, message = "ID do restaurante ou produto inválido", response = Problem.class),
            @ApiResponse(code = 404, message = "Foto do produto encontrada", response = Problem.class)
    })
    FotoProdutoOutput buscarDadosFoto(
            @ApiParam(value = "ID do restaurante", example = "1", required = true)
            Long restauranteId,
            @ApiParam(value = "ID do produto", example = "1", required = true)
            Long produtoId
    );

    @ApiOperation(value = "Download da foto de um produto de um restaurante", hidden = true)
    ResponseEntity<?> baixarFoto(Long restauranteId, Long produtoId, String acceptHeader
    ) throws HttpMediaTypeNotAcceptableException;

    @ApiOperation("Atualiza foto do produto de um restaurante")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Foto atualizada", response = Problem.class),
            @ApiResponse(code = 404, message = "Foto do produto não encontrada", response = Problem.class)
    })
    FotoProdutoOutput atualizarFoto(
            @ApiParam(value = "ID do restaurante", example = "1", required = true)
            Long restauranteId,
            @ApiParam(value = "ID do produto", example = "1", required = true)
            Long produtoId,
            @ApiParam(name = "corpo", value = "Representação de uma foto de um produto", required = true)
            FotoProdutoInput fotoProdutoInput,
            @ApiParam(value = "Arquivo da foto do produto (máximo de 500kb, apenas JPEG e PNG)", required = true)
            MultipartFile arquivo
    ) throws IOException;

    @ApiOperation("Exclui uma foto do produto de um restaurante")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Foto excluída", response = Problem.class),
            @ApiResponse(code = 404, message = "Foto do produto não encontrada", response = Problem.class)
    })
    void removerFoto(
            @ApiParam(value = "ID do restaurante", example = "1", required = true)
            Long restauranteId,
            @ApiParam(value = "ID do produto", example = "1", required = true)
            Long produtoId
    );
}