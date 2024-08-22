package com.dan.esr.api.v1.models.output.restaurante;

import com.dan.esr.api.v1.models.output.usuario.UsuarioOutput;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.util.List;

@Getter
@Setter
@ApiModel("Responsáveis do Restaurante")
@EqualsAndHashCode(of = "nome", callSuper = false)
@Relation(itemRelation = "Responsável", collectionRelation = "Responsáveis")
public class RestauranteResponsaveisOutput extends RepresentationModel<RestauranteResponsaveisOutput> {
    @JsonProperty("restaurante")
    @ApiModelProperty(example = "Restaurante")
    private String nome;

    @JsonProperty("responsaveis")
    private List<UsuarioOutput> usuarios;
}