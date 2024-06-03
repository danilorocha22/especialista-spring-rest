package com.dan.esr.api.controllers.estatisticas;

import com.dan.esr.domain.entities.dto.VendaDiaria;
import com.dan.esr.domain.filter.VendaDiariaFiltro;
import com.dan.esr.domain.services.VendaDiariaQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/estatisticas")
public class EstatisticasController {
    private final VendaDiariaQueryService vendaDiariaService;

    @GetMapping("/vendas-diaria")
    public List<VendaDiaria> consultarVendasDiaria(
            VendaDiariaFiltro filtro,
            @RequestParam(required = false, defaultValue = "+00:00") String timeOffset
    ) {
        return this.vendaDiariaService.consultarVendasDiaria(filtro, timeOffset);
    }
}