package com.dan.esr.api.controllers.grupo;

import com.dan.esr.api.models.input.grupo.GrupoInput;
import com.dan.esr.api.models.output.grupo.GrupoOutput;
import com.dan.esr.api.models.output.grupo.GrupoPermissoesOutput;
import com.dan.esr.core.assemblers.GrupoAssembler;
import com.dan.esr.domain.entities.Grupo;
import com.dan.esr.domain.services.grupo.GrupoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/grupos")
public class GrupoController {
    private final GrupoService grupoService;
    private final GrupoAssembler grupoAssembler;

    @GetMapping("/{id}")
    public GrupoOutput grupo(@PathVariable Long id) {
        Grupo grupo = this.grupoService.buscarPor(id);
        return this.grupoAssembler.toModel(grupo);
    }

    @GetMapping("/{grupoId}/permissoes")
    public GrupoPermissoesOutput grupoPermissoes(@PathVariable("grupoId") Long id) {
        Grupo grupo = this.grupoService.buscarPor(id);
        return this.grupoAssembler.toModelGrupoPermissoes(grupo);
    }

    @GetMapping
    public List<GrupoOutput> grupos() {
        List<Grupo> Grupo = this.grupoService.buscarTodos();
        return this.grupoAssembler.toCollectionModel(Grupo);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GrupoOutput novoGrupo(@RequestBody @Valid GrupoInput GrupoInput) {
        Grupo Grupo = this.grupoAssembler.toDomain(GrupoInput);
        return this.grupoAssembler.toModel(
                this.grupoService.salvarOuAtualizar(Grupo)
        );
    }

    @PutMapping("/{id}")
    public GrupoOutput atualizarGrupo(@PathVariable Long id, @RequestBody @Valid GrupoInput grupoInput) {
        Grupo grupo = this.grupoService.buscarPor(id);
        this.grupoAssembler.copyToDomain(grupoInput, grupo);
        return this.grupoAssembler.toModel(
                this.grupoService.salvarOuAtualizar(grupo)
        );
    }

    @PutMapping("/{grupoId}/permissoes/{permissaoId}")
    public GrupoPermissoesOutput adicionarPermissao(@PathVariable Long grupoId, @PathVariable Long permissaoId) {
        Grupo grupo = this.grupoService.adicionarPermissao(grupoId, permissaoId);
        return this.grupoAssembler.toModelGrupoPermissoes(grupo);
    }

    @DeleteMapping("/{grupoId}/permissoes/{permissaoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public GrupoPermissoesOutput removerPermissao(@PathVariable Long grupoId, @PathVariable Long permissaoId) {
        Grupo grupo = this.grupoService.removerPermissao(grupoId, permissaoId);
        return this.grupoAssembler.toModelGrupoPermissoes(grupo);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluirGrupo(@PathVariable Long id) {
        this.grupoService.remover(id);
    }
}