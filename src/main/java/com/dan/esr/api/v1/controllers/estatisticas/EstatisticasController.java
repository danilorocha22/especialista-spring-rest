package com.dan.esr.api.v1.controllers.estatisticas;

import com.dan.esr.api.v1.openapi.documentation.estatisticas.EstatisticasDocumentation;
import com.dan.esr.domain.entities.dto.VendaDiaria;
import com.dan.esr.domain.filter.VendaDiariaFiltro;
import com.dan.esr.domain.services.VendaDiariaQueryService;
import com.dan.esr.domain.services.VendaDiariaReportsService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.dan.esr.api.v1.links.Links.linkToEstatisticasVendasDiarias;
import static org.springframework.http.HttpHeaders.CONTENT_DISPOSITION;
import static org.springframework.http.MediaType.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/estatisticas")
public class EstatisticasController implements EstatisticasDocumentation {
    private final VendaDiariaQueryService vendaDiariaService;
    private final VendaDiariaReportsService reportsService;

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public EstatisticasModel estatisticas() {
        var estatisticasModel = new EstatisticasModel();
        estatisticasModel.add(linkToEstatisticasVendasDiarias());
        return estatisticasModel;
    }

    @Override
    @GetMapping(path = "/v1/vendas-diaria", produces = APPLICATION_JSON_VALUE)
    public List<VendaDiaria> consultarVendasDiaria(
            VendaDiariaFiltro filtro,
            @RequestParam(required = false, defaultValue = "+00:00") String timeOffset
    ) {
        return this.vendaDiariaService.consultarVendasDiaria(filtro, timeOffset);
    }

    @Override
    @GetMapping(path = "/vendas-diaria", produces = APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> consultarVendasDiariaPdf(
            VendaDiariaFiltro filtro,
            @RequestParam(required = false, defaultValue = "+00:00") String timeOffset
    ) {
        byte[] bytesPdf = this.reportsService.emitirRelatorioVendasDiaria(filtro, timeOffset);
        return ResponseEntity.ok()
                .contentType(APPLICATION_PDF)
                .headers(getHeaders())
                .body(bytesPdf);
    }

    private static HttpHeaders getHeaders() {
        var headers = new HttpHeaders();
        // attachment: faz o download do arquivo ao invés de abrir no navegador;
        headers.add(CONTENT_DISPOSITION, "attachment; filename=vendas-diaria.pdf");
        return headers;
    }

    public static class EstatisticasModel extends RepresentationModel<EstatisticasModel> {
    }
}