package com.dan.esr.infrastructure.services.queries;

import com.dan.esr.domain.entities.Pedido;
import com.dan.esr.domain.entities.dto.VendaDiaria;
import com.dan.esr.domain.filter.VendaDiariaFiltro;
import com.dan.esr.domain.services.VendaDiariaQueryService;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.dan.esr.domain.entities.enums.StatusPedido.CONFIRMADO;
import static com.dan.esr.domain.entities.enums.StatusPedido.ENTREGUE;
import static java.util.Objects.nonNull;

@Repository
public class VendaDiariaQueryImpl implements VendaDiariaQueryService {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<VendaDiaria> consultarVendasDiaria(VendaDiariaFiltro filtro, String timeOffset) {
        var builder = this.entityManager.getCriteriaBuilder();
        var query = builder.createQuery(VendaDiaria.class);
        var root = query.from(Pedido.class);
        var dataCriacao = functionDataCriacao(builder, root, timeOffset);
        var selection = getConstruct(builder, dataCriacao, root);
        var predicates = getPredicates(builder, root, filtro).toArray(new Predicate[0]);

        query.select(selection);
        query.where(predicates);
        query.groupBy(dataCriacao);

        return this.entityManager.createQuery(query).getResultList();
    }

    private static Expression<LocalDate> functionDataCriacao(
            CriteriaBuilder builder,
            Root<Pedido> root,
            String timeOffset
    ) {
        return builder.function(
                "date",
                LocalDate.class,
                functionConvertTzDataCriacao(builder, root, timeOffset)
        );
    }

    private static Expression<LocalDate> functionConvertTzDataCriacao(
            CriteriaBuilder builder,
            Root<Pedido> root,
            String timeOffset
    ) {
        return builder.function(
                "convert_tz",
                LocalDate.class,
                root.get("dataCriacao"),
                builder.literal("+00:00"),
                builder.literal(timeOffset)
        );
    }

    private static CompoundSelection<VendaDiaria> getConstruct(
            CriteriaBuilder builder,
            Expression<LocalDate> dataCriacao,
            Root<Pedido> root
    ) {
        return builder.construct(
                VendaDiaria.class,
                dataCriacao,
                builder.count(root.get("id")),
                builder.sum(root.get("valorTotal"))
        );
    }

    private static List<Predicate> getPredicates(
            CriteriaBuilder builder,
            Root<Pedido> root,
            VendaDiariaFiltro filtro
    ) {
        return new ArrayList<>() {{
            if (nonNull(filtro.getRestauranteId()))
                add(builder.equal(root.get("restaurante").get("id"), filtro.getRestauranteId()));

            if (nonNull(filtro.getDataCriacaoDe()))
                add(builder.greaterThanOrEqualTo(root.get("dataCriacao"), filtro.getDataCriacaoDe()));

            if (nonNull(filtro.getDataCriacaoAte()))
                add(builder.lessThanOrEqualTo(root.get("dataCriacao"), filtro.getDataCriacaoAte()));

            add(root.get("status").in(CONFIRMADO, ENTREGUE));
        }};
    }
}