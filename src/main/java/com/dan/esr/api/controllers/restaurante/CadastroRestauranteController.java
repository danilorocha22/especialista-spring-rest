package com.dan.esr.api.controllers.restaurante;

import com.dan.esr.api.models.input.produto.ProdutoInput;
import com.dan.esr.api.models.input.restaurante.RestauranteInput;
import com.dan.esr.api.models.output.restaurante.RestauranteOutput;
import com.dan.esr.api.models.output.restaurante.RestauranteProdutosOutput;
import com.dan.esr.api.models.output.restaurante.RestauranteResponsaveisOutput;
import com.dan.esr.api.models.output.view.RestauranteView;
import com.dan.esr.core.assemblers.ProdutoAssembler;
import com.dan.esr.core.assemblers.RestauranteEntityAssembler;
import com.dan.esr.core.assemblers.RestauranteModelAssembler;
import com.dan.esr.domain.entities.Produto;
import com.dan.esr.domain.entities.Restaurante;
import com.dan.esr.domain.exceptions.ValidacaoException;
import com.dan.esr.domain.services.restaurante.RestauranteCadastroService;
import com.dan.esr.domain.services.restaurante.RestauranteConsultaService;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.ReflectionUtils;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.SmartValidator;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("/restaurantes")
public class CadastroRestauranteController {
    private final RestauranteCadastroService restauranteCadastro;
    private final RestauranteConsultaService restauranteConsulta;
    private final RestauranteModelAssembler restauranteModelAssembler;
    private final RestauranteEntityAssembler restauranteEntityAssembler;
    private final ProdutoAssembler produtoAssembler;
    private final SmartValidator validator;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RestauranteOutput novoRestaurante(@RequestBody @Valid RestauranteInput restauranteInput) {
        Restaurante restaurante = this.restauranteEntityAssembler.toDomain(restauranteInput);
        restaurante = this.restauranteCadastro.salvarOuAtualizar(restaurante);
        return this.restauranteModelAssembler.toModel(restaurante);
    }

    @PostMapping("/{id}/produtos")
    @ResponseStatus(HttpStatus.CREATED)
    public RestauranteProdutosOutput novoProduto(
            @PathVariable("id") Long restauranteId,
            @RequestBody @Valid ProdutoInput produtoInput
    ) {
        Produto produto = this.produtoAssembler.toDomain(produtoInput);
        Restaurante restaurante = this.restauranteConsulta.buscarPor(restauranteId);
        restaurante = this.restauranteCadastro.novoProduto(restaurante, produto);
        return this.restauranteModelAssembler.toModelComProdutos(restaurante);
    }

    @PutMapping("/{id}")
    public RestauranteOutput atualizarRestaurante(
            @PathVariable Long id,
            @RequestBody @Valid RestauranteInput restauranteInput
    ) {
        Restaurante restaurante = this.restauranteConsulta.buscarPor(id);
        this.restauranteEntityAssembler.copyToRestauranteDomain(restauranteInput, restaurante);
        System.out.printf("NOME DO RESTAURANTE: %s\n", restaurante.getNome());
        restaurante = this.restauranteCadastro.salvarOuAtualizar(restaurante);
        return this.restauranteModelAssembler.toModel(restaurante);
    }

    @PutMapping("/{id}/ativo")
    @JsonView(RestauranteView.Status.class)
    public RestauranteOutput ativarRestaurante(@PathVariable Long id) {
        Restaurante restauranteAtivo = this.restauranteCadastro.ativar(id);
        return restauranteModelAssembler.toModel(restauranteAtivo);
    }

    @PutMapping("/{id}/abertura")
    public RestauranteOutput abertura(@PathVariable Long id) {
        Restaurante restaurante = this.restauranteCadastro.abrir(id);
        return this.restauranteModelAssembler.toModel(restaurante);
    }

    @PutMapping("/{id}/fechamento")
    public RestauranteOutput fechamento(@PathVariable Long id) {
        Restaurante restaurante = this.restauranteCadastro.fechar(id);
        return this.restauranteModelAssembler.toModel(restaurante);
    }

    @PutMapping("/{restauranteId}/formas-pagamento/{formasPagamentoId}")
    public RestauranteOutput adicionarFormaPagamento(
            @PathVariable Long restauranteId,
            @PathVariable Long formasPagamentoId
    ) {
        Restaurante restaurante = this.restauranteCadastro.adicionarFormaPagamento(
                restauranteId, formasPagamentoId);
        return this.restauranteModelAssembler.toModel(restaurante);
    }

    @PutMapping("/{restauranteId}/responsaveis/{usuarioId}")
    public RestauranteResponsaveisOutput adicionarResponsavel(
            @PathVariable Long restauranteId,
            @PathVariable Long usuarioId
    ) {
        Restaurante restaurante = this.restauranteCadastro.adicionarResponsavel(restauranteId, usuarioId);
        return this.restauranteModelAssembler.toModelComResponsaveis(restaurante);
    }

    @PutMapping("/ativacoes")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void ativacoes(@RequestBody List<Long> ids) {
        this.restauranteCadastro.ativacoes(ids);
    }

    @PatchMapping("/{id}")
    public RestauranteOutput atualizarParcial(
            @PathVariable Long id,
            @RequestBody Map<String, Object> campos,
            HttpServletRequest request
    ) {
        Restaurante restaurante = this.restauranteConsulta.buscarPor(id);
        mesclarCampos(campos, restaurante, request);
        validate(this.restauranteModelAssembler.toModelInput(restaurante));
        restaurante = this.restauranteCadastro.salvarOuAtualizar(restaurante);
        return this.restauranteModelAssembler.toModel(restaurante);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id) {
        this.restauranteCadastro.remover(id);
    }

    @DeleteMapping("/{id}/ativo")
    @JsonView(RestauranteView.Status.class)
    public RestauranteOutput inativar(@PathVariable Long id) {
        Restaurante restauranteInativo = this.restauranteCadastro.desativar(id);
        return restauranteModelAssembler.toModel(restauranteInativo);
    }

    @DeleteMapping("/{restauranteId}/formas-pagamento/{formaPagamentoId}")
    public RestauranteOutput removerFormaPagamento(
            @PathVariable Long restauranteId,
            @PathVariable Long formaPagamentoId
    ) {
        Restaurante restaurante = this.restauranteCadastro.removerFormaPagamento(restauranteId, formaPagamentoId);
        return this.restauranteModelAssembler.toModel(restaurante);
    }

    @DeleteMapping("/{restauranteId}/responsaveis/{usuarioId}")
    public RestauranteResponsaveisOutput removerResponsavel(
            @PathVariable Long restauranteId,
            @PathVariable Long usuarioId
    ) {
        Restaurante restaurante = this.restauranteCadastro.removerResponsavel(restauranteId, usuarioId);
        return this.restauranteModelAssembler.toModelComResponsaveis(restaurante);
    }

    @DeleteMapping("/ativacoes")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desativacoes(@RequestBody List<Long> ids) {
        this.restauranteCadastro.desativacoes(ids);
    }

    private void mesclarCampos(
            Map<String, Object> dadosOrigem,
            Restaurante restauranteDestino,
            HttpServletRequest request
    ) {
        Restaurante restauranteOrigem;

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, true);
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
            restauranteOrigem = objectMapper.convertValue(dadosOrigem, Restaurante.class);

        } catch (IllegalArgumentException ex) {
            Throwable rootCause = ExceptionUtils.getRootCause(ex);//ExceptionUtils classe da lib commons-lang3, para obter a raiz do erro
            HttpInputMessage inputMessage = new ServletServerHttpRequest(request);
            throw new HttpMessageNotReadableException(ex.getMessage(), rootCause, inputMessage);
        }

        dadosOrigem.forEach((nomeCampo, valorCampo) -> {
            Field field = ReflectionUtils.findField(Restaurante.class, nomeCampo);
            Objects.requireNonNull(field).setAccessible(true);
            Object novoValor = ReflectionUtils.getField(field, restauranteOrigem);
            ReflectionUtils.setField(Objects.requireNonNull(field), restauranteDestino, novoValor);
        });
    }

    private void validate(RestauranteInput restauranteInput) {
        BeanPropertyBindingResult bindingResult =
                new BeanPropertyBindingResult(restauranteInput, "Restaurante");

        this.validator.validate(restauranteInput, bindingResult);

        if (bindingResult.hasErrors()) throw new ValidacaoException(bindingResult);
    }
}