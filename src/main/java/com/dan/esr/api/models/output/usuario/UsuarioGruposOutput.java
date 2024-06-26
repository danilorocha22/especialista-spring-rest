package com.dan.esr.api.models.output.usuario;

import com.dan.esr.api.models.output.grupo.GrupoOutput;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@ApiModel("Grupos do Usuário")
public class UsuarioGruposOutput {
    @ApiModelProperty(value = "Nome do usuário")
    private String nome;

    private List<GrupoOutput> grupos;
}