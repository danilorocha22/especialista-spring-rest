package com.dan.esr.api.controllers.cidade;

import com.dan.esr.api.models.input.cidade.CidadeInput;
import com.dan.esr.api.models.output.cidade.CidadeEstadoOutput;
import com.dan.esr.api.models.output.cidade.CidadeNomeOutput;
import com.dan.esr.api.models.output.cidade.CidadeOutput;
import com.dan.esr.core.assemblers.CidadeAssembler;
import com.dan.esr.domain.entities.Cidade;
import com.dan.esr.domain.services.cidade.CidadeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        Cidade cidade = this.cidadeService.buscarPor(id);
        this.cidadeAssembler.copyToCidadeDomain(cidadeInput, cidade);
        cidade = this.cidadeService.salvarOuAtualizar(cidade);
        return this.cidadeAssembler.toModel(cidade, CidadeEstadoOutput.class);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void remover(@PathVariable Long id) {
        this.cidadeService.remover(id);
    }

    @GetMapping("/{id}")
    public CidadeOutput buscarPorId(@PathVariable Long id) {
        Cidade cidade = this.cidadeService.buscarPor(id);
        return this.cidadeAssembler.toModel(cidade, CidadeEstadoOutput.class);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CidadeOutput> listar() {
        List<Cidade> cidades = this.cidadeService.buscarTodos();
        return this.cidadeAssembler.toModelList(cidades, CidadeNomeOutput.class);
    }
}