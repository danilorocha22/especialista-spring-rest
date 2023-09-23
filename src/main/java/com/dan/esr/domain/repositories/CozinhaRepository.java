package com.dan.esr.domain.repositories;

import com.dan.esr.domain.entities.Cozinha;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CozinhaRepository extends CustomJpaRepository<Cozinha, Long> {

    Optional<Cozinha> findByNome(String nome);

    List<Cozinha> findCozinhasByNomeContains(String nome);

    boolean existsByNome(String nome);

}
