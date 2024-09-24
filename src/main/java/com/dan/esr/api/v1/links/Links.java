package com.dan.esr.api.v1.links;

import com.dan.esr.api.v1.controllers.cidade.CidadeController;
import com.dan.esr.api.v1.controllers.cozinha.CozinhaPesquisaController;
import com.dan.esr.api.v1.controllers.estado.EstadoController;
import com.dan.esr.api.v1.controllers.estatisticas.EstatisticasController;
import com.dan.esr.api.v1.controllers.formapagamento.FormaPagamentoController;
import com.dan.esr.api.v1.controllers.grupo.GrupoController;
import com.dan.esr.api.v1.controllers.pedido.PedidoGerenciamentoController;
import com.dan.esr.api.v1.controllers.pedido.PedidoPesquisaController;
import com.dan.esr.api.v1.controllers.restaurante.*;
import com.dan.esr.api.v1.controllers.usuario.UsuarioPesquisaController;
import com.dan.esr.domain.entities.*;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.UriTemplate;

import java.util.List;

import static com.dan.esr.api.factory.TemplateVariablesFactory.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public final class Links {

    private Links() {
    }

    public static Link linkToEstatisticasVendasDiarias() {
        var filtroVariables = createTemplateVariables(RESTAURANTE_ID, DATA_CRIACAO_INICIAL, DATA_CRIACAO_FINAL, TIME_OFFSET);
        String pedidosUrl = linkTo(methodOn(EstatisticasController.class)
                .consultarVendasDiaria(null, null)).toUri().toString();
        return Link.of(UriTemplate.of(pedidosUrl, filtroVariables), "vendas-diaria");
    }

    public static Link linkToEstatisticas() {
        return linkTo(EstatisticasController.class).withRel("estatisticas");
    }

    public static Link linkToPedido(String codigo) {
        return linkTo(methodOn(PedidoPesquisaController.class)
                .pedido(codigo)).withRel("pedido");
    }

    public static Link linkToPedidos() {
        String pedidosUrl = linkTo(PedidoPesquisaController.class).toUri().toString();
        var pageVariables = createTemplateVariables(SORT, PAGE, SIZE);
        var filtroVariables = createTemplateVariables(CLIENTE_ID, RESTAURANTE_ID, DATA_CRIACAO_INICIAL, DATA_CRIACAO_FINAL);
        return Link.of(UriTemplate.of(pedidosUrl, pageVariables.concat(filtroVariables)), "pedidos");
    }

    public static List<Link> linkToItensPedido(List<ItemPedido> itens) {
        return itens.stream()
                .map(item -> linkToItemPedido(item.getProduto()))
                .toList();
    }

    private static Link linkToItemPedido(Produto produto) {
        return linkTo(methodOn(RestauranteProdutoController.class)
                .produto(produto.getRestaurante().getId(), produto.getId())).withRel("item");
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

    public static Link linkToRestaurantes() {
        String restaurantesUrl = linkTo(RestaurantePesquisaController.class).toUri().toString();
        var pageVariables = createTemplateVariables(SORT, PAGE, SIZE);
        var filtroVariables = createTemplateVariables(CLIENTE_ID, RESTAURANTE_ID, DATA_CRIACAO_INICIAL, DATA_CRIACAO_FINAL);
        return Link.of(UriTemplate.of(restaurantesUrl, pageVariables.concat(filtroVariables)), "restaurantes");
    }

    public static Link linkToRestaurante(Long restauranteId) {
        return linkTo(methodOn(RestaurantePesquisaController.class).restaurante(restauranteId)).withRel("restaurante");
    }

    public static Link linkToRestauranteResponsaveis(Long restauranteId) {
        return linkTo(methodOn(RestauranteResponsavelController.class).buscarResponsaveis(restauranteId))
                .withRel("responsaveis");
    }

    public static Link linkToRestauranteAberto(Long restauranteId) {
        return linkTo(methodOn(RestauranteAtivacaoController.class).abertura(restauranteId)).withRel("abrir");
    }

    public static Link linkToRestauranteFechado(Long restauranteId) {
        return linkTo(methodOn(RestauranteAtivacaoController.class).fechamento(restauranteId)).withRel("fechar");
    }

    public static Link linkToRestauranteAtivado(Long restauranteId) {
        return linkTo(methodOn(RestauranteAtivacaoController.class).ativacao(restauranteId)).withRel("ativar");
    }

    public static Link linkToRestauranteInativado(Long restauranteId) {
        return linkTo(methodOn(RestauranteAtivacaoController.class).inativacao(restauranteId)).withRel("inativar");
    }

    public static Link linkToRestauranteFormasPagamento(Long restauranteId) {
        return linkTo(methodOn(RestauranteFormaPagamentoController.class)
                .listarFormasPagamento(restauranteId)).withRel("formasPagamento");
    }

    public static Link linkToRestauranteAdicionarFormaPagamento(Long restauranteId) {
        return linkTo(methodOn(RestauranteFormaPagamentoController.class)
                .adicionarFormaPagamento(restauranteId, null)).withRel("adicionarFormaPagamento");
    }

    public static Link linkToRestauranteRemoverFormaPagamento(Long restauranteId, Long formaPagamentoId) {
        return linkTo(methodOn(RestauranteFormaPagamentoController.class)
                .removerFormaPagamento(restauranteId, formaPagamentoId)).withRel("removerFormaPagamento");
    }

    public static Link linkToRestauranteAdicionarResponsavel(Long restauranteId) {
        return linkTo(methodOn(RestauranteResponsavelController.class)
                .adicionarResponsavel(restauranteId, null)).withRel("adicionarResponsavel");
    }

    public static Link linkToRestauranteRemoverResponsavel(Long restauranteId) {
        return linkTo(methodOn(RestauranteResponsavelController.class)
                .removerResponsavel(restauranteId, null)).withRel("removerResponsavel");
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
                .produto(restauranteId, produtoId)).withRel(rel);
    }

    public static Link linkToProduto(Long restauranteId, Long produtoId) {
        return linkToProduto(restauranteId, produtoId, "produto");
    }

    public static Link linkToProdutos(Long restauranteId) {
        return linkTo(methodOn(RestauranteProdutoController.class).produtos(true, restauranteId))
                .withRel("produtos");
    }

    public static Link linkToFotoProduto(Long restauranteId, Long produtoId, String foto) {
        return linkTo(methodOn(RestauranteFotoProdutoController.class).buscarDadosFoto(restauranteId, produtoId))
                .withRel(foto);
    }

    public static Link linkToUsuario(Long usuarioId, String rel) {
        return linkTo(methodOn(UsuarioPesquisaController.class).usuario(usuarioId)).withRel(rel);
    }

    public static List<Link> linkToUsuarios(List<Usuario> usuarios) {
        return usuarios.stream()
                .map(usuario -> linkToUsuario(usuario.getId()))
                .toList();
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