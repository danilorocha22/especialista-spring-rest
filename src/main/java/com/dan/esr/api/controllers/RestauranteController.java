package com.dan.esr.api.controllers;

import com.dan.esr.domain.entities.Cozinha;
import com.dan.esr.domain.entities.Restaurante;
import com.dan.esr.domain.exceptions.EntidadeEmUsoException;
import com.dan.esr.domain.exceptions.EntidadeNaoEncontradaException;
import com.dan.esr.domain.repositories.RestauranteRepository;
import com.dan.esr.domain.services.CadastroRestauranteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

    private final RestauranteRepository restauranteRepository;
    private final CadastroRestauranteService restauranteService;

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        Optional<Restaurante> optionalRestaurante = restauranteRepository.findById(id);
        return (optionalRestaurante.isPresent()) ? ResponseEntity.ok(optionalRestaurante.get()) :
                ResponseEntity.status(HttpStatus.NOT_FOUND).body(String.format(
                        "Não existe restaurante cadastrado com ID %s", id));
    }

    @GetMapping
    public List<Restaurante> listar() {
        return restauranteRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<?> adicionar(@RequestBody Restaurante restaurante) {
        try {
            Restaurante registro = restauranteService.adicionarOuAtualizar(restaurante);
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
            return ResponseEntity.ok(restauranteService.adicionarOuAtualizar(restaurante));
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
            return ResponseEntity.ok(restauranteService.adicionarOuAtualizar(registro));

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
    public ResponseEntity<?> remover(@PathVariable Long id) {
        try {
            restauranteService.remover(id);
            return ResponseEntity.status(HttpStatus.OK).body(String.format(
                    "Restaurante de ID %s removido com sucesso", id));

        } catch (EntidadeEmUsoException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(String.format(
                    "Não foi possível remover o restaurante de ID %s, pois possui um relacionamento com outra entidade",
                    id));

        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(String.format(
                    "Não existe restaurante cadastrado com ID %s", id));
        }
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
