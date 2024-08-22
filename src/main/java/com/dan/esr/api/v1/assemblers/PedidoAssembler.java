package com.dan.esr.api.v1.assemblers;

import com.dan.esr.api.v1.controllers.pedido.PedidoPesquisaController;
import com.dan.esr.api.v1.models.input.pedido.PedidoInput;
import com.dan.esr.api.v1.models.output.pedido.PedidoOutput;
import com.dan.esr.api.v1.models.output.pedido.PedidoStatusOutput;
import com.dan.esr.domain.entities.Pedido;
import lombok.NonNull;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static com.dan.esr.api.v1.links.Links.*;

@Component
public class PedidoAssembler extends RepresentationModelAssemblerSupport<Pedido, PedidoOutput> {
    @Autowired
    private ModelMapper mapper;

    public PedidoAssembler() {
        super(PedidoPesquisaController.class, PedidoOutput.class);
    }

    @NonNull
    @Override
    public PedidoOutput toModel(@NonNull Pedido pedido) {
        return this.mapper.map(pedido, PedidoOutput.class)
                .add(linkToPedido(pedido.getCodigo()))
                .add(linkToRestaurante(pedido.getRestaurante().getId()))
                .add(linkToFormaPagamento(pedido.getFormaPagamento().getId()))
                .add(linkToUsuario(pedido.getUsuario().getId()))
                .addIf(pedido.podeSerConfirmado(), () -> linkToPedidoConfirmado(pedido.getCodigo()))
                .addIf(pedido.podeSerEntregue(), () -> linkToPedidoEntregue(pedido.getCodigo()))
                .addIf(pedido.podeSerCancelado(), () -> linkToPedidoCancelado(pedido.getCodigo()))
                .add(linkToItensPedido(pedido.getItensPedido()));
    }

    public PedidoStatusOutput toModelStatus(Pedido pedido) {
        PedidoOutput pedidoOutput = toModel(pedido);
        PedidoStatusOutput pedidoStatusOutput = this.mapper.map(pedido, PedidoStatusOutput.class);
        return pedidoStatusOutput.add(pedidoOutput.getLinks());
    }

    @NonNull
    @Override
    public CollectionModel<PedidoOutput> toCollectionModel(@NonNull Iterable<? extends Pedido> entities) {
        return super.toCollectionModel(entities).add(linkToPedidos());
    }

    public Pedido toDomain(PedidoInput pedidoInput) {
        return this.mapper.map(pedidoInput, Pedido.class);
    }
}