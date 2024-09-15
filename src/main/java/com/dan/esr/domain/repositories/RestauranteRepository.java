package com.dan.esr.domain.repositories;

import com.dan.esr.domain.entities.FormaPagamento;
import com.dan.esr.domain.entities.Restaurante;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RestauranteRepository extends
        RestauranteQueries,
        CustomBaseJpaRepository<Restaurante, Long>,
        JpaSpecificationExecutor<Restaurante> {

    @Query("from Restaurante r left join fetch r.produtos p where r.id = :id order by p.id asc")
    Optional<Restaurante> buscarComProdutos(Long id);

    @Query("FROM Restaurante r LEFT JOIN FETCH r.usuariosResponsaveis u WHERE r.id = :id order by u.id asc")
    Optional<Restaurante> buscarComUsuariosResponsaveis(@Param("id") Long restauranteId);

    boolean existeResponsavel(Long restauranteId, Long usuarioId);
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


