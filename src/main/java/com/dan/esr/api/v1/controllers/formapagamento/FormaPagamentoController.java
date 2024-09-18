package com.dan.esr.api.v1.controllers.formapagamento;

import com.dan.esr.api.v1.models.input.formapagamento.FormaPagamentoInput;
import com.dan.esr.api.v1.models.output.formapagamento.FormaPagamentoOutput;
import com.dan.esr.api.v1.openapi.documentation.formapagamento.FormasPagamentoDocumentation;
import com.dan.esr.api.v1.assemblers.FormaPagamentoAssembler;
import com.dan.esr.core.security.CheckSecurity;
import com.dan.esr.domain.entities.FormaPagamento;
import com.dan.esr.domain.repositories.FormaPagamentoRepository;
import com.dan.esr.domain.services.formaspagamento.FormaPagamentoService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
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
@RequestMapping("/v1/formas-pagamento")
public class FormaPagamentoController implements FormasPagamentoDocumentation {
    private final FormaPagamentoService formaPagamentoService;
    private final FormaPagamentoAssembler formasDePagamentoAssembler;
    private final FormaPagamentoRepository formaPagamentoRepository;

    @Override
    @CheckSecurity.FormasPagamentos.PodeConsultar
    @GetMapping(path = "/{id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<EntityModel<FormaPagamentoOutput>> formaPagamento(@PathVariable Long id, ServletWebRequest request) {
        String eTag = this.getDeepETag(request);
        FormaPagamento formaPagamento = this.formaPagamentoService.buscarPor(id);
        FormaPagamentoOutput formaPagamentoOutput = this.formasDePagamentoAssembler.toModel(formaPagamento);
        return ResponseEntity.ok()
                .eTag(eTag)
                .cacheControl(CacheControl.maxAge(5, TimeUnit.SECONDS))
                .body(EntityModel.of(formaPagamentoOutput));
    }

    @Override
    @CheckSecurity.FormasPagamentos.PodeConsultar
    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<CollectionModel<FormaPagamentoOutput>> formasPagamentos(ServletWebRequest request) {
        String eTag = this.getDeepETag(request);
        if (request.checkNotModified(eTag))
            return null;

        List<FormaPagamento> formaPagamentos = this.formaPagamentoService.buscarTodos();
        CollectionModel<FormaPagamentoOutput> collectionModel
                = this.formasDePagamentoAssembler.toCollectionModel(formaPagamentos);

        return ResponseEntity.ok()
                //.cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS).cachePrivate())
                //.cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS).cachePublic())
                //.cacheControl(CacheControl.noCache())
                //.cacheControl(CacheControl.noStore())
                //.header("Etag", eTag)
                .eTag(eTag)
                .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
                .body(collectionModel);
    }

    private String getDeepETag(ServletWebRequest request) {
        ShallowEtagHeaderFilter.disableContentCaching(request.getRequest());
        OffsetDateTime dataAtualizacao = this.formaPagamentoRepository.getDataAtualizacaoMaisRecente()
                .orElse(OffsetDateTime.now());
        return String.valueOf(dataAtualizacao.toEpochSecond());
    }

    @Override
    @ResponseStatus(CREATED)
    @CheckSecurity.FormasPagamentos.PodeEditar
    @PostMapping(produces = APPLICATION_JSON_VALUE)
    public FormaPagamentoOutput novaFormaPagamento(
            @RequestBody @Valid FormaPagamentoInput formaPagamentoInput
    ) {
        FormaPagamento formaPagamento = this.formasDePagamentoAssembler.toDomain(formaPagamentoInput);
        formaPagamento = this.formaPagamentoService.salvarOuAtualizar(formaPagamento);
        return this.formasDePagamentoAssembler.toModel(formaPagamento);
    }

    @Override
    @CheckSecurity.FormasPagamentos.PodeEditar
    @PutMapping(path = "/{id}", produces = APPLICATION_JSON_VALUE)
    public FormaPagamentoOutput atualizarFormaPagamento(
            @PathVariable Long id,
            @RequestBody @Valid FormaPagamentoInput formaPagamentoInput
    ) {
        FormaPagamento formaPagamento = this.formaPagamentoService.buscarPor(id);
        this.formasDePagamentoAssembler.copyToDomain(formaPagamentoInput, formaPagamento);
        formaPagamento = this.formaPagamentoService.salvarOuAtualizar(formaPagamento);
        return this.formasDePagamentoAssembler.toModel(formaPagamento);
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    @CheckSecurity.FormasPagamentos.PodeEditar
    public void excluirFormaPagamento(@PathVariable Long id) {
        this.formaPagamentoService.remover(id);
    }
}