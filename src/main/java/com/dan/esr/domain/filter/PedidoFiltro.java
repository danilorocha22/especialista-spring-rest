package com.dan.esr.domain.filter;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.OffsetDateTime;

import static org.springframework.format.annotation.DateTimeFormat.ISO.DATE_TIME;

@Getter
@Setter
public class PedidoFiltro {
    private Long clienteId, restauranteId;

    @DateTimeFormat(iso = DATE_TIME)
    private OffsetDateTime dataCriacaoDe, dataCriacaoAte;
}