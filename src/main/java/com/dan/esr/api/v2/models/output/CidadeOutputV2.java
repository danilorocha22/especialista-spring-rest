package com.dan.esr.api.v2.models.output;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Getter
@Setter
@ApiModel("Cidade")
@Relation(collectionRelation = "cidades")
@EqualsAndHashCode(of = "id", callSuper = false)
public class CidadeOutputV2 extends RepresentationModel<CidadeOutputV2> {
    //@ApiModelProperty(value = "ID da cidade", example = "1")
    @ApiModelProperty(example = "1")
    private Long idCidade;

    //@JsonProperty("cidade")
    @ApiModelProperty(example = "Palmas")
    private String nomeCidade;

    private Long idEstado;
    private String siglaEstado;

    //@JsonProperty("estado")
    /*@ApiModelProperty(example = "TO")
    private EstadoOutput estado;*/
}