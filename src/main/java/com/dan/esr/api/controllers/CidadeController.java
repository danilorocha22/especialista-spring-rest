package com.dan.esr.api.controllers;

import com.dan.esr.domain.entities.Cidade;
import com.dan.esr.domain.repositories.CidadeRepository;
import com.dan.esr.domain.services.CadastroCidadeService;
import com.dan.esr.domain.util.ValidarCamposObrigatoriosUtil;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.NotFound;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.dan.esr.domain.util.ValidarCamposObrigatoriosUtil.validarCampoObrigatorio;

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
        return this.cidadeService.salvar(cidade);
    }

    @PutMapping("/{id}")
    public Cidade atualizar(@PathVariable Long id, @RequestBody Cidade cidade) {
        validarCampoObrigatorio(cidade, "Cidade");
        Cidade cidadeRegistro = this.cidadeService.buscarCidadePorId(id);
        BeanUtils.copyProperties(cidade, cidadeRegistro, "id");

        return this.cidadeService.salvar(cidadeRegistro);
    }



}
