package com.dan.esr.api.v2.controllers.cozinha;

import com.dan.esr.api.v2.assemblers.CozinhaAssemblerV2;
import com.dan.esr.api.v2.models.input.CozinhaInputV2;
import com.dan.esr.api.v2.models.output.CozinhaOutputV2;
import com.dan.esr.domain.entities.Cozinha;
import com.dan.esr.domain.services.cozinha.CozinhaCadastroService;
import com.dan.esr.domain.services.cozinha.CozinhaConsultaService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/v2/cozinhas", produces = APPLICATION_JSON_VALUE)
public class CozinhaCadastroControllerV2 {
    private final CozinhaCadastroService cozinhaCadastro;
    private final CozinhaConsultaService  cozinhaConsulta;
    private final CozinhaAssemblerV2 cozinhaAssembler;

    @ResponseStatus(CREATED)
    @PreAuthorize("hasAuthority('EDITAR_COZINHAS')")
    @PostMapping(produces = APPLICATION_JSON_VALUE)
    public CozinhaOutputV2 novaCozinha(@RequestBody @Valid CozinhaInputV2 cozinhaInput) {
        Cozinha cozinha = this.cozinhaAssembler.toDomain(cozinhaInput);
        cozinha = this.cozinhaCadastro.salvarOuAtualizar(cozinha);
        return this.cozinhaAssembler.toModel(cozinha);
    }

    @PutMapping(path = "/{id}")
    public CozinhaOutputV2 atualizarCidade(
            @PathVariable Long id,
            @RequestBody @Valid CozinhaInputV2 cozinhaInput
    ) {
        Cozinha cozinha = this.cozinhaConsulta.buscarPor(id);
        this.cozinhaAssembler.copyToCozinhaDomain(cozinhaInput, cozinha);
        //copyProperties(cozinhaInput, cozinha, "id");
        cozinha = this.cozinhaCadastro.salvarOuAtualizar(cozinha);
        return this.cozinhaAssembler.toModel(cozinha);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void excluirCozinha(@PathVariable Long id) {
        this.cozinhaCadastro.remover(id);
    }
}