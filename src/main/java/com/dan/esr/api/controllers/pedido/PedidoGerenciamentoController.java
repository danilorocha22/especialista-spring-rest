package com.dan.esr.api.controllers.pedido;

import com.dan.esr.api.models.input.pedido.PedidoInput;
import com.dan.esr.api.models.output.pedido.PedidoOutput;
import com.dan.esr.api.models.output.pedido.PedidoStatusOutput;
import com.dan.esr.api.openapi.documentation.pedido.PedidoGerenciamentoDocumentation;
import com.dan.esr.core.assemblers.PedidoAssembler;
import com.dan.esr.domain.entities.Pedido;
import com.dan.esr.domain.services.pedido.PedidoEmissaoService;
import com.dan.esr.domain.services.pedido.PedidoStatusService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/pedidos", produces = APPLICATION_JSON_VALUE)
public class PedidoGerenciamentoController implements PedidoGerenciamentoDocumentation {
    private final PedidoEmissaoService pedidoEmissaoService;
    private final PedidoStatusService pedidoStatusService;
    private final PedidoAssembler pedidoAssembler;

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PedidoOutput novoPedido(@RequestBody @Valid PedidoInput pedidoInput) {
        Pedido pedido = this.pedidoAssembler.toDomain(pedidoInput);
        pedido.getEndereco().setId(null);
        return this.pedidoAssembler.toModel(
                this.pedidoEmissaoService.emitir(pedido)
        );
    }

    @Override
    @PutMapping("/{codigoPedido}/confirmacao")
    public PedidoStatusOutput confirmacao(@PathVariable String codigoPedido) {
        return this.pedidoAssembler.toModelStatus(
                this.pedidoStatusService.confirmar(codigoPedido)
        );
    }

    @Override
    @PutMapping("/{codigoPedido}/entrega")
    public PedidoStatusOutput entrega(@PathVariable String codigoPedido) {
        return this.pedidoAssembler.toModelStatus(
                this.pedidoStatusService.entregar(codigoPedido)
        );
    }

    @Override
    @PutMapping("/{codigoPedido}/cancelamento")
    public PedidoStatusOutput cancelamento(@PathVariable String codigoPedido) {
        return this.pedidoAssembler.toModelStatus(
                this.pedidoStatusService.cancelar(codigoPedido)
        );
    }
}