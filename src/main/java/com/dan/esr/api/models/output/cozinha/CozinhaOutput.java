package com.dan.esr.api.models.output.cozinha;

import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Getter
@Setter
@EqualsAndHashCode(of = "id", callSuper = false)
@Relation(collectionRelation = "cozinhas")
public class CozinhaOutput extends RepresentationModel<CozinhaOutput> {

    @ApiModelProperty(example = "1")
    private Long id;

    @ApiModelProperty(example = "Brasileira")
    private String nome;
}