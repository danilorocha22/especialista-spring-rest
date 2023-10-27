package com.dan.esr.api.controllers;

import com.dan.esr.api.models.CozinhasXML;
import com.dan.esr.domain.entities.Cozinha;
import com.dan.esr.domain.repositories.CozinhaRepository;
import com.dan.esr.domain.services.CadastroCozinhaService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.web.servlet.function.ServerResponse.status;

@RequiredArgsConstructor
@RestController
@RequestMapping("/cozinhas")
public class CozinhaController {

    private final CozinhaRepository cozinhaRepository;
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
        return cozinhaRepository.existsByNome(nome);
    }

    @GetMapping("/primeiro")
    public ResponseEntity<Cozinha> cozinhaPrimeiro() {
        return cozinhaRepository.buscarPrimeiro().map(ResponseEntity::ok)
                .orElseGet(()-> ResponseEntity.notFound().build());
    }

    @ResponseStatus(HttpStatus.OK) //retorna 200
    @GetMapping
    public List<Cozinha> listar() {
        return cozinhaRepository.findAll();
    }

    @ResponseStatus(HttpStatus.OK) //retorna 200
    @GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
    public CozinhasXML listarXml() {
        return new CozinhasXML(cozinhaRepository.findAll());
    }

    @ResponseStatus(HttpStatus.CREATED) //retorna 201
    @PostMapping
    public Cozinha criar(@RequestBody Cozinha cozinha) {
        return this.cozinhaService.salvarOuAtualizar(cozinha);
    }

    @PutMapping("/{id}")
    public Cozinha atualizar(@PathVariable Long id, @RequestBody Cozinha cozinha) {
        System.out.println("COZINHA "+ cozinha);
        Cozinha cozinhaRegistro = this.cozinhaService.buscarCozinhaPorId(id);
        BeanUtils.copyProperties(cozinha, cozinhaRegistro, "id");
        return this.cozinhaService.salvarOuAtualizar(cozinhaRegistro);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void remover(@PathVariable Long id) {
        this.cozinhaService.remover(id);
    }

}
