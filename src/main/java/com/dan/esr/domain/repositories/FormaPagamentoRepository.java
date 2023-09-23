package com.dan.esr.domain.repositories;

import com.dan.esr.domain.entities.FormasDePagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FormaPagamentoRepository extends JpaRepository<FormasDePagamento, Long> {
}
