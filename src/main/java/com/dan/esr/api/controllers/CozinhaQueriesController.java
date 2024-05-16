package com.dan.esr.api.controllers;

import com.dan.esr.api.assemblers.CozinhaAssembler;
import com.dan.esr.api.models.CozinhasXML;
import com.dan.esr.api.models.output.CozinhaOutput;
import com.dan.esr.domain.entities.Cozinha;
import com.dan.esr.domain.exceptions.CozinhaNaoEncontradaException;
import com.dan.esr.domain.services.CozinhaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.dan.esr.core.util.ValidacaoCampoObrigatorioUtil.validarCampoObrigatorio;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cozinhas")
public class CozinhaQueriesController {
    private final CozinhaService cozinhaService;
    private final CozinhaAssembler cozinhaAssembler;

    @GetMapping("/{id}")
    public CozinhaOutput buscarPorId(@PathVariable Long id) {
        validarCampoObrigatorio(id, "ID");
        Cozinha cozinha = this.cozinhaService.buscarPorId(id);
        return this.cozinhaAssembler.toModel(cozinha);
    }

    @GetMapping("/primeira")
    public CozinhaOutput buscarPrimeiraCozinha() {
        Cozinha cozinha = this.cozinhaService.buscarPrimeira();
        return this.cozinhaAssembler.toModel(cozinha);
    }

    //@ResponseStatus(HttpStatus.OK) //retorna 200
    @GetMapping("/primeira-com-nome-semelhante")
    public CozinhaOutput buscarPrimeiraCozinhaComNomeSemelhante(String nome) {
        if (isCampoVazio(nome)) nome = null;
        validarCampoObrigatorio(nome, "Nome");
        Cozinha cozinha = this.cozinhaService.buscarPrimeiraComNomeSemelhante(nome);
        return this.cozinhaAssembler.toModel(cozinha);
    }

    @GetMapping("/por-nome-igual")
    public CozinhaOutput buscarPorNomeIgual(String nome) {
        if (isCampoVazio(nome)) nome = null;
        validarCampoObrigatorio(nome, "Nome");
        Cozinha cozinha = this.cozinhaService.buscarPorNomeIgual(nome);
        return this.cozinhaAssembler.toModel(cozinha);
    }

    @GetMapping("/por-nome-semelhante")
    public List<CozinhaOutput> buscarTodasComNomeSemelhante(String nome) {
        if (isCampoVazio(nome)) nome = null;
        validarCampoObrigatorio(nome, "Nome");
        List<Cozinha> cozinhas = this.cozinhaService.buscarTodasComNomeSemelhante(nome);
        return this.cozinhaAssembler.toModelList(cozinhas);
    }

    @GetMapping
    public List<CozinhaOutput> listar() {
        List<Cozinha> cozinhas = this.cozinhaService.buscarTodos();
        return this.cozinhaAssembler.toModelList(cozinhas);
    }

    @GetMapping("/existe")
    public boolean existe(String nome) {
        if (isCampoVazio(nome)) nome = null;
        validarCampoObrigatorio(nome, "Nome");
        return cozinhaService.existePorNome(nome);
    }

    @GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
    public CozinhasXML listarXml() {
        List<Cozinha> cozinhas = this.cozinhaService.buscarTodos();
        if (cozinhas.isEmpty()) {
            throw new CozinhaNaoEncontradaException("Nenhuma cozinha encontrada");
        }
        return new CozinhasXML(this.cozinhaAssembler.toModelList(cozinhas));
    }

    private boolean isCampoVazio(String nome) {
        return nome != null && nome.isBlank();
    }
}