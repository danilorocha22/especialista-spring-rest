package com.dan.esr.api.controllers.formapagamento;

import com.dan.esr.api.models.input.formapagamento.FormaPagamentoInput;
import com.dan.esr.api.models.output.formapagamento.FormaPagamentoOutput;
import com.dan.esr.core.assemblers.FormaPagamentoAssembler;
import com.dan.esr.domain.entities.FormaPagamento;
import com.dan.esr.domain.repositories.FormasPagamentoRepository;
import com.dan.esr.domain.services.formaspagamento.FormaPagamentoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequiredArgsConstructor
@RequestMapping("/formas-pagamento")
public class FormaPagamentoController {
    private final FormaPagamentoService formaPagamentoService;
    private final FormaPagamentoAssembler formasDePagamentoAssembler;
    private final FormasPagamentoRepository formaPagamentoRepository;


    @GetMapping("/{id}")
    public ResponseEntity<FormaPagamentoOutput> formaDePagamento(@PathVariable Long id, ServletWebRequest request) {
        String eTag = this.getDeepETag(request);
        FormaPagamento formaPagamento = this.formaPagamentoService.buscarPor(id);
        FormaPagamentoOutput pagamentoOutput = this.formasDePagamentoAssembler.toModel(formaPagamento);
        return ResponseEntity.ok()
                .eTag(eTag)
                .cacheControl(CacheControl.maxAge(5, TimeUnit.SECONDS))
                .body(pagamentoOutput);
    }

    @GetMapping
    public ResponseEntity<List<FormaPagamentoOutput>> formasDePagamentos(ServletWebRequest request) {
        String eTag = this.getDeepETag(request);
        if (request.checkNotModified(eTag))
            return null;

        List<FormaPagamento> formaPagamento = this.formaPagamentoService.buscarTodos();
        List<FormaPagamentoOutput> formasPagamentosModel =
                this.formasDePagamentoAssembler.toCollectionModel(formaPagamento);

        return ResponseEntity.ok()
                //.cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS).cachePrivate())
                //.cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS).cachePublic())
                //.cacheControl(CacheControl.noCache())
                //.cacheControl(CacheControl.noStore())
                //.header("Etag", eTag)
                .eTag(eTag)
                .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
                .body(formasPagamentosModel);
    }

    private String getDeepETag(ServletWebRequest request) {
        ShallowEtagHeaderFilter.disableContentCaching(request.getRequest());
        OffsetDateTime dataAtualizacao = this.formaPagamentoRepository.getDataAtualizacaoMaisRecente()
                .orElse(OffsetDateTime.now());
        return String.valueOf(dataAtualizacao.toEpochSecond());
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