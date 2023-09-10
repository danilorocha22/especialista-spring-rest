package com.dan.esr.api.controllers;

import com.dan.esr.domain.entities.Restaurante;
import com.dan.esr.domain.repositories.RestauranteRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

    private RestauranteRepository restauranteRepository;

    @GetMapping("/{id}")
    public Optional<Restaurante> buscarPorId(@PathVariable Long id) {
        if (restauranteRepository.findById(id).isPresent()) {
            return Optional.of(restauranteRepository.findById(id).get());
        }
        return Optional.empty();
    }

    @GetMapping
    @ResponseBody
    public List<Restaurante> listar() {
        return restauranteRepository.findAll() ;
    }

    @PostMapping
    public Restaurante salvarOuAtualizar(@RequestBody Restaurante restaurante) {
     return restauranteRepository.saveAndFlush(restaurante);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        restauranteRepository.deleteById(id);
    }

}
