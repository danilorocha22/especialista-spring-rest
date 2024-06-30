package com.dan.esr.domain.repositories;

import com.dan.esr.domain.entities.FormaPagamento;
import org.springframework.data.jpa.repository.Query;

import java.time.OffsetDateTime;
import java.util.Optional;

public interface FormaPagamentoRepository extends CustomBaseJpaRepository<FormaPagamento, Long> {

    @Query("select max(dataAtualizacao) from FormaPagamento")
    Optional<OffsetDateTime> getDataAtualizacaoMaisRecente();
}