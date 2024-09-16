package com.dan.esr.api.v1.controllers.pedido;

import com.dan.esr.api.v1.models.input.pedido.PedidoInput;
import com.dan.esr.api.v1.models.output.pedido.PedidoOutput;
import com.dan.esr.api.v1.models.output.pedido.PedidoStatusOutput;
import com.dan.esr.api.v1.openapi.documentation.pedido.PedidoGerenciamentoDocumentation;
import com.dan.esr.api.v1.assemblers.PedidoAssembler;
import com.dan.esr.core.security.DanfoodSecurity;
import com.dan.esr.domain.entities.Pedido;
import com.dan.esr.domain.entities.Usuario;
import com.dan.esr.domain.services.pedido.PedidoEmissaoService;
import com.dan.esr.domain.services.pedido.PedidoStatusService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1/pedidos", produces = APPLICATION_JSON_VALUE)
public class PedidoGerenciamentoController implements PedidoGerenciamentoDocumentation {
    private final PedidoEmissaoService pedidoEmissaoService;
    private final PedidoStatusService pedidoStatusService;
    private final PedidoAssembler pedidoAssembler;
    private final DanfoodSecurity danfoodSecurity;

    @Override
    @PostMapping
    @ResponseStatus(CREATED)
    public EntityModel<PedidoOutput> novoPedido(@RequestBody @Valid PedidoInput pedidoInput) {
        Pedido novoPedido = this.pedidoAssembler.toDomain(pedidoInput);
        novoPedido.setUsuario(new Usuario());
        novoPedido.getUsuario().setId(danfoodSecurity.getUsuarioId());
        novoPedido.getEndereco().setId(null);
        novoPedido = this.pedidoEmissaoService.emitir(novoPedido);
        return EntityModel.of(
                this.pedidoAssembler.toModel(novoPedido)
        );
    }

    @Override
    @PutMapping("/{codigoPedido}/confirmacao")
    public EntityModel<PedidoStatusOutput> confirmado(@PathVariable String codigoPedido) {
        Pedido pedidoConfirmado = this.pedidoStatusService.confirmar(codigoPedido);
        return EntityModel.of(
                this.pedidoAssembler.toModelStatus(pedidoConfirmado)
        );
    }

    @Override
    @PutMapping("/{codigoPedido}/entrega")
    public EntityModel<PedidoStatusOutput> entregue(@PathVariable String codigoPedido) {
        Pedido pedidoEntregue = this.pedidoStatusService.entregar(codigoPedido);
        return EntityModel.of(
                this.pedidoAssembler.toModelStatus(pedidoEntregue)
        );
    }

    @Override
    @PutMapping("/{codigoPedido}/cancelamento")
    public EntityModel<PedidoStatusOutput> cancelado(@PathVariable String codigoPedido) {
        Pedido pedidoCancelado = this.pedidoStatusService.cancelar(codigoPedido);
        return EntityModel.of(
                this.pedidoAssembler.toModelStatus(pedidoCancelado)
        );
    }
}