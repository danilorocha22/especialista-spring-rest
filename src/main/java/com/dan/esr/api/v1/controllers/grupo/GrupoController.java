package com.dan.esr.api.v1.controllers.grupo;

import com.dan.esr.api.v1.models.input.grupo.GrupoInput;
import com.dan.esr.api.v1.models.output.grupo.GrupoOutput;
import com.dan.esr.api.v1.openapi.documentation.grupo.GrupoDocumentation;
import com.dan.esr.api.v1.assemblers.GrupoAssembler;
import com.dan.esr.core.security.CheckSecurity;
import com.dan.esr.domain.entities.Grupo;
import com.dan.esr.domain.services.grupo.GrupoService;

import javax.validation.Valid;

import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/v1/grupos")
public class GrupoController implements GrupoDocumentation {
    private final GrupoService grupoService;
    private final GrupoAssembler grupoAssembler;

    @Override
    @CheckSecurity.UsuariosGruposPermissoes.PodeConsultar
    @GetMapping(path = "/{id}", produces = APPLICATION_JSON_VALUE)
    public EntityModel<GrupoOutput> grupo(@PathVariable Long id) {
        Grupo grupo = this.grupoService.buscarPor(id);
        return EntityModel.of(
                this.grupoAssembler.toModel(grupo)
        );
    }

    @Override
    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @CheckSecurity.UsuariosGruposPermissoes.PodeConsultar
    public CollectionModel<GrupoOutput> grupos() {
        return this.grupoAssembler.toCollectionModel(
                this.grupoService.buscarTodos()
        );
    }

    @Override
    @ResponseStatus(CREATED)
    @PostMapping(produces = APPLICATION_JSON_VALUE)
    @CheckSecurity.UsuariosGruposPermissoes.PodeEditar
    public GrupoOutput novoGrupo(@RequestBody @Valid GrupoInput grupoInput) {
        Grupo grupo = this.grupoAssembler.toDomain(grupoInput);
        return this.grupoAssembler.toModel(
                this.grupoService.salvarOuAtualizar(grupo)
        );
    }

    @Override
    @CheckSecurity.UsuariosGruposPermissoes.PodeEditar
    @PutMapping(path = "/{id}", produces = APPLICATION_JSON_VALUE)
    public GrupoOutput atualizarGrupo(
            @PathVariable Long id,
            @RequestBody @Valid GrupoInput grupoInput
    ) {
        Grupo grupo = this.grupoService.buscarPor(id);
        this.grupoAssembler.copyToDomain(grupoInput, grupo);
        return this.grupoAssembler.toModel(
                this.grupoService.salvarOuAtualizar(grupo)
        );
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    @CheckSecurity.UsuariosGruposPermissoes.PodeEditar
    public void excluirGrupo(@PathVariable Long id) {
        this.grupoService.remover(id);
    }
}