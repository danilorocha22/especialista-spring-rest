package com.dan.esr.api.v1.models.output.usuario;

import com.dan.esr.api.v1.models.output.view.PedidoView;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.util.Objects;

@Getter
@Setter
@ApiModel("Usuário")
@JsonView(PedidoView.Resumo.class)
@Relation(collectionRelation = "usuarios")
public class UsuarioOutput extends RepresentationModel<UsuarioOutput> {
    @ApiModelProperty(example = "1")
    private Long id;

    @ApiModelProperty(example = "Paulo Gomes")
    private String nome;

    @ApiModelProperty(example = "paulo.gomes@email.com")
    private String email;

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof UsuarioOutput that)) return false;
        if (!super.equals(object)) return false;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getId());
    }
}