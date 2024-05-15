package com.dan.esr.api.controllers;

import com.dan.esr.api.assembler.CozinhaAssembler;
import com.dan.esr.api.models.input.CozinhaInput;
import com.dan.esr.api.models.output.CozinhaOutput;
import com.dan.esr.domain.entities.Cozinha;
import com.dan.esr.domain.exceptions.PropriedadeIlegalException;
import com.dan.esr.domain.services.CozinhaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static com.dan.esr.core.util.MessagesUtil.MSG_PROPRIEDADE_ILEGAL;
import static com.dan.esr.core.util.ValidacaoCampoObrigatorioUtil.validarCampoObrigatorio;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cozinhas")
public class CozinhaTransactionController {
    private final CozinhaService cozinhaService;
    private final CozinhaAssembler cozinhaAssembler;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) //retorna 201
    public CozinhaOutput salvar(@RequestBody @Valid CozinhaInput cozinhaInput) {
        Cozinha cozinha = this.cozinhaAssembler.toDomain(cozinhaInput);
        if (cozinha.isExiste())
            throw new PropriedadeIlegalException(MSG_PROPRIEDADE_ILEGAL.formatted("ID"));
        cozinha = this.cozinhaService.salvarOuAtualizar(cozinha);
        return this.cozinhaAssembler.toModel(cozinha);
    }

    @PutMapping("/{id}")
    public CozinhaOutput atualizar(
            @PathVariable Long id,
            @RequestBody @Valid CozinhaInput cozinhaInput
    ) {
        validarCampoObrigatorio(id, "ID");
        Cozinha cozinha = this.cozinhaAssembler.toDomain(cozinhaInput);
        cozinha.setId(id);
        cozinha = this.cozinhaService.salvarOuAtualizar(cozinha);
        return this.cozinhaAssembler.toModel(cozinha);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void remover(@PathVariable Long id) {
        validarCampoObrigatorio(id, "ID");
        this.cozinhaService.remover(id);
    }
}