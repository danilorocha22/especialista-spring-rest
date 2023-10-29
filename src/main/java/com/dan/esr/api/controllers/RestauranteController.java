package com.dan.esr.api.controllers;

import com.dan.esr.domain.entities.Restaurante;
import com.dan.esr.domain.exceptions.CozinhaNaoEncontradaException;
import com.dan.esr.domain.exceptions.EntidadeNaoEncontradaException;
import com.dan.esr.domain.exceptions.NegocioException;
import com.dan.esr.domain.repositories.RestauranteRepository;
import com.dan.esr.domain.services.CadastroCozinhaService;
import com.dan.esr.domain.services.CadastroRestauranteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RequiredArgsConstructor
@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

    private final RestauranteRepository restauranteRepo;
    private final CadastroRestauranteService restauranteService;
    private final CadastroCozinhaService cozinhaService;

    @GetMapping("/{id}")
    public Restaurante buscarPorId(@PathVariable Long id) {
        return this.restauranteService.buscarRestaurantePorId(id);
    }

    @GetMapping("/por-taxa")
    public List<Restaurante> buscarPorTaxa(BigDecimal taxaInicial, BigDecimal taxaFinal) {
        return this.restauranteService.buscarRestaurantesPorTaxa(taxaInicial, taxaFinal);
    }

    @GetMapping("/por-nome-e-cozinha")
    public List<Restaurante> buscarPorNomeECozinha(String nome, Long cozinhaId) {
        return this.restauranteService.consultarPorNomeECozinhaId(nome, cozinhaId);
    }

    @GetMapping("/por-nome-e-frete")
    public List<Restaurante> buscarPorNomeEFrete(String nome, BigDecimal freteInicial, BigDecimal freteFinal) {
        return this.restauranteService.consultarPorNomeEFrete(nome, freteInicial, freteFinal);
    }

    @GetMapping("/primeiro-por-nome")
    public Restaurante buscarPrimeiroPorNome(String nome) {
        return this.restauranteService.buscarPrimeiroPorNome(nome);
    }

    @GetMapping("/top2-por-nome")
    public List<Restaurante> buscarTop2PorNome(String nome) {
        return this.restauranteService.buscarTop2PorNome(nome);
    }

    @GetMapping("/count-por-cozinha")
    public int quantidadePorCozinha(Long cozinhaId) {
        return restauranteRepo.countByCozinhaId(cozinhaId);
    }

    @GetMapping("/com-frete-gratis")
    public List<Restaurante> buscarComFreteGratis(String nome) {
        return this.restauranteService.buscarComFreteGratis(nome);
    }

    @GetMapping("/primeiro")
    public Restaurante restaurantePrimeiro() {
        return this.restauranteService.buscarPrimeiroRestaurante();
    }

    @GetMapping
    public List<Restaurante> listar() {
        return restauranteRepo.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Restaurante salvar(@RequestBody Restaurante restaurante) {
        try {
            return this.restauranteService.salvarOuAtualizar(restaurante);
        }catch (EntidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public Restaurante atualizar(@PathVariable Long id, @RequestBody Restaurante restaurante) {
        restaurante.setId(id);

        try {
            return this.restauranteService.salvarOuAtualizar(restaurante);
        } catch (CozinhaNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @PatchMapping("/{id}")
    public Restaurante atualizarParcial(@PathVariable Long id, @RequestBody Map<String, Object> campos) {
        Restaurante restauranteRegistro = this.restauranteService.buscarRestaurantePorId(id);
        atualizarCampos(campos, restauranteRegistro);

        return this.restauranteService.salvarOuAtualizar(restauranteRegistro);
    }

    @DeleteMapping("/{id}")
    public void remover(@PathVariable Long id) {
        this.restauranteService.remover(id);
    }

    private void atualizarCampos(Map<String, Object> dadosOrigem, Restaurante restauranteDestino) {
        ObjectMapper objectMapper = new ObjectMapper();
        Restaurante restauranteOrigem = objectMapper.convertValue(dadosOrigem, Restaurante.class);

        dadosOrigem.forEach((nomeCampo, valorCampo) -> {
            Field field = ReflectionUtils.findField(Restaurante.class, nomeCampo);
            Objects.requireNonNull(field).setAccessible(true);
            Object novoValor = ReflectionUtils.getField(field, restauranteOrigem);
            ReflectionUtils.setField(Objects.requireNonNull(field), restauranteDestino, novoValor);
        });
    }

}
