package com.dan.esr.api.controllers;

import com.dan.esr.api.exceptionhandler.Problema;
import com.dan.esr.domain.entities.Cidade;
import com.dan.esr.domain.exceptions.EntidadeNaoEncontradaException;
import com.dan.esr.domain.exceptions.EstadoNaoEncontradoException;
import com.dan.esr.domain.exceptions.NegocioException;
import com.dan.esr.domain.repositories.CidadeRepository;
import com.dan.esr.domain.services.CadastroCidadeService;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

import static com.dan.esr.api.exceptionhandler.Problema.novoProblema;

@AllArgsConstructor
@RestController
@RequestMapping("/cidades")
public class CidadeController {

    private CadastroCidadeService cidadeService;
    private CidadeRepository cidadeRepo;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Cidade> listar() {
        return this.cidadeRepo.findAll();
    }

    @GetMapping("/{id}")
    public Cidade buscarPorId(@PathVariable Long id) {
        return this.cidadeService.buscarCidadePorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cidade salvar(@RequestBody Cidade cidade) {
        try {
            return this.cidadeService.salvarOuAtualizar(cidade);
        }catch (EstadoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @PutMapping("/{id}")
    public Cidade atualizar(@PathVariable Long id, @RequestBody Cidade cidade) {
        Cidade cidadeRegistro = this.cidadeService.buscarCidadePorId(id);
        BeanUtils.copyProperties(cidade, cidadeRegistro, "id");

        try {
            return this.cidadeService.salvarOuAtualizar(cidadeRegistro);
        } catch (EstadoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void remover(@PathVariable Long id) {
        this.cidadeService.remover(id);
    }

    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    public ResponseEntity<?> tratarEntidadeNaoEncontradaException(EntidadeNaoEncontradaException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(novoProblema(e));
    }

    @ExceptionHandler(NegocioException.class)
    public ResponseEntity<?> tratarNegocioException(NegocioException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(novoProblema(e));
    }





}
