package com.dan.esr.api.controllers.pedido;

import com.dan.esr.api.models.input.pedido.PedidoInput;
import com.dan.esr.api.models.output.pedido.PedidoOutput;
import com.dan.esr.api.models.output.pedido.PedidoResumoOutput;
import com.dan.esr.api.models.output.pedido.PedidoStatusOutput;
import com.dan.esr.core.assemblers.EnderecoAssembler;
import com.dan.esr.core.assemblers.ItemPedidoAssembler;
import com.dan.esr.core.assemblers.PedidoAssembler;
import com.dan.esr.domain.entities.Pedido;
import com.dan.esr.domain.services.pedido.PedidoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/pedidos")
public class PedidoController {
    private final PedidoService pedidoService;
    private final PedidoAssembler pedidoAssembler;
    private final EnderecoAssembler enderecoAssembler;
    private final ItemPedidoAssembler itemPedidoAssembler;

    @GetMapping("/{codigoPedido}")
    public PedidoOutput pedido(@PathVariable String codigoPedido) {
        Pedido pedido = this.pedidoService.buscarPor(codigoPedido);
        return this.pedidoAssembler.toModel(pedido);
    }

    @GetMapping
    public List<PedidoResumoOutput> todos() {
        List<Pedido> pedidos = this.pedidoService.todos();
        return this.pedidoAssembler.toCollection(pedidos);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PedidoOutput novoPedido(@RequestBody @Valid PedidoInput pedidoInput) {
        Pedido pedido = this.pedidoAssembler.toDomain(pedidoInput);
        return this.pedidoAssembler.toModel(
                this.pedidoService.emitir(pedido)
        );
    }

    @PutMapping("/{codigoPedido}/confirmacao")
    public PedidoStatusOutput pedidoConfirmado(@PathVariable String codigoPedido) {
        Pedido pedido = this.pedidoService.buscarPor(codigoPedido);
        return this.pedidoAssembler.toModelStatus(
                this.pedidoService.confirmar(pedido)
        );
    }

    @PutMapping("/{codigoPedido}/entrega")
    public PedidoStatusOutput pedidoEntregue(@PathVariable String codigoPedido) {
        Pedido pedido = this.pedidoService.buscarPor(codigoPedido);
        return this.pedidoAssembler.toModelStatus(
                this.pedidoService.entregar(pedido)
        );
    }

    @PutMapping("/{codigoPedido}/cancelamento")
    public PedidoStatusOutput pedidoCancelado(@PathVariable String codigoPedido) {
        Pedido pedido = this.pedidoService.buscarPor(codigoPedido);
        return this.pedidoAssembler.toModelStatus(
                this.pedidoService.cancelar(pedido)
        );
    }
}