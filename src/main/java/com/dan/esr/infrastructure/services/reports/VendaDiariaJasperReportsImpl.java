package com.dan.esr.infrastructure.services.reports;

import com.dan.esr.core.helper.LoggerHelper;
import com.dan.esr.domain.filter.VendaDiariaFiltro;
import com.dan.esr.domain.services.VendaDiariaQueryService;
import com.dan.esr.domain.services.VendaDiariaReportsService;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class VendaDiariaJasperReportsImpl implements VendaDiariaReportsService {
    private static final LoggerHelper logger = new LoggerHelper(VendaDiariaJasperReportsImpl.class);
    private final VendaDiariaQueryService vendaDiariaQueryService;

    @Override
    public byte[] emitirRelatorioVendasDiaria(VendaDiariaFiltro filtro, String timeOffset) {
        try {
            return gerarRelatorio(filtro, timeOffset);

        } catch (Exception ex) {
            logger.error("emitirRelatorioVendasDiaria(VendaDiariaFiltro filtro, String timeOffset) -> " +
                    "Erro: {}", ex.getLocalizedMessage(), ex);
            throw new RelatorioException("Ocorreu um erro ao tentar gerar o relatório de Vendas Diárias.", ex);
        }
    }

    private byte[] gerarRelatorio(VendaDiariaFiltro filtro, String timeOffset) throws JRException {
        var inputStream = this.getClass().getResourceAsStream("/reports/vendas-diaria.jasper");

        var parametros = new HashMap<String, Object>();
        parametros.put("REPORT_LOCALE", new Locale("pt", "BR"));

        var vendasDiarias = this.vendaDiariaQueryService.consultarVendasDiaria(filtro, timeOffset);
        var dataSource = new JRBeanCollectionDataSource(vendasDiarias);

        var jasperPrint = JasperFillManager.fillReport(inputStream, parametros, dataSource);
        return JasperExportManager.exportReportToPdf(jasperPrint);
    }
}