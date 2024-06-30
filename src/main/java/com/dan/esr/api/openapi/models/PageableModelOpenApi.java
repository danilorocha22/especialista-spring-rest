package com.dan.esr.api.openapi.models;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@ApiModel("Paginação")
public class PageableModelOpenApi {

    @ApiModelProperty(value = "Número da página (começa em 0)", example = "0")
    private int page;

    @ApiModelProperty(value = "Quantidade de elementos por página", example = "10")
    private int size;

    @ApiModelProperty(value = "Nome da propriedade para ordenação", example = "nome,asc")
    private List<String> sor;
}