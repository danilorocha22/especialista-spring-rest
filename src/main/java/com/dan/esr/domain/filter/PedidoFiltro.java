package com.dan.esr.domain.filter;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.OffsetDateTime;

import static org.springframework.format.annotation.DateTimeFormat.ISO.DATE_TIME;

@Getter
@Setter
public class PedidoFiltro {

    @ApiModelProperty(value = "ID de um cliente para filtro da pesquisa", example = "1")
    private Long clienteId;

    @ApiModelProperty(value = "ID de um restaurante para filtro da pesquisa", example = "2")
    private Long restauranteId;

    @DateTimeFormat(iso = DATE_TIME)
    @ApiModelProperty(example = "2019-10-30T00:00:00Z", value = "Data/hora de criação inicial para filtro da pesquisa")
    private OffsetDateTime dataCriacaoDe;

    @DateTimeFormat(iso = DATE_TIME)
    @ApiModelProperty(example = "2019-11-01T10:00:00Z", value = "Data/hora de criação final para filtro da pesquisa")
    private OffsetDateTime dataCriacaoAte;
}