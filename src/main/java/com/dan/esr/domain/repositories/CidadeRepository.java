package com.dan.esr.domain.repositories;

import com.dan.esr.domain.entities.Cidade;
import org.springframework.stereotype.Repository;

@Repository
public interface CidadeRepository extends CustomJpaRepository<Cidade, Long> {
}
