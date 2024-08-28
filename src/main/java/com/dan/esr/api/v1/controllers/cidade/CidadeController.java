package com.dan.esr.api.v1.controllers.cidade;

import com.dan.esr.api.helper.uri.ResourceUriHelper;
import com.dan.esr.api.v1.assemblers.CidadeAssembler;
import com.dan.esr.api.v1.models.input.cidade.CidadeInput;
import com.dan.esr.api.v1.models.output.cidade.CidadeOutput;
import com.dan.esr.api.v1.openapi.documentation.cidade.CidadeDocumentation;
import com.dan.esr.domain.entities.Cidade;
import com.dan.esr.domain.services.cidade.CidadeService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import static com.dan.esr.core.web.DanFoodMediaTypes.V1_APPLICATION_JSON_VALUE;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/v1/cidades", produces = APPLICATION_JSON_VALUE)
public class CidadeController implements CidadeDocumentation {
    private final CidadeService cidadeService;
    private final CidadeAssembler cidadeAssembler;

    @Override
    @GetMapping(path = "/{id}")
    public EntityModel<CidadeOutput> cidade(@PathVariable Long id) {
        Cidade cidade = this.cidadeService.buscarPor(id);
        return EntityModel.of(
                this.cidadeAssembler.toModel(cidade)
        );
    }

    @Deprecated
    @Override
    @GetMapping
    public CollectionModel<CidadeOutput> cidades() {
        return this.cidadeAssembler.toCollectionModel(
                this.cidadeService.buscarTodos()
        );
    }

    @Override
    @PostMapping
    @ResponseStatus(CREATED)
    public CidadeOutput novaCidade(@RequestBody @Valid CidadeInput cidadeInput) {
        Cidade cidade = this.cidadeAssembler.toDomain(cidadeInput);
        cidade = this.cidadeService.salvarOuAtualizar(cidade);
        ResourceUriHelper.addUriInResponseHeader(cidade.getId());
        return this.cidadeAssembler.toModel(cidade);
    }

    @Override
    @PutMapping(path = "/{id}")
    public CidadeOutput atualizarCidade(@PathVariable Long id, @RequestBody @Valid CidadeInput cidadeInput) {
        Cidade cidade = this.cidadeService.buscarPor(id);
        this.cidadeAssembler.copyToCidadeDomain(cidadeInput, cidade);
        cidade = this.cidadeService.salvarOuAtualizar(cidade);
        return this.cidadeAssembler.toModel(cidade);
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void excluirCidade(@PathVariable Long id) {
        this.cidadeService.remover(id);
    }
}