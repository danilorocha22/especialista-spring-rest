package com.dan.esr.api.controllers.grupo;

import com.dan.esr.api.models.input.grupo.GrupoInput;
import com.dan.esr.api.models.output.grupo.GrupoOutput;
import com.dan.esr.api.openapi.documentation.grupo.GrupoDocumentation;
import com.dan.esr.core.assemblers.GrupoAssembler;
import com.dan.esr.domain.entities.Grupo;
import com.dan.esr.domain.services.grupo.GrupoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/grupos")
public class GrupoController implements GrupoDocumentation {
    private final GrupoService grupoService;
    private final GrupoAssembler grupoAssembler;

    @Override
    @GetMapping(path = "/{id}", produces = APPLICATION_JSON_VALUE)
    public GrupoOutput grupo(@PathVariable Long id) {
        Grupo grupo = this.grupoService.buscarPor(id);
        return this.grupoAssembler.toModel(grupo);
    }

    @Override
    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public List<GrupoOutput> grupos() {
        List<Grupo> Grupo = this.grupoService.buscarTodos();
        return this.grupoAssembler.toCollectionModel(Grupo);
    }

    @Override
    @PostMapping(produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public GrupoOutput novoGrupo(@RequestBody @Valid GrupoInput GrupoInput) {
        Grupo Grupo = this.grupoAssembler.toDomain(GrupoInput);
        return this.grupoAssembler.toModel(
                this.grupoService.salvarOuAtualizar(Grupo)
        );
    }

    @Override
    @PutMapping(path = "/{id}", produces = APPLICATION_JSON_VALUE)
    public GrupoOutput atualizarGrupo(@PathVariable Long id, @RequestBody @Valid GrupoInput grupoInput) {
        Grupo grupo = this.grupoService.buscarPor(id);
        this.grupoAssembler.copyToDomain(grupoInput, grupo);
        return this.grupoAssembler.toModel(
                this.grupoService.salvarOuAtualizar(grupo)
        );
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluirGrupo(@PathVariable Long id) {
        this.grupoService.remover(id);
    }
}