package com.dan.esr.infrastructure.repositories.impl;

import com.dan.esr.domain.entities.Cidade;
import com.dan.esr.domain.entities.Endereco;
import com.dan.esr.domain.entities.Restaurante;
import com.dan.esr.domain.exceptions.PersistenciaException;
import com.dan.esr.domain.exceptions.restaurante.RestauranteNaoEncontradoException;
import com.dan.esr.domain.repositories.RestauranteQueries;
import com.dan.esr.domain.repositories.RestauranteRepository;
import com.dan.esr.infrastructure.repositories.spec.RestauranteSpecs;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.dan.esr.core.util.MensagensUtil.MSG_ERRO_BANCO_DE_DADOS;
import static com.dan.esr.infrastructure.repositories.spec.RestauranteSpecs.*;
import static java.util.Comparator.comparing;

@Slf4j
@Repository
public class RestauranteRepositoryImpl implements RestauranteQueries {

    @PersistenceContext
    private EntityManager entityManager;

    @Lazy //Esta anotação resolve o problema da dependência circular
    @Autowired
    private RestauranteRepository restauranteRepository;

    /*@Autowired
    private ProdutoModelAssembler produtoModelAssembler;

    @Autowired
    private RestauranteEntityAssembler restauranteEntityAssembler;*/

    /*###################################### CONSULTAS ######################################*/

    @Override
    public List<Restaurante> buscarComNomeContendoEfreteGratis(String nome) {
        return restauranteRepository.findAll(comNomeSemelhante(nome).and(comFreteGratis()));
    }

    @Override
    public List<Restaurante> buscarComFreteEntre(BigDecimal taxaInicial, BigDecimal taxaFinal) {
        return restauranteRepository.findAll(comTaxaFreteEntre(taxaInicial, taxaFinal));
    }

    /*@Override
    public Optional<Restaurante> buscarRestauranteComProdutos(Long restauranteId) {
        String jpql = "SELECT new com.dan.esr.api.models.output.restaurante.RestauranteProdutosOutput(r.id, r.nome) " +
                "FROM Restaurante r WHERE r.id = :id";

        List<Produto> produtos = entityManager.createQuery("SELECT p FROM Produto p " +
                        "WHERE p.restaurante.id = :id AND p.ativo = true", Produto.class)
                .setParameter("id", restauranteId)
                .getResultList();

        try {
            RestauranteProdutosOutput restauranteOutput = entityManager.createQuery(jpql,
                            RestauranteProdutosOutput.class)
                    .setParameter("id", restauranteId)
                    .getSingleResult();

            List<ProdutoOutput> produtoOutputs = this.produtoModelAssembler.toCollectionDTO(produtos);
            restauranteOutput.setProdutos(produtoOutputs);
            return Optional.ofNullable(this.restauranteEntityAssembler.toDomain(restauranteOutput));

        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }*/

    @Override
    public List<Restaurante> buscarComNomeContendoEcozinhaId(String nome, Long cozinhaId) {
        try {
            return restauranteRepository.findAll(comNomeSemelhante(nome).and(comCozinhaId(cozinhaId)));

        } catch (Exception ex) {
            log.error("buscarNomeContendoEcozinhaId(nome, cozinhaId) -> Erro: {}", ex.getLocalizedMessage());
            throw new PersistenciaException(MSG_ERRO_BANCO_DE_DADOS);
        }
    }

    @Override
    public Optional<Restaurante> porId(Long id) {
        try {
            var jpql = ("FROM %s r " +
                    "JOIN FETCH r.cozinha " +
                    "LEFT JOIN FETCH r.formasPagamento " +
                    "LEFT JOIN FETCH r.endereco e " +
                    "LEFT JOIN FETCH e.cidade c " +
                    "LEFT JOIN FETCH c.estado " +
                    "LEFT JOIN FETCH r.usuariosResponsaveis " +
                    "WHERE r.id = :id").formatted(Restaurante.class.getSimpleName());

            Restaurante restaurante = this.entityManager.createQuery(jpql, Restaurante.class)
                    .setParameter("id", id)
                    .getSingleResult();

            return Optional.ofNullable(restaurante);

        } catch (NoResultException ex) {
            log.error("buscarComFormasPagamento(id) -> Erro: {}", ex.getLocalizedMessage());
            throw new RestauranteNaoEncontradoException(id);

        } catch (Exception ex) {
            log.error("buscarComFormasPagamento(id) -> Erro: {}", ex.getLocalizedMessage());
            throw new PersistenciaException(MSG_ERRO_BANCO_DE_DADOS);
        }
    }

    @Override
    public int countByCozinhaId(Long cozinhaId) {
        try {
            return (int) restauranteRepository.count(RestauranteSpecs.countByCozinhaId(cozinhaId));
        } catch (Exception ex) {
            log.error("countByCozinhaId(cozinhaId) -> Erro: {}", ex.getLocalizedMessage());
            throw new PersistenciaException(MSG_ERRO_BANCO_DE_DADOS);
        }
    }

    @Override
    public List<Restaurante> todos() {
        var criteria = getCriteria();
        var root = criteria.from(Restaurante.class);

        root.fetch("cozinha", JoinType.INNER);
        root.fetch("formasPagamento", JoinType.LEFT);
        Fetch<Restaurante, Endereco> enderecoFetch = root.fetch("endereco", JoinType.LEFT);
        Fetch<Endereco, Cidade> cidadeFetch = enderecoFetch.fetch("cidade", JoinType.LEFT);
        cidadeFetch.fetch("estado", JoinType.LEFT);
        criteria.select(root);
        List<Restaurante> restaurantes = entityManager.createQuery(criteria).getResultList();
        restaurantes.sort(comparing(Restaurante::getId));

        return restaurantes;
    }

    //Consulta dinâmica com API Criteria
    @Override
    public List<Restaurante> buscar(String nome, BigDecimal freteInicial, BigDecimal freteFinal) {
        var criteria = getCriteria();
        var root = criteria.from(Restaurante.class);
        var parametros = configurarParametrosDaBusca(nome, freteInicial, freteFinal, root);
        criteria.where(parametros.toArray(new Predicate[0]));
        var query = entityManager.createQuery(criteria);
        return query.getResultList();
    }

    private ArrayList<Predicate> configurarParametrosDaBusca(
            String nome,
            BigDecimal freteInicial,
            BigDecimal freteFinal,
            Root<?> root
    ) {
        return new ArrayList<>() {{
            if (StringUtils.hasText(nome))
                add(getBuilder().like(root.get("nome"), "%" + nome + "%"));
            if (Objects.nonNull(freteInicial))
                add(getBuilder().greaterThanOrEqualTo(root.get("taxaFrete"), freteInicial));
            if (Objects.nonNull(freteFinal))
                add(getBuilder().lessThanOrEqualTo(root.get("taxaFrete"), freteFinal));
        }};
    }

    private CriteriaBuilder getBuilder() {
        return entityManager.getCriteriaBuilder();
    }

    private CriteriaQuery<Restaurante> getCriteria() {
        return getBuilder().createQuery(Restaurante.class);
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
