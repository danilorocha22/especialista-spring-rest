package com.dan.esr.api.v2.controllers.cidade;

import com.dan.esr.api.helper.uri.ResourceUriHelper;
import com.dan.esr.api.v2.assemblers.CidadeAssemblerV2;
import com.dan.esr.api.v2.models.input.CidadeInputV2;
import com.dan.esr.api.v2.models.output.CidadeOutputV2;
import com.dan.esr.domain.entities.Cidade;
import com.dan.esr.domain.services.cidade.CidadeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/v2/cidades", produces = APPLICATION_JSON_VALUE)
public class CidadeControllerV2 {
    private final CidadeService cidadeService;
    private final CidadeAssemblerV2 cidadeAssembler;

    @GetMapping(path = "/{id}")
    public EntityModel<CidadeOutputV2> cidade(@PathVariable Long id) {
        Cidade cidade = this.cidadeService.buscarPor(id);
        return EntityModel.of(
                this.cidadeAssembler.toModel(cidade)
        );
    }

    @GetMapping
    public CollectionModel<CidadeOutputV2> cidades() {
        return this.cidadeAssembler.toCollectionModel(
                this.cidadeService.buscarTodos()
        );
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public CidadeOutputV2 novaCidade(@RequestBody @Valid CidadeInputV2 cidadeInput) {
        Cidade cidade = this.cidadeAssembler.toDomain(cidadeInput);
        cidade = this.cidadeService.salvarOuAtualizar(cidade);
        ResourceUriHelper.addUriInResponseHeader(cidade.getId());
        return this.cidadeAssembler.toModel(cidade);
    }

    @PutMapping(path = "/{id}")
    public CidadeOutputV2 atualizarCidade(@PathVariable Long id, @RequestBody @Valid CidadeInputV2 cidadeInput) {
        Cidade cidade = this.cidadeService.buscarPor(id);
        this.cidadeAssembler.copyToCidadeDomain(cidadeInput, cidade);
        cidade = this.cidadeService.salvarOuAtualizar(cidade);
        return this.cidadeAssembler.toModel(cidade);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void excluirCidade(@PathVariable Long id) {
        this.cidadeService.remover(id);
    }
}