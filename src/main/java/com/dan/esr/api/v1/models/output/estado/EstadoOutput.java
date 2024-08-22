package com.dan.esr.api.v1.models.output.estado;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Setter
@ApiModel(value = "Estado")
@EqualsAndHashCode(callSuper = true)
public class EstadoOutput extends RepresentationModel<EstadoOutput> {
    @ApiModelProperty(example = "1")
    private Long id;

    @ApiModelProperty(example = "TO")
    private String sigla;
}