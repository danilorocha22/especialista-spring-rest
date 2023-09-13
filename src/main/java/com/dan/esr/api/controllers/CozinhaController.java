package com.dan.esr.api.controllers;

import com.dan.esr.domain.entities.Cozinha;
import com.dan.esr.domain.repositories.CozinhaRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/cozinhas")
public class CozinhaController {

    private CozinhaRepository cozinhaRepository;

    @GetMapping("/{id}")
    public Optional<Cozinha> buscarPorId(@PathVariable Long id) {
        if (cozinhaRepository.findById(id).isPresent()) {
            return Optional.of(cozinhaRepository.findById(id).get());
        }
        return Optional.empty();
    }

    @GetMapping
    public List<Cozinha> listar() {
        return cozinhaRepository.findAll() ;
    }

    @PostMapping
    public Cozinha salvarOuAtualizar(@RequestBody Cozinha cozinha) {
     return cozinhaRepository.saveAndFlush(cozinha);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        cozinhaRepository.deleteById(id);
    }

}
