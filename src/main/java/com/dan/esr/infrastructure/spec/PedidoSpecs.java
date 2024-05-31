package com.dan.esr.infrastructure.spec;

import com.dan.esr.domain.entities.Produto;
import com.dan.esr.domain.entities.Restaurante;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public class PedidoSpecs {

    /*public static Specification<Restaurante> todos() {
        return (root, query, builder) -> {
            root.fetch("cozinha", JoinType.LEFT);
            root.fetch("formasDePagamento", JoinType.LEFT);
            query.distinct(true);
            query.getOrderList();
            return null;
        };
    }*/

    public static Specification<Restaurante> comFreteGratis() {
        return (root, query, builder) -> builder.equal(root.get("taxaFrete"), BigDecimal.ZERO);
    }

    public static Specification<Restaurante> comNomeSemelhante(String nome) {
        return (root, query, builder) -> builder.like(root.get("nome"), "%" + nome + "%");
    }

    public static Specification<Restaurante> comProdutosAtivos(Long id) {
        return (root, query, builder) -> {
            Join<Restaurante, Produto> joinProdutos = root.join("produtos", JoinType.LEFT);
            root.fetch("produtos", JoinType.LEFT);
            query.distinct(true);

            return builder.or(
                    builder.equal(root.get("id"), id),
                    builder.isTrue(joinProdutos.get("ativo"))
            );
        };
    }

    public static Specification<Restaurante> comTaxaFreteEntre(BigDecimal taxaInicial, BigDecimal taxaFinal) {
        return (root, query, builder) -> builder.between(root.get("taxaFrete"), taxaInicial, taxaFinal);
    }

    public static Specification<Restaurante> comCozinhaId(Long cozinhaId) {
        return ((root, query, builder) -> builder.equal(root.get("cozinha").get("id"), cozinhaId));
    }

    public static Specification<Restaurante> countByCozinhaId(Long cozinhaId) {
        return ((root, query, builder) -> builder.equal(root.join("cozinha").get("id"), cozinhaId));
    }


}
