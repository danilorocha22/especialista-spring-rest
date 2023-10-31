package com.dan.esr.api.controllers;

import com.dan.esr.domain.entities.Cidade;
import com.dan.esr.domain.exceptions.EstadoNaoEncontradoException;
import com.dan.esr.domain.exceptions.NegocioException;
import com.dan.esr.domain.repositories.CidadeRepository;
import com.dan.esr.domain.services.CadastroCidadeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/cidades")
public class CidadeController {

    private final CadastroCidadeService cidadeService;
    private final CidadeRepository cidadeRepo;

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



}
