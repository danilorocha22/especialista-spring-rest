package com.dan.esr.api.controllers;

import com.dan.esr.domain.entities.Estado;
import com.dan.esr.domain.repositories.EstadoRepository;
import com.dan.esr.domain.services.EstadoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/estados")
public class EstadoController {
    private final EstadoService cadastroEstado;
    private final EstadoRepository estadoRepo;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Estado> listar() {
        return this.estadoRepo.findAll();
    }

    @GetMapping("/{id}")
    public Estado buscarPorId(@PathVariable Long id) {
        return this.cadastroEstado.buscarEstadoPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Estado salvar(@RequestBody @Valid Estado estado) {
        return this.cadastroEstado.salvarOuAtualizar(estado);
    }

    @PutMapping("/{id}")
    public Estado atualizar(@PathVariable Long id, @Valid @RequestBody Estado estado) {
        Estado estadoRegistro = this.cadastroEstado.buscarEstadoPorId(id);
        BeanUtils.copyProperties(estado, estadoRegistro, "id");
        return this.cadastroEstado.salvarOuAtualizar(estadoRegistro);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void remover(@PathVariable Long id)   {
        this.cadastroEstado.remover(id);
    }

    /*@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    private List<Estado> listarEstadosApi() {
        List<Estado> estados = estadoService.estados();
        System.out.println("Quantidade Estados "+ estados.size());

        return estados;
    }*/

}
