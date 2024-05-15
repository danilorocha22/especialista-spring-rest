package com.dan.esr.domain.repositories;

import com.dan.esr.domain.entities.Restaurante;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RestauranteRepository extends
        RestauranteQueries,
        CustomBaseJpaRepository<Restaurante, Long>,
        JpaSpecificationExecutor<Restaurante> {

    void removeById(Long id);
}





/* Selecione todas as instâncias da entidade Restaurante, usando o alias r, e inclua
 os dados da associação cozinha (JOIN FETCH). Em seguida, faça um LEFT JOIN FETCH
 com a associação formasDePagamento, mesmo que não tenham formas de pagamento associadas,
 que neste caso serão retornados como null.*/
/*@NonNull
@Query("from Restaurante r join fetch r.cozinha left join fetch r.formasDePagamento")
List<Restaurante> findAll();*/

//List<Restaurante> findByTaxaFreteBetween(BigDecimal taxaInicial, BigDecimal taxaFinal);

//List<Restaurante> findByNomContainingAndCozinhaId(String nome, Long cozinhaId);

/*@Query("from Restaurante where nome like %:nome% and cozinha.id = :cozinhaId")
List<Restaurante> consultarPorNomeECozinhaId(String nome, Long cozinhaId);*/

//Optional<Restaurante> findFirstRestauranteByNomeContaining(String nome);


//List<Restaurante> findTop2ByNomeContaining(String nome);

//int countByCozinhaId(Long cozinhaId);


