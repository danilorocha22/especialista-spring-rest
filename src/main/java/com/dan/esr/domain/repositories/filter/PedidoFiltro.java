package com.dan.esr.domain.repositories.filter;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.OffsetDateTime;

import static org.springframework.format.annotation.DateTimeFormat.ISO.DATE_TIME;

@Getter
@Setter
@ToString
public class PedidoFiltro {
    private Long clienteId, restauranteId;

    @DateTimeFormat(iso = DATE_TIME)
    private OffsetDateTime dataCriacaoDe, dataCriacaoAte;
}