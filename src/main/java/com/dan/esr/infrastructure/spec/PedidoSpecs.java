package com.dan.esr.infrastructure.spec;

import com.dan.esr.domain.entities.Pedido;
import com.dan.esr.domain.repositories.filter.PedidoFiltro;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.nonNull;

public class PedidoSpecs {

    public static Specification<Pedido> filtrar(PedidoFiltro filtro) {
        return (root, query, builder) -> {
            configurarFetchs(root);
            var predicates = new ArrayList<>(getPredicates(filtro, root, builder));
            return builder.and(predicates.toArray(new Predicate[0]));
        };
    }

    private static void configurarFetchs(Root<Pedido> root) {
        root.fetch("usuario");
        root.fetch("itensPedido");
        root.fetch("formaPagamento");
        root.fetch("endereco").fetch("cidade").fetch("estado");
        root.fetch("restaurante").fetch("produtos").fetch("restaurante")
                .fetch("cozinha");
    }

    private static List<Predicate> getPredicates(
            PedidoFiltro filtro,
            Root<Pedido> root,
            CriteriaBuilder builder
    ) {
        return new ArrayList<>() {{
            if (nonNull(filtro.getClienteId()))
                add(builder.equal(root.get("usuario").get("id"), filtro.getClienteId()));

            if (nonNull(filtro.getRestauranteId()))
                add(builder.equal(root.get("restaurante").get("id"), filtro.getRestauranteId()));

            if (nonNull(filtro.getDataCriacaoDe()))
                add(builder.greaterThanOrEqualTo(root.get("dataCriacao"), filtro.getDataCriacaoDe()));

            if (nonNull(filtro.getDataCriacaoAte()))
                add(builder.lessThanOrEqualTo(root.get("dataCriacao"), filtro.getDataCriacaoAte()));

        }};
    }
}