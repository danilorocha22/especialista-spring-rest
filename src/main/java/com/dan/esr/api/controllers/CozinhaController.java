package com.dan.esr.api.controllers;

import com.dan.esr.api.assembler.CozinhaEntityAssembler;
import com.dan.esr.api.assembler.CozinhaModelAssembler;
import com.dan.esr.api.models.CozinhasXML;
import com.dan.esr.api.models.input.CozinhaIdInput;
import com.dan.esr.api.models.output.CozinhaOutput;
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
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private CozinhaModelAssembler modelAssembler;

    @Autowired
    private CozinhaEntityAssembler entityAssembler;

    @GetMapping("/{id}")
    public CozinhaOutput buscarPorId(@PathVariable Long id) {
        Cozinha cozinha = this.cozinhaService.buscarCozinhaPorId(id);
        return this.modelAssembler.toModel(cozinha);
    }

    //@ResponseStatus(HttpStatus.OK) //retorna 200
    @GetMapping
    public List<CozinhaOutput> listar() {
        return this.cozinhaRepo.findAll().stream()
                .map(cozinha -> this.modelAssembler.toModel(cozinha))
                .toList();
    }

    @ResponseStatus(HttpStatus.CREATED) //retorna 201
    @PostMapping
    public CozinhaOutput salvar(@RequestBody @Valid CozinhaIdInput cozinhaIdInput) {
        if (Objects.nonNull(cozinhaIdInput.getId())) {
            throw new PropriedadeIlegalException(String.format(
                    "A propriedade 'id' com valor '%s' não pode ser informada", cozinhaIdInput.getId()));
        }

        Cozinha cozinha = this.cozinhaService.salvarOuAtualizar(this.entityAssembler.toRestauranteDomain(cozinhaIdInput));
        return this.modelAssembler.toModel(cozinha);
    }

    @PutMapping("/{id}")
    public CozinhaOutput atualizar(@PathVariable Long id, @Valid @RequestBody CozinhaIdInput cozinhaIdInput) {
        cozinhaIdInput.setId(id);
        Cozinha cozinha = this.cozinhaService.salvarOuAtualizar(this.entityAssembler.toRestauranteDomain(cozinhaIdInput));
        return this.modelAssembler.toModel(cozinha);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void remover(@PathVariable Long id) {
        this.cozinhaService.remover(id);
    }

    @GetMapping("/por-nome")
    public List<CozinhaOutput> buscarPorNome(String nome) {
        return this.cozinhaService.buscarTodasPorNome(nome).stream()
                .map(cozinha -> this.modelAssembler.toModel(cozinha))
                .toList();
    }

    @GetMapping("/unica-por-nome")
    public CozinhaOutput buscarUnicaPorNome(String nome) {
        Cozinha cozinha = this.cozinhaService.buscarUnicaPorNome(nome);
        return this.modelAssembler.toModel(cozinha) ;
    }

    @GetMapping("/existe")
    public boolean existe(String nome) {
        return cozinhaRepo.existsByNome(nome);
    }

    @GetMapping("/primeiro")
    public CozinhaOutput cozinhaPrimeiro() {
        Cozinha cozinha = this.cozinhaService.buscarPrimeiro();
        return this.modelAssembler.toModel(cozinha) ;
    }

    @GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
    public CozinhasXML listarXml() {
        return new CozinhasXML(cozinhaRepo.findAll());
    }

}
