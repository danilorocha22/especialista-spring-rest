package com.dan.esr.api.v2.controllers.cozinha;

import com.dan.esr.api.v2.assemblers.CozinhaAssemblerV2;
import com.dan.esr.api.v2.models.output.CozinhaOutputV2;
import com.dan.esr.domain.entities.Cozinha;
import com.dan.esr.domain.services.cozinha.CozinhaConsultaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.dan.esr.core.util.ValidacaoUtil.validarCampoObrigatorio;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/v2/cozinhas", produces = APPLICATION_JSON_VALUE)
public class CozinhaPesquisaControllerV2 {
    private final CozinhaConsultaService cozinhaConsultaService;
    private final CozinhaAssemblerV2 cozinhaAssembler;
    private final PagedResourcesAssembler<Cozinha> pagedResourcesAssembler;

    @GetMapping(path = "/{id}")
    public EntityModel<CozinhaOutputV2> cozinha(@PathVariable Long id) {
        Cozinha cozinha = this.cozinhaConsultaService.buscarPor(id);
        return EntityModel.of(
                this.cozinhaAssembler.toModel(cozinha)
        );
    }

    @GetMapping(path = "/primeira")
    public EntityModel<CozinhaOutputV2> primeiraCozinha() {
        Cozinha cozinha = this.cozinhaConsultaService.buscarPrimeira();
        return EntityModel.of(
                this.cozinhaAssembler.toModel(cozinha)
        );
    }

    @GetMapping(path = "/primeira-com-nome-semelhante")
    public EntityModel<CozinhaOutputV2> primeiraCozinhaComNomeSemelhante(String nome) {
        validarCampoObrigatorio(nome, "Nome");
        Cozinha cozinha = this.cozinhaConsultaService.buscarPrimeiraComNomeSemelhante(nome);
        return EntityModel.of(
                this.cozinhaAssembler.toModel(cozinha)
        );
    }

    @GetMapping(path = "/por-nome-igual")
    public EntityModel<CozinhaOutputV2> cozinhaComNomeIgual(String nome) {
        validarCampoObrigatorio(nome, "Nome");
        Cozinha cozinha = this.cozinhaConsultaService.buscarComNomeIgual(nome);
        return EntityModel.of(
                this.cozinhaAssembler.toModel(cozinha)
        );
    }

    @GetMapping(path = "/por-nome-semelhante")
    public CollectionModel<CozinhaOutputV2> cozinhasComNomeSemelhante(String nome) {
        validarCampoObrigatorio(nome, "Nome");
        return this.cozinhaAssembler.toCollectionModel(
                this.cozinhaConsultaService.buscarTodasComNomeSemelhante(nome)
        );
    }

    @GetMapping
    public PagedModel<CozinhaOutputV2> cozinhas(@PageableDefault(size = 5) Pageable pageable) {
        Page<Cozinha> cozinhaPage = this.cozinhaConsultaService.todosPaginados(pageable);
        return this.pagedResourcesAssembler
                .toModel(cozinhaPage, this.cozinhaAssembler);
    }

    @GetMapping("/existe")
    public boolean existe(String nome) {
        validarCampoObrigatorio(nome, "Nome");
        return cozinhaConsultaService.existePorNomeIgual(nome);
    }
}