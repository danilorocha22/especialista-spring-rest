package com.dan.esr.infrastructure.repositories.impl;

import com.dan.esr.core.util.LoggerHelper;
import com.dan.esr.domain.repositories.CustomBaseJpaRepository;
import jakarta.persistence.EntityManager;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import java.util.List;
import java.util.Optional;

public class CustomBaseJpaRepositoryImpl<T, ID>
        extends SimpleJpaRepository<T, ID> implements CustomBaseJpaRepository<T, ID> {

    private static final LoggerHelper logger = new LoggerHelper(CustomBaseJpaRepositoryImpl.class);
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
    public void remover(Long id) {
        var jpql = "DELETE FROM %s WHERE id = :id"
                .formatted(getDomainClass().getName());

        this.entityManager.createQuery(jpql)
                .setParameter("id", id)
                .executeUpdate();
    }

    @Override
    public void remover(T t) {
        this.entityManager.remove(t);
    }

    @Override
    public Optional<T> buscarPorId(Long id) {
        var jpql = "FROM %s WHERE id = :id"
                .formatted(getDomainClass().getSimpleName());

        T entity = this.entityManager.createQuery(jpql, getDomainClass())
                .setParameter("id", id)
                .getSingleResult();

        return Optional.of(entity);
    }

    @Override
    public Optional<T> buscarPrimeira() {
        T entity = this.entityManager.createQuery(getJpqlTodos(), getDomainClass())
                .setMaxResults(1)
                .getSingleResult();

        return Optional.ofNullable(entity);
    }

    @Override
    public List<T> buscarPorNomeSemelhante(String nome) {
        return this.entityManager.createQuery(getJpqlNomeContendo(), getDomainClass())
                .setParameter("nome", "%" + nome + "%")
                .getResultList();
    }

    @Override
    public Optional<T> buscarPrimeiraComNomeContendo(String nome) {
        T entity = this.entityManager.createQuery(getJpqlNomeContendo(), getDomainClass())
                .setParameter("nome", "%" + nome + "%")
                .setMaxResults(1)
                .getSingleResult();

        return Optional.ofNullable(entity);
    }

    @Override
    public Optional<T> buscarPorNomeIgual(String nome) {
        T entity = this.entityManager.createQuery(getJpqlNomeIgual(), getDomainClass())
                .setParameter("nome", nome)
                .getSingleResult();

        return Optional.ofNullable(entity);
    }

    @Override
    public List<T> buscarTodos() {
        return this.entityManager.createQuery(getJpqlTodos(), getDomainClass())
                .getResultList();
    }

    @Override
    public List<T> buscarTop2ComNomeContendo(String nome) {
        return this.entityManager.createQuery(getJpqlNomeContendo(), getDomainClass())
                .setParameter("nome", "%" + nome + "%")
                .setMaxResults(2)
                .getResultList();
    }

    @Override
    public boolean existeRegistroCom(String nome) {
        return this.entityManager.createQuery(getJpqlNomeIgual(), getDomainClass())
                .setParameter("nome", nome)
                .getSingleResult() != null;
    }

    private String getJpqlNomeContendo() {
        return "FROM %s WHERE nome LIKE :nome".formatted(getDomainClass().getName());
    }

    private String getJpqlNomeIgual() {
        return "FROM %s WHERE nome = :nome".formatted(getDomainClass().getName());
    }

    private String getJpqlTodos() {
        return "FROM %s".formatted(getDomainClass().getName());
    }
}