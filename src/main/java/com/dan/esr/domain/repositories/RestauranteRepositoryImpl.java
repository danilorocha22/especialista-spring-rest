package com.dan.esr.domain.repositories;

import com.dan.esr.domain.entities.Restaurante;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class RestauranteRepositoryImpl implements RestauranteRepositoryQueries {

    @PersistenceContext
    private EntityManager entityManager;

    //Consulta dinâmica com API Criteria
    @Override
    public List<Restaurante> find(String nome, BigDecimal freteInicial, BigDecimal freteFinal) {
        var criteria = getBuilder().createQuery(Restaurante.class);
        var root = criteria.from(Restaurante.class);
        var parametros = definirParametros(nome, freteInicial, freteFinal, root);
        criteria.where(parametros.toArray(new Predicate[0]));
        var query = entityManager.createQuery(criteria);
        return query.getResultList();
    }

    private CriteriaBuilder getBuilder() {
        return entityManager.getCriteriaBuilder();
    }

    private ArrayList<Predicate> definirParametros(
            String nome, BigDecimal freteInicial, BigDecimal freteFinal, Root<?> root) {
        return new ArrayList<>() {{
            if (StringUtils.hasText(nome))
                add(getBuilder().like(root.get("nome"), "%" + nome + "%"));
            if (Objects.nonNull(freteInicial))
                add(getBuilder().greaterThanOrEqualTo(root.get("taxaFrete"), freteInicial));
            if (Objects.nonNull(freteFinal))
                add(getBuilder().lessThanOrEqualTo(root.get("taxaFrete"), freteFinal));
        }};
    }

    //Consulta dinâmica com JPQL
    /*@Override
    public List<Restaurante> find(String nome, BigDecimal freteInicial, BigDecimal freteFinal) {
        var jpql = new StringBuilder();
        var parametros = new HashMap<String, Object>();

        jpql.append("from Restaurante where 0 = 0 ");

        if (StringUtils.hasLength(nome)) {
            jpql.append("and nome like :nome ");
            parametros.put("nome", "%" + nome + "%");
        }

        if (Objects.nonNull(freteInicial)) {
            jpql.append("and taxaFrete >= :freteInicial ");
            parametros.put("freteInicial", freteInicial);
        }

        if (Objects.nonNull(freteFinal)) {
            jpql.append("and taxaFrete <= :freteFinal");
            parametros.put("freteFinal", freteFinal);
        }

        TypedQuery<Restaurante> typedQuery = entityManager.createQuery(jpql.toString(), Restaurante.class);
        parametros.forEach(typedQuery::setParameter);
        return typedQuery.getResultList();
    }*/


    //Consulta não dinâmica com JPQL
    /*@Override
    public List<Restaurante> find(String nome, BigDecimal freteInicial, BigDecimal freteFinal) {

        var jpql = "from Restaurante where nome like :nome and taxaFrete between :freteInicial and :freteFinal";

        return entityManager.createQuery(jpql, Restaurante.class)
                .setParameter("nome", "%" + nome + "%")
                .setParameter("freteInicial", freteInicial)
                .setParameter("freteFinal", freteFinal)
                .getResultList();
    }*/
}
