package com.dan.esr.domain.repositories;

import com.dan.esr.domain.entities.Cozinha;

public interface CozinhaRepository extends
        CustomBaseJpaRepository<Cozinha, Long> {

    void removeById(Long id);

    //Optional<Cozinha> findByNome(String nome);

    //List<Cozinha> findCozinhasByNomeContains(String nome);

    //boolean existsByNome(String nome);

}
