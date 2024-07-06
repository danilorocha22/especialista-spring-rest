package com.dan.esr.core.helper;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Map;

public final class PageableWrapperHelper {
    private PageableWrapperHelper() {
    }

    public static Pageable of(Pageable pageable) {
        return PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                configurarCamposParaOrdenacao(pageable.getSort())
        );
    }

    private static Sort configurarCamposParaOrdenacao(Sort sort) {
        List<Sort.Order> orders = sort.stream()
                .filter(order -> getCampos().containsKey(order.getProperty()))
                .map(order -> new Sort.Order(order.getDirection(), getCampos().get(order.getProperty())))
                .toList();

        return Sort.by(orders);
    }

    private static Map<String, String> getCampos() {
        return Map.of(
                "codigo", "codigo",
                "codigoPedido", "codigo",
                "nomeProduto", "produto.nome",
                "produtoNome", "produto.nome",
                "restaurante.nome", "restaurante.nome",
                "restauranteNome", "restaurante.nome",
                "nomeRestaurante", "restaurante.nome",
                "nomeCliente", "usuario.nome",
                "cliente.nome", "usuario.nome",
                "nomeUsuario", "usuario.nome"
        );
    }
}