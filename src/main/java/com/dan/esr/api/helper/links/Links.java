package com.dan.esr.api.helper.links;

import com.dan.esr.api.controllers.pedido.PedidoPesquisaController;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.UriTemplate;

import static com.dan.esr.api.helper.links.factory.TemplateVariablesFactory.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

public final class Links {

    private Links() {
    }

    public static Link linkToPedidos() {
        String pedidosUrl = linkTo(PedidoPesquisaController.class).toUri().toString();
        var pageVariables = createTemplateVariables(SORT, PAGE, SIZE);
        var filtroVariables = createTemplateVariables(CLIENTE_ID, RESTAURANTE_ID, DATA_CRIACAO_INICIAL,
                DATA_CRIACAO_FINAL);
        return Link.of(UriTemplate.of(pedidosUrl, pageVariables.concat(filtroVariables)), "pedidos");
    }
}
