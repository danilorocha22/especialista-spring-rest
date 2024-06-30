package com.dan.esr.api.openapi.models;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PageModelOpenApi<T> {

    private List<T> content;

    @ApiModelProperty(value = "Quantidade de registros por página", example = "10")
    private Long size;

    @ApiModelProperty(value = "Total de registros", example = "50")
    private Long totalElements;

    @ApiModelProperty(value = "Total de páginas", example = "5")
    private Long totalPages;

    @ApiModelProperty(value = "Número da página (começa em 0)", example = "0")
    private Long number;
}