package com.dan.esr.domain.repositories;

import com.dan.esr.domain.entities.Cidade;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CidadeRepository extends CustomBaseJpaRepository<Cidade, Long> {

    @Query("from Cidade c inner join fetch c.estado where c.id = :id")
    Optional<Cidade> por(Long id);
}
