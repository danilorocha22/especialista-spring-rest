package com.dan.esr.api.models.output.grupo;

import com.dan.esr.api.models.output.permissao.PermissaoOutput;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@ApiModel("Permissões do Grupo")
public class GrupoPermissoesOutput {
    @ApiModelProperty(value = "Nome do grupo")
    private String nome;

    private List<PermissaoOutput> permissoes;
}