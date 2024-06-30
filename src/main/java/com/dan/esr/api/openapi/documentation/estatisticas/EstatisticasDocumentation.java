package com.dan.esr.api.openapi.documentation.estatisticas;

import com.dan.esr.domain.entities.dto.VendaDiaria;
import com.dan.esr.domain.filter.VendaDiariaFiltro;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Api(tags = "Estatísticas")
public interface EstatisticasDocumentation {

    @ApiOperation("Consulta estatísticas de vendas diárias")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "restauranteId", value = "ID do restaurante", example = "1", dataType = "int"),
            @ApiImplicitParam(name = "dataCriacaoDe", value = "Data/hora inicial da criação do pedido",
                    example = "2019-12-01T00:00:00Z", dataType = "date-time"),
            @ApiImplicitParam(name = "dataCriacaoDe", value = "Data/hora final da criação do pedido",
                    example = "2019-12-02T23:59:59Z", dataType = "date-time")
    })
    List<VendaDiaria> consultarVendasDiaria(
            VendaDiariaFiltro filtro,
            @ApiParam(value = "Deslocamento de horário a ser considerado na consulta em relação ao horário do servidor",
                    defaultValue = "+00:00")
            String timeOffset
    );

    ResponseEntity<byte[]> consultarVendasDiariaPdf(
            VendaDiariaFiltro filtro,
            String timeOffset
    );
}
