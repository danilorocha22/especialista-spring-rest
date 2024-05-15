package com.dan.esr.api.controllers;

import com.dan.esr.api.assembler.CidadeAssembler;
import com.dan.esr.api.models.input.CidadeInput;
import com.dan.esr.api.models.output.CidadeEstadoOutput;
import com.dan.esr.api.models.output.CidadeNomeOutput;
import com.dan.esr.api.models.output.CidadeOutput;
import com.dan.esr.domain.entities.Cidade;
import com.dan.esr.domain.services.CidadeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.dan.esr.core.util.ValidacaoCampoObrigatorioUtil.validarCampoObrigatorio;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cidades")
public class CidadeController {
    private final CidadeService cidadeService;
    private final CidadeAssembler cidadeAssembler;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CidadeOutput salvar(@RequestBody @Valid CidadeInput cidadeInput) {
        Cidade cidade = this.cidadeAssembler.toDomain(cidadeInput);
        cidade = this.cidadeService.salvarOuAtualizar(cidade);
        return this.cidadeAssembler.toModel(cidade, CidadeEstadoOutput.class);
    }

    @PutMapping("/{id}")
    public CidadeOutput atualizar(
            @PathVariable Long id,
            @RequestBody @Valid CidadeInput cidadeInput
    ) {
        validarCampoObrigatorio(id, "ID");
        Cidade cidade = this.cidadeAssembler.toDomain(cidadeInput);
        cidade.setId(id);
        cidade = this.cidadeService.salvarOuAtualizar(cidade);
        return this.cidadeAssembler.toModel(cidade, CidadeEstadoOutput.class);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void remover(@PathVariable Long id) {
        validarCampoObrigatorio(id, "ID");
        this.cidadeService.remover(id);
    }

    @GetMapping("/{id}")
    public CidadeOutput buscarPorId(@PathVariable Long id) {
        validarCampoObrigatorio(id, "ID");
        Cidade cidade = this.cidadeService.buscarCidadePorId(id);
        return this.cidadeAssembler.toModel(cidade, CidadeEstadoOutput.class);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CidadeOutput> listar() {
        List<Cidade> cidades = this.cidadeService.buscarTodos();
        return this.cidadeAssembler.toModelList(cidades, CidadeNomeOutput.class);
    }
}