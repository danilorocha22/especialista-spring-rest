package com.dan.esr.api.controllers;

import com.dan.esr.domain.entities.Cozinha;
import com.dan.esr.domain.entities.Restaurante;
import com.dan.esr.domain.exceptions.EntidadeEmUsoException;
import com.dan.esr.domain.exceptions.EntidadeNaoEncontradaException;
import com.dan.esr.domain.repositories.RestauranteRepository;
import com.dan.esr.domain.services.CadastroRestauranteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

    private final RestauranteRepository restauranteRepository;
    private final CadastroRestauranteService restauranteService;

    @GetMapping("/{id}")
    public Restaurante buscarPorId(@PathVariable Long id) {
        return this.restauranteService.buscarPorId(id);
    }

    @GetMapping("/por-taxa")
    public List<Restaurante> buscarPorTaxa(BigDecimal taxaInicial, BigDecimal taxaFinal) {
        return this.restauranteService.buscarRestaurantesPorTaxa(taxaInicial, taxaFinal);

        /*try {
            List<Restaurante> restaurantes = restauranteService.buscarRestaurantesPorTaxa(taxaInicial, taxaFinal);
            return ResponseEntity.ok(restaurantes);
        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(List.of(e.getMessage()));
        }*/
    }

    @GetMapping("/por-nome-e-cozinha")
    public ResponseEntity<List<Restaurante>> buscarPorNomeECozinha(String nome, Long cozinhaId) {
        List<Restaurante> restaurantes = restauranteRepository.consultarPorNomeECozinhaId(nome, cozinhaId);
        if (restaurantes.isEmpty())
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(restaurantes);
    }

    @GetMapping("/por-nome-e-frete")
    public ResponseEntity<List<Restaurante>> buscarPorNomeEFrete(String nome, BigDecimal freteInicial,
                                                                 BigDecimal freteFinal) {
        List<Restaurante> restaurantes = restauranteRepository.find(nome, freteInicial, freteFinal);
        if (restaurantes.isEmpty())
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(restaurantes);
    }

    @GetMapping("/primeiro-por-nome")
    public ResponseEntity<Restaurante> buscarPrimeiroPorNome(String nome) {
        return restauranteRepository.findFirstRestauranteByNomeContaining(nome)
                .map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/top2-por-nome")
    public ResponseEntity<List<Restaurante>> buscarTop2PorNome(String nome) {
        List<Restaurante> restaurantes = restauranteRepository.findTop2ByNomeContaining(nome);
        if (restaurantes.isEmpty())
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(restaurantes);
    }

    @GetMapping("/count-por-cozinha")
    public int quantidadePorCozinha(Long cozinhaId) {
        return restauranteRepository.countByCozinhaId(cozinhaId);
    }

    @GetMapping("/com-frete-gratis")
    public ResponseEntity<List<Restaurante>> buscarComFreteGratis(String nome) {
        return ResponseEntity.ok(restauranteRepository.findComFreteGratis(nome));
    }

    @GetMapping("/primeiro")
    public ResponseEntity<Restaurante> restaurantePrimeiro() {
        return restauranteRepository.buscarPrimeiro().map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<Restaurante> listar() {
        return restauranteRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<?> adicionar(@RequestBody Restaurante restaurante) {
        try {
            Restaurante registro = restauranteService.salvarOuAtualizar(restaurante);
            return ResponseEntity.status(HttpStatus.CREATED).body(registro);

        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.badRequest().body(e.getMessage());

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(String.format(
                    "Não foi possível adicionar o %s. Erro: %s", "restaurante", e.getMessage()
            ));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody Restaurante restaurante) {
        Optional<Restaurante> optionalRestaurante = restauranteRepository.findById(id);

        if (optionalRestaurante.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(String.format(
                    "Não existe restaurante cadastrado com ID %s", id));

        try {
            restaurante.setId(id);
            return ResponseEntity.ok(restauranteService.salvarOuAtualizar(restaurante));
        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(String.format(
                    "Não foi possível atualizar o restaurante de ID %s. Erro: %s", id, e.getMessage()
            ));
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> atualizarParcial(@PathVariable Long id, @RequestBody Map<String, Object> campos) {
        Restaurante registro = restauranteRepository.findById(id).orElse(null);

        if (Objects.isNull(registro))
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(String.format(
                    "Não existe restaurante cadastrado com ID %s", id));

        try {
            merge(campos, registro);
            return ResponseEntity.ok(restauranteService.salvarOuAtualizar(registro));

        } catch (EntidadeNaoEncontradaException e) {
            ObjectMapper objectMapper = new ObjectMapper();
            Cozinha cozinha = objectMapper.convertValue(campos.get("cozinha"), Cozinha.class);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(String.format(
                    "Não existe cozinha cadastrada com ID %s", cozinha.getId()));

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(String.format(
                    "Não foi possível atualizar o restaurante de ID %s. Erro: %s", id, e.getMessage()));
        }

    }

    @DeleteMapping("/{id}")
    public void remover(@PathVariable Long id) {
        this.restauranteService.remover(id);
    }

    private void merge(Map<String, Object> dadosOrigem, Restaurante restauranteDestino) {
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
