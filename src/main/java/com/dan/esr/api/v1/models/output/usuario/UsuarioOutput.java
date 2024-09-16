package com.dan.esr.api.v1.models.output.usuario;

import com.dan.esr.api.v1.models.output.view.PedidoView;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Getter
@Setter
@ApiModel("Usuário")
@JsonView(PedidoView.Resumo.class)
@Relation(collectionRelation = "usuarios")
@EqualsAndHashCode(of = "id", callSuper = false)
public class UsuarioOutput extends RepresentationModel<UsuarioOutput> {
    @ApiModelProperty(example = "1")
    private Long id;

    @ApiModelProperty(example = "Paulo Gomes")
    private String nome;

    @ApiModelProperty(example = "paulo.gomes@email.com")
    private String email;
}