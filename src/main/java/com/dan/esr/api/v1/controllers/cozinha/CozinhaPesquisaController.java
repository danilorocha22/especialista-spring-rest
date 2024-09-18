package com.dan.esr.api.v1.controllers.cozinha;

import com.dan.esr.api.v1.models.output.cozinha.CozinhaOutput;
import com.dan.esr.api.v1.models.output.cozinha.CozinhasXML;
import com.dan.esr.api.v1.openapi.documentation.cozinha.CozinhaPesquisaDocumentation;
import com.dan.esr.api.v1.assemblers.CozinhaAssembler;
import com.dan.esr.core.security.CheckSecurity;
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

import java.util.List;

import static com.dan.esr.core.util.ValidacaoUtil.validarCampoObrigatorio;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.APPLICATION_XML_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/cozinhas")
public class CozinhaPesquisaController implements CozinhaPesquisaDocumentation {
    private final CozinhaConsultaService cozinhaConsultaService;
    private final CozinhaAssembler cozinhaAssembler;
    private final PagedResourcesAssembler<Cozinha> pagedResourcesAssembler;


    @Override
    @GetMapping(path = "/{id}", produces = APPLICATION_JSON_VALUE)
    public EntityModel<CozinhaOutput> cozinha(@PathVariable Long id) {
        Cozinha cozinha = this.cozinhaConsultaService.buscarPor(id);
        return EntityModel.of(
                this.cozinhaAssembler.toModel(cozinha)
        );
    }

    @Override
    @GetMapping(path = "/primeira", produces = APPLICATION_JSON_VALUE)
    public EntityModel<CozinhaOutput> primeiraCozinha() {
        Cozinha cozinha = this.cozinhaConsultaService.buscarPrimeira();
        return EntityModel.of(
                this.cozinhaAssembler.toModel(cozinha)
        );
    }

    @Override
    @GetMapping(path = "/primeira-com-nome-semelhante", produces = APPLICATION_JSON_VALUE)
    public EntityModel<CozinhaOutput> primeiraCozinhaComNomeSemelhante(String nome) {
        validarCampoObrigatorio(nome, "Nome");
        Cozinha cozinha = this.cozinhaConsultaService.buscarPrimeiraComNomeSemelhante(nome);
        return EntityModel.of(
                this.cozinhaAssembler.toModel(cozinha)
        );
    }

    @Override
    @GetMapping(path = "/por-nome-igual", produces = APPLICATION_JSON_VALUE)
    public EntityModel<CozinhaOutput> cozinhaComNomeIgual(String nome) {
        validarCampoObrigatorio(nome, "Nome");
        Cozinha cozinha = this.cozinhaConsultaService.buscarComNomeIgual(nome);
        return EntityModel.of(
                this.cozinhaAssembler.toModel(cozinha)
        );
    }

    @Override
    @GetMapping(path = "/por-nome-semelhante", produces = APPLICATION_JSON_VALUE)
    public CollectionModel<CozinhaOutput> cozinhasComNomeSemelhante(String nome) {
        validarCampoObrigatorio(nome, "Nome");
        return this.cozinhaAssembler.toCollectionModel(
                this.cozinhaConsultaService.buscarTodasComNomeSemelhante(nome)
        );
    }

    @Override
    @CheckSecurity.Cozinhas.PodeConsultar
    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public PagedModel<CozinhaOutput> cozinhas(@PageableDefault(size = 5) Pageable pageable) {
        Page<Cozinha> cozinhaPage = this.cozinhaConsultaService.todosPaginados(pageable);
        return this.pagedResourcesAssembler
                .toModel(cozinhaPage, this.cozinhaAssembler);
    }

    @Override
    @GetMapping("/existe")
    public boolean existe(String nome) {
        validarCampoObrigatorio(nome, "Nome");
        return cozinhaConsultaService.existePorNomeIgual(nome);
    }

    @Override
    @GetMapping(produces = APPLICATION_XML_VALUE)
    public CollectionModel<CozinhasXML> listarXml(Pageable pageable) {
        Page<Cozinha> cozinhaPage = this.cozinhaConsultaService.todosPaginados(pageable);
        CollectionModel<CozinhaOutput> collectionModel = this.cozinhaAssembler.toCollectionModel(cozinhaPage.getContent());
        return (CollectionModel<CozinhasXML>) CollectionModel.of(new CozinhasXML((List<CozinhaOutput>) collectionModel));
    }
}