package com.dan.esr.api.v1.controllers.cozinha;

import com.dan.esr.api.v1.models.input.cozinha.CozinhaInput;
import com.dan.esr.api.v1.models.output.cozinha.CozinhaOutput;
import com.dan.esr.api.v1.openapi.documentation.cozinha.CozinhaCadastroDocumentation;
import com.dan.esr.api.v1.assemblers.CozinhaAssembler;
import com.dan.esr.domain.entities.Cozinha;
import com.dan.esr.domain.services.cozinha.CozinhaCadastroService;
import com.dan.esr.domain.services.cozinha.CozinhaConsultaService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/cozinhas")
public class CozinhaCadastroController implements CozinhaCadastroDocumentation {
    private final CozinhaCadastroService cozinhaCadastro;
    private final CozinhaConsultaService  cozinhaConsulta;
    private final CozinhaAssembler cozinhaAssembler;

    @Override
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(produces = APPLICATION_JSON_VALUE)
    public CozinhaOutput novaCozinha(@RequestBody @Valid CozinhaInput cozinhaInput) {
        Cozinha cozinha = this.cozinhaAssembler.toDomain(cozinhaInput);
        cozinha = this.cozinhaCadastro.salvarOuAtualizar(cozinha);
        return this.cozinhaAssembler.toModel(cozinha);
    }

    @Override
    @PutMapping(path = "/{id}", produces = APPLICATION_JSON_VALUE)
    public CozinhaOutput atualizarCidade(
            @PathVariable Long id,
            @RequestBody @Valid CozinhaInput cozinhaInput
    ) {
        Cozinha cozinha = this.cozinhaConsulta.buscarPor(id);
        this.cozinhaAssembler.copyToCozinhaDomain(cozinhaInput, cozinha);
        //copyProperties(cozinhaInput, cozinha, "id");
        cozinha = this.cozinhaCadastro.salvarOuAtualizar(cozinha);
        return this.cozinhaAssembler.toModel(cozinha);
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluirCozinha(@PathVariable Long id) {
        this.cozinhaCadastro.remover(id);
    }
}