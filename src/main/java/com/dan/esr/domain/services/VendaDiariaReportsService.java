package com.dan.esr.domain.services;

import com.dan.esr.domain.filter.VendaDiariaFiltro;

public interface VendaDiariaReportsService {

    byte[] emitirRelatorioVendasDiaria(VendaDiariaFiltro filtro, String timeOffset);
}