package com.dan.esr.domain.repositories;

import com.dan.esr.domain.entities.Cozinha;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CozinhaRepository extends JpaRepository<Cozinha, Long> {

    Optional<Cozinha> findByNome(String nome);

    List<Cozinha> findCozinhasByNomeContains(String nome);

    boolean existsByNome(String nome);

}
