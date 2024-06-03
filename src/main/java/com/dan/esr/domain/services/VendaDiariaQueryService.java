package com.dan.esr.domain.services;

import com.dan.esr.domain.entities.dto.VendaDiaria;
import com.dan.esr.domain.filter.VendaDiariaFiltro;

import java.util.List;

public interface VendaDiariaQueryService {

    List<VendaDiaria> consultarVendasDiaria(VendaDiariaFiltro filtro, String timeOffset);
}