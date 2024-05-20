package com.dan.esr.api.controllers;

import com.dan.esr.api.models.input.formaspagamento.FormasPagamentoInput;
import com.dan.esr.api.models.output.FormasPagamentoOutput;
import com.dan.esr.core.assemblers.FormasPagamentoAssembler;
import com.dan.esr.domain.entities.FormasPagamento;
import com.dan.esr.domain.services.FormasPagamentoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/formas-pagamento")
public class FormasPagamentoController {
    private final FormasPagamentoService formasPagamentoService;
    private final FormasPagamentoAssembler formasDePagamentoAssembler;


    @GetMapping("/{id}")
    public FormasPagamentoOutput formaDePagamento(@PathVariable Long id) {
        FormasPagamento formasPagamento = this.formasPagamentoService.buscarPor(id);
        return this.formasDePagamentoAssembler.toModel(formasPagamento);
    }

    @GetMapping
    public List<FormasPagamentoOutput> todasAsFormasDePagamento() {
        List<FormasPagamento> formasPagamento = this.formasPagamentoService.buscarTodos();
        return this.formasDePagamentoAssembler.toCollectionModel(formasPagamento);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FormasPagamentoOutput cadastrarFormaDePagamento(
            @RequestBody @Valid FormasPagamentoInput FormasPagamentoInput) {

        FormasPagamento formasPagamento = this.formasDePagamentoAssembler.toDomain(FormasPagamentoInput);
        formasPagamento = this.formasPagamentoService.salvarOuAtualizar(formasPagamento);
        return this.formasDePagamentoAssembler.toModel(formasPagamento);
    }


    @PutMapping("/{id}")
    public FormasPagamentoOutput atualizarFormaDePagamento(
            @PathVariable Long id,
            @RequestBody @Valid FormasPagamentoInput FormasPagamentoInput
    ) {
        FormasPagamento formasPagamento = this.formasPagamentoService.buscarPor(id);
        this.formasDePagamentoAssembler.copyToDomain(FormasPagamentoInput, formasPagamento);
        formasPagamento = this.formasPagamentoService.salvarOuAtualizar(formasPagamento);
        return this.formasDePagamentoAssembler.toModel(formasPagamento);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removerFormaDePagamento(@PathVariable Long id) {
        this.formasPagamentoService.remover(id);

    }
}