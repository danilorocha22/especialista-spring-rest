package com.dan.esr.api.controllers.formapagamento;

import com.dan.esr.api.models.input.formapagamento.FormaPagamentoInput;
import com.dan.esr.api.models.output.formapagamento.FormaPagamentoOutput;
import com.dan.esr.api.openapi.documentation.formapagamento.FormasPagamentoDocumentation;
import com.dan.esr.core.assemblers.FormaPagamentoAssembler;
import com.dan.esr.domain.entities.FormaPagamento;
import com.dan.esr.domain.repositories.FormaPagamentoRepository;
import com.dan.esr.domain.services.formaspagamento.FormaPagamentoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping("/formas-pagamento")
public class FormaPagamentoController implements FormasPagamentoDocumentation {
    private final FormaPagamentoService formaPagamentoService;
    private final FormaPagamentoAssembler formasDePagamentoAssembler;
    private final FormaPagamentoRepository formaPagamentoRepository;

    @Override
    @GetMapping(path = "/{id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<FormaPagamentoOutput> formaPagamento(@PathVariable Long id, ServletWebRequest request) {
        String eTag = this.getDeepETag(request);
        FormaPagamento formaPagamento = this.formaPagamentoService.buscarPor(id);
        FormaPagamentoOutput pagamentoOutput = this.formasDePagamentoAssembler.toModel(formaPagamento);
        return ResponseEntity.ok()
                .eTag(eTag)
                .cacheControl(CacheControl.maxAge(5, TimeUnit.SECONDS))
                .body(pagamentoOutput);
    }

    @Override
    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<FormaPagamentoOutput>> formasPagamentos(ServletWebRequest request) {
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

    @Override
    @ResponseStatus(CREATED)
    @PostMapping(produces = APPLICATION_JSON_VALUE)
    public FormaPagamentoOutput novaFormaPagamento(
            @RequestBody @Valid FormaPagamentoInput FormaPagamentoInput
    ) {
        FormaPagamento formaPagamento = this.formasDePagamentoAssembler.toDomain(FormaPagamentoInput);
        formaPagamento = this.formaPagamentoService.salvarOuAtualizar(formaPagamento);
        return this.formasDePagamentoAssembler.toModel(formaPagamento);
    }

    @Override
    @PutMapping(path = "/{id}", produces = APPLICATION_JSON_VALUE)
    public FormaPagamentoOutput atualizarFormaPagamento(
            @PathVariable Long id,
            @RequestBody @Valid FormaPagamentoInput FormaPagamentoInput
    ) {
        FormaPagamento formaPagamento = this.formaPagamentoService.buscarPor(id);
        this.formasDePagamentoAssembler.copyToDomain(FormaPagamentoInput, formaPagamento);
        formaPagamento = this.formaPagamentoService.salvarOuAtualizar(formaPagamento);
        return this.formasDePagamentoAssembler.toModel(formaPagamento);
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void excluirFormaPagamento(@PathVariable Long id) {
        this.formaPagamentoService.remover(id);
    }
}