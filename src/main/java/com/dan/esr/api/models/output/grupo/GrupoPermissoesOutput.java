package com.dan.esr.api.models.output.grupo;

import com.dan.esr.api.models.output.permissao.PermissaoOutput;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class GrupoPermissoesOutput {
    private String nome;
    private List<PermissaoOutput> permissoes;
}