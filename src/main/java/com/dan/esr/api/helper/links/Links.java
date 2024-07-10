package com.dan.esr.api.helper.links;

import com.dan.esr.api.controllers.cidade.CidadeController;
import com.dan.esr.api.controllers.cozinha.CozinhaPesquisaController;
import com.dan.esr.api.controllers.estado.EstadoController;
import com.dan.esr.api.controllers.formapagamento.FormaPagamentoController;
import com.dan.esr.api.controllers.grupo.GrupoController;
import com.dan.esr.api.controllers.pedido.PedidoGerenciamentoController;
import com.dan.esr.api.controllers.pedido.PedidoPesquisaController;
import com.dan.esr.api.controllers.restaurante.RestaurantePesquisaController;
import com.dan.esr.api.controllers.restaurante.RestauranteProdutoController;
import com.dan.esr.api.controllers.restaurante.RestauranteResponsavelController;
import com.dan.esr.api.controllers.usuario.UsuarioPesquisaController;
import com.dan.esr.domain.entities.FormaPagamento;
import com.dan.esr.domain.entities.Grupo;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.TemplateVariables;
import org.springframework.hateoas.UriTemplate;

import java.util.List;

import static com.dan.esr.api.helper.links.factory.TemplateVariablesFactory.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public final class Links {
    private static final TemplateVariables PAGE_VARIABLES = createTemplateVariables(SORT, PAGE, SIZE);
    private static final TemplateVariables FILTRO_VARIABLES = createTemplateVariables(CLIENTE_ID, RESTAURANTE_ID,
            DATA_CRIACAO_INICIAL, DATA_CRIACAO_FINAL);

    private Links() {
    }

    public static Link linkToPedidos() {
        String pedidosUrl = linkTo(PedidoPesquisaController.class).toUri().toString();
        return Link.of(UriTemplate.of(pedidosUrl, PAGE_VARIABLES.concat(FILTRO_VARIABLES)), "pedidos");
    }

    public static Link linkToPedidoConfirmado(String codigoPedido) {
        return linkTo(methodOn(PedidoGerenciamentoController.class)
                .confirmado(codigoPedido)).withRel("confirmado");
    }

    public static Link linkToPedidoEntregue(String codigoPedido) {
        return linkTo(methodOn(PedidoGerenciamentoController.class)
                .entregue(codigoPedido)).withRel("entregue");
    }

    public static Link linkToPedidoCancelado(String codigoPedido) {
        return linkTo(methodOn(PedidoGerenciamentoController.class)
                .cancelado(codigoPedido)).withRel("cancelado");
    }

    public static Link linkToRestaurante(Long restauranteId) {
        return linkTo(methodOn(RestaurantePesquisaController.class).restaurante(restauranteId)).withRel("restaurante");
    }

    public static Link linkToRestaurantes() {
        return linkTo(RestaurantePesquisaController.class).withSelfRel();
    }

    public static Link linkToResponsaveisRestaurante(Long restauranteId) {
        return linkTo(methodOn(RestauranteResponsavelController.class).buscarResponsaveis(restauranteId))
                .withRel("responsaveis");
    }

    public static Link linkToFormaPagamento(Long formaPagamentoId) {
        return linkTo(methodOn(FormaPagamentoController.class)
                .formaPagamento(formaPagamentoId, null)).withRel("formaPagamento");
    }

    public static Link linkToFormasPagamento() {
        return linkTo(methodOn(FormaPagamentoController.class)).withSelfRel();
    }

    public static List<Link> linkToFormasPagamento(List<FormaPagamento> formasPagamento) {
        return formasPagamento.stream()
                .map(fp -> linkToFormaPagamento(fp.getId()))
                .toList();
    }

    public static Link linkToProduto(Long restauranteId, Long produtoId, String rel) {
        return linkTo(methodOn(RestauranteProdutoController.class)
                .produto(restauranteId, produtoId))
                .withRel(rel);
    }

    public static Link linkToProduto(Long restauranteId, Long produtoId) {
        return linkToProduto(restauranteId, produtoId, "produto");
    }

    public static Link linkToUsuario(Long usuarioId, String rel) {
        return linkTo(methodOn(UsuarioPesquisaController.class).usuario(usuarioId)).withRel(rel);
    }

    public static Link linkToUsuario(Long usuarioId) {
        return linkToUsuario(usuarioId, "usuário");
    }

    public static Link linkToUsuarios() {
        return linkTo(UsuarioPesquisaController.class).withSelfRel();
    }

    public static Link linkToCidade(Long cidadeId) {
        return linkTo(methodOn(CidadeController.class).cidade(cidadeId)).withSelfRel();
    }
    public static Link linkToCidades() {
        return linkTo(CidadeController.class).withSelfRel();
    }

    public static Link linkToEstado(Long estadoId) {
        return linkTo(methodOn(EstadoController.class).estado(estadoId)).withSelfRel();
    }

    public static Link linkToEstados() {
        return linkTo(EstadoController.class).withSelfRel();
    }

    public static Link linkToCozinha(Long cozinhaId) {
        return linkTo(methodOn(CozinhaPesquisaController.class).cozinha(cozinhaId)).withSelfRel();
    }

    public static Link linkToCozinhas() {
        return linkTo(CozinhaPesquisaController.class).withSelfRel();
    }

    public static Link linkToGrupo(Long grupoId) {
        return linkTo(methodOn(GrupoController.class).grupo(grupoId)).withSelfRel();
    }

    public static Link linkToGrupos() {
        return linkTo(GrupoController.class).withSelfRel();
    }

    public static List<Link> linkToGrupos(List<Grupo> grupos) {
        return grupos.stream()
                .map(grupo -> linkToGrupo(grupo.getId()))
                .toList();
    }
}