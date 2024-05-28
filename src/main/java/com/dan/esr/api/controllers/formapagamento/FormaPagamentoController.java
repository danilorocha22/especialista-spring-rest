package com.dan.esr.api.controllers.formapagamento;

import com.dan.esr.api.models.input.formapagamento.FormaPagamentoInput;
import com.dan.esr.api.models.output.formapagamento.FormaPagamentoOutput;
import com.dan.esr.core.assemblers.FormaPagamentoAssembler;
import com.dan.esr.domain.entities.FormaPagamento;
import com.dan.esr.domain.services.formaspagamento.FormaPagamentoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/formas-pagamento")
public class FormaPagamentoController {
    private final FormaPagamentoService formaPagamentoService;
    private final FormaPagamentoAssembler formasDePagamentoAssembler;


    @GetMapping("/{id}")
    public FormaPagamentoOutput formaDePagamento(@PathVariable Long id) {
        FormaPagamento formaPagamento = this.formaPagamentoService.buscarPor(id);
        return this.formasDePagamentoAssembler.toModel(formaPagamento);
    }

    @GetMapping
    public List<FormaPagamentoOutput> todasAsFormasDePagamento() {
        List<FormaPagamento> formaPagamento = this.formaPagamentoService.buscarTodos();
        return this.formasDePagamentoAssembler.toCollectionModel(formaPagamento);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FormaPagamentoOutput cadastrarFormaDePagamento(
            @RequestBody @Valid FormaPagamentoInput FormaPagamentoInput) {

        FormaPagamento formaPagamento = this.formasDePagamentoAssembler.toDomain(FormaPagamentoInput);
        formaPagamento = this.formaPagamentoService.salvarOuAtualizar(formaPagamento);
        return this.formasDePagamentoAssembler.toModel(formaPagamento);
    }


    @PutMapping("/{id}")
    public FormaPagamentoOutput atualizarFormaDePagamento(
            @PathVariable Long id,
            @RequestBody @Valid FormaPagamentoInput FormaPagamentoInput
    ) {
        FormaPagamento formaPagamento = this.formaPagamentoService.buscarPor(id);
        this.formasDePagamentoAssembler.copyToDomain(FormaPagamentoInput, formaPagamento);
        formaPagamento = this.formaPagamentoService.salvarOuAtualizar(formaPagamento);
        return this.formasDePagamentoAssembler.toModel(formaPagamento);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removerFormaDePagamento(@PathVariable Long id) {
        this.formaPagamentoService.remover(id);

    }
}