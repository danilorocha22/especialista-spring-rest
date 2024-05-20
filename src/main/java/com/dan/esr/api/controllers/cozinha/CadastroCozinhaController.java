package com.dan.esr.api.controllers.cozinha;

import com.dan.esr.api.models.input.cozinha.CozinhaInput;
import com.dan.esr.api.models.output.CozinhaOutput;
import com.dan.esr.core.assemblers.CozinhaAssembler;
import com.dan.esr.domain.entities.Cozinha;
import com.dan.esr.domain.services.cozinha.CozinhaCadastroService;
import com.dan.esr.domain.services.cozinha.CozinhaConsultaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cozinhas")
public class CadastroCozinhaController {
    private final CozinhaCadastroService cozinhaCadastro;
    private final CozinhaConsultaService  cozinhaConsulta;
    private final CozinhaAssembler cozinhaAssembler;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) //retorna 201
    public CozinhaOutput salvar(@RequestBody @Valid CozinhaInput cozinhaInput) {
        Cozinha cozinha = this.cozinhaAssembler.toDomain(cozinhaInput);
        cozinha = this.cozinhaCadastro.salvarOuAtualizar(cozinha);
        return this.cozinhaAssembler.toModel(cozinha);
    }

    @PutMapping("/{id}")
    public CozinhaOutput atualizar(
            @PathVariable Long id,
            @RequestBody @Valid CozinhaInput cozinhaInput
    ) {
        Cozinha cozinha = this.cozinhaConsulta.buscarPor(id);
        this.cozinhaAssembler.copyToCozinhaDomain(cozinhaInput, cozinha);
        //copyProperties(cozinhaInput, cozinha, "id");
        cozinha = this.cozinhaCadastro.salvarOuAtualizar(cozinha);
        return this.cozinhaAssembler.toModel(cozinha);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void remover(@PathVariable Long id) {
        this.cozinhaCadastro.remover(id);
    }
}