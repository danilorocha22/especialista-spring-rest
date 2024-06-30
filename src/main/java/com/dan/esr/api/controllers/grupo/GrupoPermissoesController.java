package com.dan.esr.api.controllers.grupo;

import com.dan.esr.api.models.output.grupo.GrupoPermissoesOutput;
import com.dan.esr.api.openapi.documentation.grupo.GrupoPermissoesDocumentation;
import com.dan.esr.core.assemblers.GrupoAssembler;
import com.dan.esr.domain.entities.Grupo;
import com.dan.esr.domain.services.grupo.GrupoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/grupos/{grupoId}/permissoes")
public class GrupoPermissoesController implements GrupoPermissoesDocumentation {
    private final GrupoService grupoService;
    private final GrupoAssembler grupoAssembler;

    @Override
    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public GrupoPermissoesOutput permissoesGrupo(@PathVariable("grupoId") Long id) {
        Grupo grupo = this.grupoService.buscarPor(id);
        return this.grupoAssembler.toModelGrupoPermissoes(grupo);
    }

    @Override
    @PutMapping("/{permissaoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void adicionarPermissao(@PathVariable Long grupoId, @PathVariable Long permissaoId) {
        this.grupoService.adicionarPermissao(grupoId, permissaoId);
    }

    @Override
    @DeleteMapping("/{permissaoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removerPermissao(@PathVariable Long grupoId, @PathVariable Long permissaoId) {
        this.grupoService.removerPermissao(grupoId, permissaoId);
    }
}