package com.dan.esr.api.controllers.cozinha;

import com.dan.esr.api.models.output.cozinha.CozinhasXML;
import com.dan.esr.api.models.output.cozinha.CozinhaOutput;
import com.dan.esr.api.openapi.documentation.cozinha.CozinhaPesquisaDocumentation;
import com.dan.esr.core.assemblers.CozinhaAssembler;
import com.dan.esr.domain.entities.Cozinha;
import com.dan.esr.domain.services.cozinha.CozinhaConsultaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
@RequestMapping("/cozinhas")
public class CozinhaPesquisaController implements CozinhaPesquisaDocumentation {
    private final CozinhaConsultaService cozinhaConsultaService;
    private final CozinhaAssembler cozinhaAssembler;

    @Override
    @GetMapping(path = "/{id}", produces = APPLICATION_JSON_VALUE)
    public CozinhaOutput cozinha(@PathVariable Long id) {
        return this.cozinhaAssembler.toModel(
                this.cozinhaConsultaService.buscarPor(id)
        );
    }

    @Override
    @GetMapping(path = "/primeira", produces = APPLICATION_JSON_VALUE)
    public CozinhaOutput primeiraCozinha() {
        return this.cozinhaAssembler.toModel(
                this.cozinhaConsultaService.buscarPrimeira()
        );
    }

    @Override
    @GetMapping(path = "/primeira-com-nome-semelhante", produces = APPLICATION_JSON_VALUE)
    public CozinhaOutput primeiraCozinhaComNomeSemelhante(String nome) {
        validarCampoObrigatorio(nome, "Nome");
        Cozinha cozinha = this.cozinhaConsultaService.buscarPrimeiraComNomeSemelhante(nome);
        return this.cozinhaAssembler.toModel(cozinha);
    }

    @Override
    @GetMapping(path = "/por-nome-igual", produces = APPLICATION_JSON_VALUE)
    public CozinhaOutput cozinhaComNomeIgual(String nome) {
        validarCampoObrigatorio(nome, "Nome");
        Cozinha cozinha = this.cozinhaConsultaService.buscarComNomeIgual(nome);
        return this.cozinhaAssembler.toModel(cozinha);
    }

    @Override
    @GetMapping(path = "/por-nome-semelhante", produces = APPLICATION_JSON_VALUE)
    public List<CozinhaOutput> cozinhasComNomeSemelhante(String nome) {
        validarCampoObrigatorio(nome, "Nome");
        List<Cozinha> cozinhas = this.cozinhaConsultaService.buscarTodasComNomeSemelhante(nome);
        return this.cozinhaAssembler.toModelList(cozinhas);
    }

    @Override
    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public Page<CozinhaOutput> cozinhas(@PageableDefault(size = 5) Pageable pageable) {
        Page<Cozinha> cozinhaPage = this.cozinhaConsultaService.todosPaginados(pageable);
        List<CozinhaOutput> cozinhasOutput = this.cozinhaAssembler.toModelList(cozinhaPage.getContent());
        return new PageImpl<>(cozinhasOutput, pageable, cozinhaPage.getTotalElements());
    }

    @Override
    @GetMapping("/existe")
    public boolean existe(String nome) {
        validarCampoObrigatorio(nome, "Nome");
        return cozinhaConsultaService.existePorNomeIgual(nome);
    }

    @Override
    @GetMapping(produces = APPLICATION_XML_VALUE)
    public CozinhasXML listarXml(Pageable pageable) {
        Page<Cozinha> cozinhaPage = this.cozinhaConsultaService.todosPaginados(pageable);
        List<CozinhaOutput> cozinhasOutput = this.cozinhaAssembler.toModelList(cozinhaPage.getContent());
        return new CozinhasXML(cozinhasOutput);
    }
}