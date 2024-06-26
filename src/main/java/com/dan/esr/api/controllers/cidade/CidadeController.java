package com.dan.esr.api.controllers.cidade;

import com.dan.esr.api.models.input.cidade.CidadeInput;
import com.dan.esr.api.models.output.cidade.CidadeEstadoOutput;
import com.dan.esr.api.models.output.cidade.CidadeNomeOutput;
import com.dan.esr.api.models.output.cidade.CidadeOutput;
import com.dan.esr.api.openapi.documentation.cidade.CidadeDocumentation;
import com.dan.esr.core.assemblers.CidadeAssembler;
import com.dan.esr.domain.entities.Cidade;
import com.dan.esr.domain.services.cidade.CidadeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/cidades")
public class CidadeController implements CidadeDocumentation {
    private final CidadeService cidadeService;
    private final CidadeAssembler cidadeAssembler;

    @Override
    @GetMapping(path = "/{id}", produces = APPLICATION_JSON_VALUE)
    public CidadeOutput cidade(@PathVariable Long id) {
        Cidade cidade = this.cidadeService.buscarPor(id);
        return this.cidadeAssembler.toModel(cidade, CidadeEstadoOutput.class);
    }

    @Override
    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public List<CidadeOutput> cidades() {
        List<Cidade> cidades = this.cidadeService.buscarTodos();
        return this.cidadeAssembler.toModelList(cidades, CidadeNomeOutput.class);
    }

    @Override
    @PostMapping(produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public CidadeOutput novaCidade(@RequestBody @Valid CidadeInput cidadeInput) {
        Cidade cidade = this.cidadeAssembler.toDomain(cidadeInput);
        cidade = this.cidadeService.salvarOuAtualizar(cidade);
        return this.cidadeAssembler.toModel(cidade, CidadeEstadoOutput.class);
    }

    @Override
    @PutMapping(path = "/{id}", produces = APPLICATION_JSON_VALUE)
    public CidadeOutput atualizarCidade(@PathVariable Long id, @RequestBody @Valid CidadeInput cidadeInput) {
        Cidade cidade = this.cidadeService.buscarPor(id);
        this.cidadeAssembler.copyToCidadeDomain(cidadeInput, cidade);
        cidade = this.cidadeService.salvarOuAtualizar(cidade);
        return this.cidadeAssembler.toModel(cidade, CidadeEstadoOutput.class);
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluirCidade(@PathVariable Long id) {
        this.cidadeService.remover(id);
    }
}