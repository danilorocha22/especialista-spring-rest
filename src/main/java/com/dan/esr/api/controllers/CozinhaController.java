package com.dan.esr.api.controllers;

import com.dan.esr.api.models.CozinhasXML;
import com.dan.esr.domain.entities.Cozinha;
import com.dan.esr.domain.exceptions.NegocioException;
import com.dan.esr.domain.exceptions.PropriedadeIlegalException;
import com.dan.esr.domain.repositories.CozinhaRepository;
import com.dan.esr.domain.services.CadastroCozinhaService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@RestController
@RequestMapping("/cozinhas")
public class CozinhaController {

    private final CozinhaRepository cozinhaRepo;
    private final CadastroCozinhaService cozinhaService;

    @GetMapping("/{id}")
    public Cozinha buscarPorId(@PathVariable Long id) {
        return this.cozinhaService.buscarCozinhaPorId(id);
    }

    @GetMapping("/por-nome")
    public List<Cozinha> buscarPorNome(String nome) {
        return this.cozinhaService.buscarTodasPorNome(nome);
    }

    @GetMapping("/unica-por-nome")
    public Cozinha buscarUnicaPorNome(String nome) {
        return this.cozinhaService.buscarUnicaPorNome(nome);
    }

    @GetMapping("/existe")
    public boolean existe(String nome) {
        return cozinhaRepo.existsByNome(nome);
    }

    @GetMapping("/primeiro")
    public Cozinha cozinhaPrimeiro() {
        return this.cozinhaService.buscarPrimeiro();
    }

    //@ResponseStatus(HttpStatus.OK) //retorna 200
    @GetMapping
    public List<Cozinha> listar() {
        return this.cozinhaRepo.findAll();
    }

    @GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
    public CozinhasXML listarXml() {
        return new CozinhasXML(cozinhaRepo.findAll());
    }

    @ResponseStatus(HttpStatus.CREATED) //retorna 201
    @PostMapping
    public Cozinha salvar(@RequestBody @Valid Cozinha cozinha) {
        if (Objects.nonNull(cozinha.getId())) {
            throw new PropriedadeIlegalException(String.format(
                    "A propriedade 'id' com valor '%s' não pode ser informada", cozinha.getId()));
        }

        return this.cozinhaService.salvarOuAtualizar(cozinha);
    }

    @PutMapping("/{id}")
    public Cozinha atualizar(@PathVariable Long id, @RequestBody Cozinha cozinha) {
        cozinha.setId(id);
        return this.cozinhaService.salvarOuAtualizar(cozinha);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void remover(@PathVariable Long id) {
        this.cozinhaService.remover(id);
    }

}
