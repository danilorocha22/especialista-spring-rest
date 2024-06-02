package com.dan.esr.api.controllers.cozinha;

import com.dan.esr.api.models.CozinhasXML;
import com.dan.esr.api.models.output.cozinha.CozinhaOutput;
import com.dan.esr.core.assemblers.CozinhaAssembler;
import com.dan.esr.domain.entities.Cozinha;
import com.dan.esr.domain.exceptions.cozinha.CozinhaNaoEncontradaException;
import com.dan.esr.domain.services.cozinha.CozinhaConsultaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.dan.esr.core.util.ValidacaoUtil.validarCampoObrigatorio;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cozinhas")
public class CozinhaConsultaController {
    private final CozinhaConsultaService cozinhaConsultaService;
    private final CozinhaAssembler cozinhaAssembler;

    @GetMapping("/{id}")
    public CozinhaOutput buscarPorId(@PathVariable Long id) {
        return this.cozinhaAssembler.toModel(
                this.cozinhaConsultaService.buscarPor(id)
        );
    }

    @GetMapping("/primeira")
    public CozinhaOutput buscarPrimeiraCozinha() {
        return this.cozinhaAssembler.toModel(
                this.cozinhaConsultaService.buscarPrimeira()
        );
    }

    @GetMapping("/primeira-com-nome-semelhante")
    public CozinhaOutput buscarPrimeiraCozinhaComNomeSemelhante(String nome) {
        validarCampoObrigatorio(nome, "Nome");
        Cozinha cozinha = this.cozinhaConsultaService.buscarPrimeiraComNomeSemelhante(nome);
        return this.cozinhaAssembler.toModel(cozinha);
    }

    @GetMapping("/por-nome-igual")
    public CozinhaOutput buscarPorNomeIgual(String nome) {
        validarCampoObrigatorio(nome, "Nome");
        Cozinha cozinha = this.cozinhaConsultaService.buscarPorNomeIgual(nome);
        return this.cozinhaAssembler.toModel(cozinha);
    }

    @GetMapping("/por-nome-semelhante")
    public List<CozinhaOutput> buscarTodasComNomeSemelhante(String nome) {
        validarCampoObrigatorio(nome, "Nome");
        List<Cozinha> cozinhas = this.cozinhaConsultaService.buscarTodasComNomeSemelhante(nome);
        return this.cozinhaAssembler.toModelList(cozinhas);
    }

    @GetMapping
    public Page<CozinhaOutput> cozinhas(@PageableDefault(size = 5) Pageable pageable) {
        List<CozinhaOutput> cozinhasOutput = this.cozinhaAssembler.toModelList(
                this.cozinhaConsultaService.todosPaginados(pageable)
        );
        return new PageImpl<>(cozinhasOutput, pageable, cozinhasOutput.size());
    }

    @GetMapping("/existe")
    public boolean existe(String nome) {
        validarCampoObrigatorio(nome, "Nome");
        return cozinhaConsultaService.existePorNomeIgual(nome);
    }

    @GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
    public CozinhasXML listarXml(Pageable pageable) {
        List<Cozinha> cozinhas = this.cozinhaConsultaService.todosPaginados(pageable);
        if (cozinhas.isEmpty()) {
            throw new CozinhaNaoEncontradaException("Nenhuma cozinha encontrada");
        }
        return new CozinhasXML(this.cozinhaAssembler.toModelList(cozinhas));
    }
}