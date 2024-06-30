package com.dan.esr.api.models.output.usuario;

import com.dan.esr.api.models.output.view.PedidoView;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("Usuário")
@JsonView(PedidoView.Resumo.class)
public class UsuarioOutput {
    @ApiModelProperty(example = "1")
    private Long id;

    @ApiModelProperty(example = "Paulo Gomes")
    private String nome;

    @ApiModelProperty(example = "paulo.gomes@email.com")
    private String email;
}