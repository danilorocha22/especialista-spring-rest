package com.dan.esr.api.controllers;

import com.dan.esr.api.models.input.GrupoInput;
import com.dan.esr.api.models.output.GrupoOutput;
import com.dan.esr.core.assemblers.GrupoAssembler;
import com.dan.esr.domain.entities.Grupo;
import com.dan.esr.domain.services.GrupoService;
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

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluirGrupo(@PathVariable Long id) {
        this.grupoService.remover(id);
    }
}