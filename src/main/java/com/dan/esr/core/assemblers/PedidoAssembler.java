package com.dan.esr.core.assemblers;

import com.dan.esr.api.controllers.formapagamento.FormaPagamentoController;
import com.dan.esr.api.controllers.pedido.PedidoPesquisaController;
import com.dan.esr.api.controllers.restaurante.RestaurantePesquisaController;
import com.dan.esr.api.controllers.usuario.UsuarioPesquisaController;
import com.dan.esr.api.models.input.pedido.PedidoInput;
import com.dan.esr.api.models.output.pedido.PedidoOutput;
import com.dan.esr.api.models.output.pedido.PedidoStatusOutput;
import com.dan.esr.core.util.TemplateVariablesUtil;
import com.dan.esr.domain.entities.Pedido;
import lombok.NonNull;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.*;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static com.dan.esr.core.util.TemplateVariablesUtil.templateVariables;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


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
        PedidoOutput pedidoOutput = createModelWithId(pedido.getCodigo(), pedido);
        this.mapper.map(pedido, pedidoOutput);
        String pedidosUrl = linkTo(PedidoPesquisaController.class).toUri().toString();

        return pedidoOutput
                .add(Link.of(UriTemplate.of(pedidosUrl, templateVariables()), "pedidos"))
                .add(linkTo(methodOn(RestaurantePesquisaController.class).restaurante(pedido.getId())).withSelfRel())
                .add((linkTo(methodOn(FormaPagamentoController.class).formaPagamento(
                        pedido.getFormaPagamento().getId(), null)).withSelfRel()))
                .add(linkTo(methodOn(UsuarioPesquisaController.class).usuario(pedido.getUsuario().getId()))
                        .withSelfRel());
    }

    public PedidoStatusOutput toModelStatus(Pedido pedido) {
        return this.mapper.map(pedido, PedidoStatusOutput.class);
    }

    @NonNull
    @Override
    public CollectionModel<PedidoOutput> toCollectionModel(@NonNull Iterable<? extends Pedido> entities) {
        return toCollectionModel(entities)
                .add(linkTo(PedidoPesquisaController.class).withRel("pedidos"));
    }

    public Pedido toDomain(PedidoInput pedidoInput) {
        return this.mapper.map(pedidoInput, Pedido.class);
    }
}