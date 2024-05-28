package com.dan.esr.infrastructure.repositories.impl;

import com.dan.esr.domain.repositories.CustomBaseJpaRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import java.util.List;
import java.util.Optional;

public class CustomBaseJpaRepositoryImpl<T, ID>
        extends SimpleJpaRepository<T, ID> implements CustomBaseJpaRepository<T, ID> {

    private final EntityManager entityManager;

    public CustomBaseJpaRepositoryImpl(
            JpaEntityInformation<T, ?> entityInformation,
            EntityManager entityManager
    ) {
        super(entityInformation, entityManager);
        this.entityManager = entityManager;
    }

    @Override
    public Optional<T> salvarOuAtualizar(T t) {
        T entity = this.entityManager.merge(t);
        return Optional.ofNullable(entity);
    }

    @Override
    public Optional<Integer> remover(Long id) {
        String jpql = "DELETE FROM " + nomeEntidade() + " e WHERE e.id = :id";
        int quantosRegistrosExcluidos = this.entityManager.createQuery(jpql)
                .setParameter("id", id)
                .executeUpdate();

        if (quantosRegistrosExcluidos == 0) {
            return Optional.empty();
        } else {
            return Optional.of(quantosRegistrosExcluidos);
        }
    }

    /*@Override
    public void remover(T t) {
        this.entityManager.remove(t);
    }*/

    @Override
    public Optional<T> buscarPor(Long id) {
        try {
            var jpql = "FROM %s WHERE id = :id".formatted(nomeEntidade());
            T entity = this.entityManager.createQuery(jpql, getDomainClass())
                    .setParameter("id", id)
                    .getSingleResult();
            return Optional.of(entity);

        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<T> primeiro() {
        try {
            T entity = this.entityManager.createQuery(jpqlTodos(), getDomainClass())
                    .setMaxResults(1)
                    .getSingleResult();
            return Optional.ofNullable(entity);

        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<T> primeiroComNomeSemelhante(String nome) {
        try {
            T entity = this.entityManager.createQuery(jpqlNomeSemelhante(), getDomainClass())
                    .setParameter("nome", "%" + nome + "%")
                    .setMaxResults(1)
                    .getSingleResult();
            return Optional.ofNullable(entity);

        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<T> comNomeIgual(String nome) {
        try {
            String jpql = "FROM %s e WHERE LOWER(e.nome) = LOWER(:nome)"
                    .formatted(nomeEntidade());

            T entity = this.entityManager.createQuery(jpql, getDomainClass())
                    .setParameter("nome", nome.trim().toLowerCase())
                    .getSingleResult();

            return Optional.ofNullable(entity);

        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }

    @Override
    public List<T> todos() {
        return this.entityManager.createQuery(jpqlTodos(), getDomainClass())
                .getResultList();
    }

    @Override
    public List<T> comNomeSemelhante(String nome) {
        return this.entityManager.createQuery(jpqlNomeSemelhante(), getDomainClass())
                .setParameter("nome", "%" + nome + "%")
                .getResultList();
    }

    @Override
    public List<T> top2ComNomeSemelhante(String nome) {
        return this.entityManager.createQuery(jpqlNomeSemelhante(), getDomainClass())
                .setParameter("nome", "%" + nome + "%")
                .setMaxResults(2)
                .getResultList();
    }

    private String jpqlNomeSemelhante() {
        return "FROM %s WHERE nome LIKE :nome".formatted(nomeEntidade());
    }

    private String jpqlTodos() {
        return "FROM %s".formatted(nomeEntidade());
    }

    private String nomeEntidade() {
        return getDomainClass().getSimpleName();
    }
}