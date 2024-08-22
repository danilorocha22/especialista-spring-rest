package com.dan.esr.domain.repositories;

import com.dan.esr.domain.entities.FotoProduto;
import com.dan.esr.domain.entities.Produto;
import com.dan.esr.domain.entities.Restaurante;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ProdutoRepository extends
        ProdutoQueries,
        CustomBaseJpaRepository<Produto, Long> {

    @Query("from Produto p inner join fetch p.restaurante r left join fetch r.formasPagamento " +
            "where p.id = :produtoId and p.restaurante.id = :restauranteId")
    Optional<Produto> por(Long produtoId, Long restauranteId);

    //@Query("from Produto p where p.ativo = :ativo and p.restaurante = :restaurante")
    //Set<Produto> todosPor(@Param("ativo") boolean ativo, Restaurante restaurante);
    @Query("from Produto p where (:ativo IS NULL OR p.ativo = :ativo) and p.restaurante = :restaurante")
    List<Produto> todosPor(@Param("ativo") Boolean ativo, @Param("restaurante") Restaurante restaurante);

    @Query("select fp from FotoProduto fp join fp.produto p where p.restaurante.id = :restauranteId and " +
            "fp.produto.id = :produtoId")
    Optional<FotoProduto> findFotoBy(Long restauranteId, Long produtoId);
}