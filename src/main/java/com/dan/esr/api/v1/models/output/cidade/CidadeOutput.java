package com.dan.esr.api.v1.models.output.cidade;

import com.dan.esr.api.v1.models.output.estado.EstadoOutput;
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
public class CidadeOutput extends RepresentationModel<CidadeOutput> {
    //@ApiModelProperty(value = "ID da cidade", example = "1")
    @ApiModelProperty(example = "1")
    private Long id;

    //@JsonProperty("cidade")
    @ApiModelProperty(example = "Palmas")
    private String nome;

    //@JsonProperty("estado")
    @ApiModelProperty(example = "TO")
    private EstadoOutput estado;
}