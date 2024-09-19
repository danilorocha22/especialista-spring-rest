package com.dan.esr.api.v1.controllers.grupo;

import com.dan.esr.api.v1.models.output.grupo.GrupoPermissoesOutput;
import com.dan.esr.api.v1.openapi.documentation.grupo.GrupoPermissoesDocumentation;
import com.dan.esr.api.v1.assemblers.GrupoAssembler;
import com.dan.esr.core.security.CheckSecurity;
import com.dan.esr.domain.entities.Grupo;
import com.dan.esr.domain.services.grupo.GrupoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/v1/grupos/{grupoId}/permissoes")
public class GrupoPermissoesController implements GrupoPermissoesDocumentation {
    private final GrupoService grupoService;
    private final GrupoAssembler grupoAssembler;

    @Override
    @CheckSecurity.UsuariosGruposPermissoes.PodeConsultar
    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public GrupoPermissoesOutput permissoesDoGrupo(@PathVariable("grupoId") Long id) {
        Grupo grupo = this.grupoService.buscarPor(id);
        return this.grupoAssembler.toModelGrupoPermissoes(grupo);
    }

    @Override
    @ResponseStatus(NO_CONTENT)
    @PutMapping("/{permissaoId}")
    @CheckSecurity.UsuariosGruposPermissoes.PodeEditar
    public void adicionarPermissao(@PathVariable Long grupoId, @PathVariable Long permissaoId) {
        this.grupoService.adicionarPermissao(grupoId, permissaoId);
    }

    @Override
    @ResponseStatus(NO_CONTENT)
    @DeleteMapping("/{permissaoId}")
    @CheckSecurity.UsuariosGruposPermissoes.PodeEditar
    public void removerPermissao(@PathVariable Long grupoId, @PathVariable Long permissaoId) {
        this.grupoService.removerPermissao(grupoId, permissaoId);
    }
}