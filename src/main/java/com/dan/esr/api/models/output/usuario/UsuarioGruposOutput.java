package com.dan.esr.api.models.output.usuario;

import com.dan.esr.api.models.output.grupo.GrupoOutput;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class UsuarioGruposOutput {
    private String nome;
    private List<GrupoOutput> grupos;
}
