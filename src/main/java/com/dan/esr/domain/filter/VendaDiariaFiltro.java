package com.dan.esr.domain.filter;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Date;

import static org.springframework.format.annotation.DateTimeFormat.ISO.DATE_TIME;

@Getter
@Setter
public class VendaDiariaFiltro {
    private Long restauranteId;

    @DateTimeFormat(iso = DATE_TIME)
    private LocalDate dataCriacaoDe, dataCriacaoAte;
}