package com.dan.esr.domain.entities.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class VendaDiaria {
    private LocalDate dataVenda;
    private Long quantidadeVendas;
    private BigDecimal valorTotal;
}