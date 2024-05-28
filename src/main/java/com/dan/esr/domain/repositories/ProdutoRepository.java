package com.dan.esr.domain.repositories;

import com.dan.esr.domain.entities.Produto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ProdutoRepository extends CustomBaseJpaRepository<Produto, Long> {

    @Query("from Produto p where p.id = :produtoId and p.restaurante.id = :restauranteId")
    Optional<Produto> buscarPor(Long produtoId,Long restauranteId);
}