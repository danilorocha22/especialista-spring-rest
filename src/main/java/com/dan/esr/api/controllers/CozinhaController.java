package com.dan.esr.api.controllers;

import com.dan.esr.api.models.CozinhasXML;
import com.dan.esr.domain.entities.Cozinha;
import com.dan.esr.domain.exceptions.EntidadeComAtributoNuloException;
import com.dan.esr.domain.exceptions.EntidadeEmUsoException;
import com.dan.esr.domain.exceptions.EntidadeNaoEncontradaException;
import com.dan.esr.domain.repositories.CozinhaRepository;
import com.dan.esr.domain.services.CadastroCozinhaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.springframework.web.servlet.function.ServerResponse.status;

@RequiredArgsConstructor
@RestController
@RequestMapping("/cozinhas")
public class CozinhaController {

    private final CozinhaRepository cozinhaRepository;
    private final CadastroCozinhaService cozinhaService;

    @GetMapping("/{id}")
    public ResponseEntity<Cozinha> buscarPorId(@PathVariable Long id) {
        Optional<Cozinha> optionalCozinha = cozinhaRepository.findById(id);
        return optionalCozinha.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/por-nome")
    public ResponseEntity<List<?>> buscarPorNome(String nome) {
        try {
            return ResponseEntity.ok(cozinhaService.buscarPorNome(nome));
        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(List.of(String.format("Não existe cozinha cadastrada com nome: '%s'", nome)));
        }
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

    //@ResponseStatus(HttpStatus.CREATED) //retorna 201
    @PostMapping
    public ResponseEntity<?> criar(@RequestBody Cozinha cozinha) {
        try {
            Cozinha registro = cozinhaService.adicionarOuAtualizar(cozinha);
            return ResponseEntity.status(HttpStatus.CREATED).body(registro);
        } catch (EntidadeComAtributoNuloException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody Cozinha cozinha) {
        if (cozinhaRepository.findById(id).isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(String.format(
                    "Não existe cozinha cadastrada com ID %s", id));

        try {
            cozinha.setId(id);
            return ResponseEntity.ok(cozinhaService.adicionarOuAtualizar(cozinha));
        } catch (EntidadeComAtributoNuloException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    //@ResponseStatus(HttpStatus.NO_CONTENT) //retorna 204)
    @DeleteMapping("/{id}")
    public ResponseEntity<?> remover(@PathVariable Long id) {
        try {
            cozinhaService.remover(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(String.format(
                    "Cozinha de ID %s, excluída com sucesso.", id));

        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());

        } catch (EntidadeEmUsoException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

}
