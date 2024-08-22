package com.dan.esr.api.v1.controllers.restaurante;

import com.dan.esr.api.v1.models.input.restaurante.RestauranteInput;
import com.dan.esr.api.v1.models.output.restaurante.RestauranteOutput;
import com.dan.esr.api.v1.openapi.documentation.restaurante.RestauranteCadastroDocumentation;
import com.dan.esr.api.v1.assemblers.RestauranteEntityAssembler;
import com.dan.esr.api.v1.assemblers.RestauranteModelAssembler;
import com.dan.esr.domain.entities.Restaurante;
import com.dan.esr.domain.exceptions.ValidacaoException;
import com.dan.esr.domain.services.restaurante.RestauranteCadastroService;
import com.dan.esr.domain.services.restaurante.RestauranteConsultaService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.ReflectionUtils;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.SmartValidator;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.Map;

import static java.util.Objects.requireNonNull;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/restaurantes")
public class RestauranteCadastroController implements RestauranteCadastroDocumentation {
    private final RestauranteCadastroService restauranteCadastro;
    private final RestauranteConsultaService restauranteConsulta;
    private final RestauranteModelAssembler restauranteModelAssembler;
    private final RestauranteEntityAssembler restauranteEntityAssembler;
    private final SmartValidator validator;

    @Override
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(produces = APPLICATION_JSON_VALUE)
    public EntityModel<RestauranteOutput> novoRestaurante(@RequestBody @Valid RestauranteInput restauranteInput) {
        Restaurante restaurante = this.restauranteEntityAssembler.toDomain(restauranteInput);
        restaurante = this.restauranteCadastro.salvarOuAtualizar(restaurante);
        return EntityModel.of(
                this.restauranteModelAssembler.toModel(restaurante)
        );
    }

    @Override
    @PutMapping(path = "/{id}", produces = APPLICATION_JSON_VALUE)
    public EntityModel<RestauranteOutput> atualizarRestaurante(
            @PathVariable Long id,
            @RequestBody @Valid RestauranteInput restauranteInput
    ) {
        Restaurante restaurante = this.restauranteConsulta.buscarPor(id);
        this.restauranteEntityAssembler.copyToRestaurante(restauranteInput, restaurante);
        restaurante = this.restauranteCadastro.salvarOuAtualizar(restaurante);
        return EntityModel.of(
                this.restauranteModelAssembler.toModel(restaurante)
        );
    }

    @Override
    @PatchMapping(path = "/{id}", produces = APPLICATION_JSON_VALUE)
    public EntityModel<RestauranteOutput> atualizarParcial(
            @PathVariable Long id,
            @RequestBody Map<String, Object> campos,
            HttpServletRequest request
    ) {
        Restaurante restaurante = this.restauranteConsulta.buscarPor(id);
        mesclarCampos(campos, restaurante, request);
        validate(this.restauranteModelAssembler.toModelInput(restaurante));
        restaurante = this.restauranteCadastro.salvarOuAtualizar(restaurante);
        return EntityModel.of(
                this.restauranteModelAssembler.toModel(restaurante)
        );
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {
        this.restauranteCadastro.remover(id);
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
            requireNonNull(field).setAccessible(true);
            Object novoValor = ReflectionUtils.getField(field, restauranteOrigem);
            ReflectionUtils.setField(requireNonNull(field), restauranteDestino, novoValor);
        });
    }

    private void validate(RestauranteInput restauranteInput) {
        BeanPropertyBindingResult bindingResult =
                new BeanPropertyBindingResult(restauranteInput, "Restaurante");

        this.validator.validate(restauranteInput, bindingResult);
        if (bindingResult.hasErrors()) throw new ValidacaoException(bindingResult);
    }
}