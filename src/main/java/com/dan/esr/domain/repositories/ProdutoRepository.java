package com.dan.esr.domain.repositories;

import com.dan.esr.domain.entities.Produto;
import com.dan.esr.domain.entities.Restaurante;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.Set;

public interface ProdutoRepository extends CustomBaseJpaRepository<Produto, Long> {

    @Query("from Produto p where p.id = :produtoId and p.restaurante.id = :restauranteId")
    Optional<Produto> por(Long produtoId, Long restauranteId);

    //@Query("from Produto p where p.ativo = :ativo and p.restaurante = :restaurante")
    //Set<Produto> todosPor(@Param("ativo") boolean ativo, Restaurante restaurante);
    @Query("from Produto p where (:ativo IS NULL OR p.ativo = :ativo) and p.restaurante = :restaurante")
    Set<Produto> todosPor(@Param("ativo") Boolean ativo, @Param("restaurante") Restaurante restaurante);
}