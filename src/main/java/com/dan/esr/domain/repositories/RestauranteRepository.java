package com.dan.esr.domain.repositories;

import com.dan.esr.domain.entities.Restaurante;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface RestauranteRepository extends CustomJpaRepository<Restaurante, Long>, RestauranteRepositoryQueries,
        JpaSpecificationExecutor<Restaurante> {

    List<Restaurante> findByTaxaFreteBetween(BigDecimal taxaInicial, BigDecimal taxaFinal);

    //List<Restaurante> findByNomContainingAndCozinhaId(String nome, Long cozinhaId);

    //@Query("from Restaurante where nome like %:nome% and cozinha.id = :cozinhaId")
    List<Restaurante> consultarPorNomeECozinhaId(String nome, Long cozinhaId);

    Optional<Restaurante> findFirstRestauranteByNomeContaining(String nome);

    List<Restaurante> findTop2ByNomeContaining(String nome);

    int countByCozinhaId(Long cozinhaId);

}
