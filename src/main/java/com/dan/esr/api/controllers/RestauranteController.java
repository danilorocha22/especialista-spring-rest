package com.dan.esr.api.controllers;

import com.dan.esr.api.assembler.RestauranteEntityAssembler;
import com.dan.esr.api.assembler.RestauranteModelAssembler;
import com.dan.esr.api.models.input.RestauranteInput;
import com.dan.esr.api.models.output.RestauranteOutput;
import com.dan.esr.domain.entities.Cozinha;
import com.dan.esr.domain.entities.Restaurante;
import com.dan.esr.domain.exceptions.CozinhaNaoEncontradaException;
import com.dan.esr.domain.exceptions.EntidadeNaoEncontradaException;
import com.dan.esr.domain.exceptions.NegocioException;
import com.dan.esr.domain.exceptions.ValidacaoException;
import com.dan.esr.domain.repositories.RestauranteRepository;
import com.dan.esr.domain.services.CadastroCozinhaService;
import com.dan.esr.domain.services.CadastroRestauranteService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.ReflectionUtils;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.SmartValidator;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static java.util.Comparator.comparingLong;

@RequiredArgsConstructor
@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

    private final RestauranteRepository restauranteRepo;

    private final CadastroRestauranteService restauranteService;

    private final CadastroCozinhaService cozinhaService;

    private final SmartValidator validator;

    @Autowired
    private RestauranteModelAssembler modelAssembler;

    @Autowired
    private RestauranteEntityAssembler entityAssembler;

    @GetMapping("/{id}")
    public RestauranteOutput buscarPorId(@PathVariable @Validated Long id) {
        Restaurante restauranteRegistro = this.restauranteService.buscarRestaurantePorId(id);
        return this.modelAssembler.toModel(restauranteRegistro);
    }

    @GetMapping
    public List<RestauranteOutput> listar() {
        return this.modelAssembler.toCollectionModel(this.restauranteRepo.findAll())
                .stream()
                .sorted(comparingLong(RestauranteOutput::getId))
                .toList();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RestauranteOutput salvar(@RequestBody @Valid RestauranteInput restauranteInput) {
        try {
            Restaurante restaurante = this.entityAssembler.toRestauranteDomain(restauranteInput);
            restaurante = this.restauranteService.salvarOuAtualizar(restaurante);
            return this.modelAssembler.toModel(restaurante);

        } catch (EntidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public RestauranteOutput atualizar(@PathVariable Long id, @Valid @RequestBody RestauranteInput restauranteInput) {
        restauranteInput.setId(id);

        try {
            Restaurante restauranteRegistro = this.restauranteService.buscarRestaurantePorId(id);
            this.entityAssembler.copyToRestauranteDomain(restauranteInput, restauranteRegistro);
            restauranteRegistro = this.restauranteService.salvarOuAtualizar(restauranteRegistro);
            return this.modelAssembler.toModel(restauranteRegistro);

        } catch (CozinhaNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @PatchMapping("/{id}")
    public RestauranteOutput atualizarParcial(@PathVariable Long id, @RequestBody Map<String, Object> campos,
                                              HttpServletRequest request) {

        Restaurante restauranteRegistro = this.restauranteService.buscarRestaurantePorId(id);
        mesclarCampos(campos, restauranteRegistro, request);
        validate(restauranteRegistro);
        restauranteRegistro = this.restauranteService.salvarOuAtualizar(restauranteRegistro);

        Cozinha cozinhaRegistro = this.cozinhaService.buscarCozinhaPorId(restauranteRegistro.getCozinha().getId());
        restauranteRegistro.setCozinha(cozinhaRegistro);

        return this.modelAssembler.toModel(restauranteRegistro);
    }

    @DeleteMapping("/{id}")
    public void remover(@PathVariable Long id) {
        this.restauranteService.remover(id);
    }

    @GetMapping("/por-taxa")
    public List<RestauranteOutput> buscarPorTaxa(BigDecimal taxaInicial, BigDecimal taxaFinal) {
        List<Restaurante> restaurantes = this.restauranteService.buscarRestaurantesPorTaxa(taxaInicial, taxaFinal);
        return this.modelAssembler.toCollectionModel(restaurantes);
    }

    @GetMapping("/por-nome-e-cozinha")
    public List<RestauranteOutput> buscarPorNomeECozinha(String nome, Long cozinhaId) {
        List<Restaurante> restaurantes = this.restauranteService.consultarPorNomeECozinhaId(nome, cozinhaId);
        return this.modelAssembler.toCollectionModel(restaurantes);
    }

    @GetMapping("/por-nome-e-frete")
    public List<RestauranteOutput> buscarPorNomeEFrete(String nome, BigDecimal freteInicial, BigDecimal freteFinal) {
        List<Restaurante> restaurantes = this.restauranteService.consultarPorNomeEFrete(nome, freteInicial, freteFinal);
        return this.modelAssembler.toCollectionModel(restaurantes);
    }

    @GetMapping("/primeiro-por-nome")
    public RestauranteOutput buscarPrimeiroPorNome(String nome) {
        Restaurante restaurante = this.restauranteService.buscarPrimeiroPorNome(nome);
        return this.modelAssembler.toModel(restaurante);
    }

    @GetMapping("/top2-por-nome")
    public List<RestauranteOutput> buscarTop2PorNome(String nome) {
        List<Restaurante> restaurantes = this.restauranteService.buscarTop2PorNome(nome);
        return this.modelAssembler.toCollectionModel(restaurantes);
    }

    @GetMapping("/count-por-cozinha")
    public int quantidadePorCozinha(Long cozinhaId) {
        return restauranteRepo.countByCozinhaId(cozinhaId);
    }

    @GetMapping("/com-frete-gratis")
    public List<RestauranteOutput> buscarComFreteGratis(String nome) {
        List<Restaurante> restaurantes = this.restauranteService.buscarComFreteGratis(nome);
        return this.modelAssembler.toCollectionModel(restaurantes);
    }

    @GetMapping("/primeiro")
    public RestauranteOutput restaurantePrimeiro() {
        Restaurante restaurante = this.restauranteService.buscarPrimeiroRestaurante();
        return this.modelAssembler.toModel(restaurante);
    }


    private void mesclarCampos(Map<String, Object> dadosOrigem, Restaurante restauranteDestino,
                               HttpServletRequest request) {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, true);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
        Restaurante restauranteOrigem;

        try {
            restauranteOrigem = objectMapper.convertValue(dadosOrigem, Restaurante.class);

        } catch (IllegalArgumentException ex) {
            Throwable rootCause = ExceptionUtils.getRootCause(ex);
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

    private void validate(Restaurante restaurante) {
        BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(restaurante, "restaurante");
        this.validator.validate(restaurante, bindingResult);

        if (bindingResult.hasErrors()) {
            throw new ValidacaoException(bindingResult);
        }
    }

}
