package com.dan.esr.api.v1.openapi.models;

import com.dan.esr.api.v1.models.output.cidade.CidadeOutput;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.Links;

import java.util.List;

@Getter
@Setter
@ApiModel("CidadesModel")
public class CidadesModelOpenApi {

    private CidadesEmbeddedModelOpenApi _embedded;
    private Links _links;

    @Getter
    @Setter
    @ApiModel("CidadesEmbeddedModel")
    public static class CidadesEmbeddedModelOpenApi {
        private List<CidadeOutput> cidades;
    }
}