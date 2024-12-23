package com.dan.esr.api.v1.openapi.documentation.formapagamento;

import com.dan.esr.api.exceptionhandler.Problem;
import com.dan.esr.api.v1.models.input.formapagamento.FormaPagamentoInput;
import com.dan.esr.api.v1.models.output.formapagamento.FormaPagamentoOutput;
import io.swagger.annotations.*;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.ServletWebRequest;

@Api(tags = "Formas de Pagamento")
public interface FormasPagamentoDocumentation {

    @ApiOperation("Busca uma forma de pagamento pelo ID")
    @ApiResponses({
            @ApiResponse(code = 400, message = "ID da forma de pagamento", response = Problem.class),
            @ApiResponse(code = 404, message = "Forma de pagamento não encontrada", response = Problem.class)
    })
    ResponseEntity<EntityModel<FormaPagamentoOutput>> formaPagamento(
            @ApiParam(value = "ID de uma forma de pagamento", example = "1")
            Long id,
            ServletWebRequest request
    );

    @ApiOperation("Lista as formas de pagamento")
    ResponseEntity<CollectionModel<FormaPagamentoOutput>> formasPagamentos(ServletWebRequest request);

    @ApiOperation("Cadastra uma forma de pagamento")
    @ApiResponses(@ApiResponse(code = 200, message = "Forma de pagamento cadastrada"))
    FormaPagamentoOutput novaFormaPagamento(
            @ApiParam(name = "corpo", value = "Representação de uma nova forma de pagamento")
            FormaPagamentoInput FormaPagamentoInput
    );

    @ApiOperation("Atualiza uma forma de pagamento pelo ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Forma de pagamento atualizada", response = Problem.class),
            @ApiResponse(code = 404, message = "Forma de pagamento não encontrada", response = Problem.class)
    })
    FormaPagamentoOutput atualizarFormaPagamento(
            @ApiParam(value = "ID de uma forma de pagamento", example = "1")
            Long id,
             @ApiParam(name = "corpo", value = "Representação de uma forma de pagamento com novos dados")
            FormaPagamentoInput FormaPagamentoInput
    );

    @ApiOperation("Exclui uma forma de pagamento pelo ID")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Forma de pagamento excluída", response = Problem.class),
            @ApiResponse(code = 404, message = "Forma de pagamento não encontrada", response = Problem.class)
    })
    void excluirFormaPagamento(@ApiParam(value = "ID de uma forma de pagamento", example = "1") Long id);
}