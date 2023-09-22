package com.dan.esr.domain.repositories;

import com.dan.esr.domain.entities.Restaurante;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Repository
public class RestauranteRepositoryImpl implements RestauranteRepositoryQueries {

    @PersistenceContext
    private EntityManager entityManager;

    //Consulta dinâmica
    @Override
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
    }


    //Consulta não dinâmica
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
