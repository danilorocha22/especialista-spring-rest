package com.dan.esr.api.models.output.grupo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Setter
@ApiModel("Grupo")
@EqualsAndHashCode(callSuper = true)
public class GrupoOutput extends RepresentationModel<GrupoOutput> {
    @ApiModelProperty(example = "1")
    private Long id;

    @ApiModelProperty(example = "Vendedor")
    private String nome;
}