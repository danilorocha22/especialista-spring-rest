package com.dan.esr.core.assemblers;

import com.dan.esr.api.controllers.pedido.PedidoPesquisaController;
import com.dan.esr.api.models.output.pedido.PedidoOutput;
import com.dan.esr.api.models.output.pedido.PedidoResumoOutput;
import com.dan.esr.domain.entities.Pedido;
import lombok.NonNull;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static com.dan.esr.api.helper.links.Links.linkToPedidos;

@Component
@SuppressWarnings("all")
public class PedidoResumoAssembler extends RepresentationModelAssemblerSupport<Pedido, PedidoResumoOutput> {
    @Autowired
    private ModelMapper mapper;

    @Autowired
    private PedidoAssembler pedidoAssembler;

    public PedidoResumoAssembler() {
        super(PedidoPesquisaController.class, PedidoResumoOutput.class);
    }

    @NonNull
    @Override
    public PedidoResumoOutput toModel(@NonNull Pedido pedido) {
        PedidoOutput pedidoOutput = pedidoAssembler.toModel(pedido);
        PedidoResumoOutput pedidoResumoOutput = this.mapper.map(pedidoOutput, PedidoResumoOutput.class);
        return pedidoResumoOutput.add(pedidoOutput.getLinks());
    }

    @NonNull
    @Override
    public CollectionModel<PedidoResumoOutput> toCollectionModel(@NonNull Iterable<? extends Pedido> entities) {
        return super.toCollectionModel(entities).add(linkToPedidos());
    }
}