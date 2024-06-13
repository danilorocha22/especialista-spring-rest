package com.dan.esr.infrastructure.repositories.impl;

import com.dan.esr.core.util.LoggerHelper;
import com.dan.esr.domain.entities.FotoProduto;
import com.dan.esr.domain.exceptions.NegocioException;
import com.dan.esr.domain.repositories.ProdutoQueries;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public class ProdutoRepositoryImpl implements ProdutoQueries {
    private static final LoggerHelper logger = new LoggerHelper(ProdutoRepositoryImpl.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @Override
    public Optional<FotoProduto> salvarOuAtualizar(FotoProduto foto) {
        try {
            return Optional.ofNullable(this.entityManager.merge(foto));

        } catch (DataIntegrityViolationException ex) {
            logger.error("salvarOuAtualizar(FotoProduto foto) -> Erro: {}", ex.getLocalizedMessage(), ex);
            throw new NegocioException("A foto %s do produto %s, já existe no cadastro."
                    .formatted(foto.getDescricao(), foto.getProduto().getNome()));
        }
    }

    @Transactional
    @Override
    public void removerFoto(FotoProduto foto) {
        this.entityManager.remove(foto);
        /*var criteria = this.entityManager.getCriteriaBuilder();
        var delete = criteria.createCriteriaDelete(FotoProduto.class);
        var root = delete.from(FotoProduto.class);
        delete.where(criteria.equal(root.get("id"), fotoId));
        this.entityManager.createQuery(delete).executeUpdate();*/
    }

    @Override
    public boolean existeFoto(Long fotoId) {
        var criteria = this.entityManager.getCriteriaBuilder();
        var query = criteria.createQuery(Long.class);
        var root = query.from(FotoProduto.class);
        query.select(criteria.count(root));
        query.where(criteria.equal(root.get("id"), fotoId));
        Long count = entityManager.createQuery(query).getSingleResult();
        return count > 0;
    }
}