package com.dan.esr.api.controllers;

import com.dan.esr.api.models.CozinhasXML;
import com.dan.esr.domain.entities.Cozinha;
import com.dan.esr.domain.repositories.CozinhaRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@RestController
@RequestMapping("/cozinhas")
public class CozinhaController {

    private CozinhaRepository cozinhaRepository;

    @ResponseStatus(HttpStatus.OK) //retorna 200
    @GetMapping
    public List<Cozinha> listar() {
        return cozinhaRepository.findAll();
    }

    @ResponseStatus(HttpStatus.OK) //retorna 200
    @GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
    public CozinhasXML listarXml() {
        return new CozinhasXML(cozinhaRepository.findAll());
    }

    //@ResponseStatus(HttpStatus.OK) //retorna 200
    @GetMapping("/{id}")
    public ResponseEntity<Cozinha> buscarPorId(@PathVariable Long id) {
        Cozinha cozinha = cozinhaRepository.findById(id).orElse(null);
        return (cozinha != null) ? ResponseEntity.ok(cozinha) : ResponseEntity.notFound().build();
        /*if (cozinha != null) {
            return ResponseEntity.status(HttpStatus.OK).body(cozinha);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }*/
    }

    //@ResponseStatus(HttpStatus.CREATED) //retorna 201
    @PostMapping
    public ResponseEntity<Cozinha> adicionar(@RequestBody Cozinha cozinha) {
        Cozinha registro = cozinhaRepository.save(cozinha);
        return ResponseEntity.status(HttpStatus.CREATED).body(registro);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cozinha> atualizar(@PathVariable Long id, @RequestBody Cozinha cozinha) {
        Cozinha registro = cozinhaRepository.findById(id).orElse(null);
        if (Objects.isNull(registro)) return ResponseEntity.notFound().build();
        BeanUtils.copyProperties(cozinha, registro, "id");
        registro = cozinhaRepository.saveAndFlush(registro);
        return ResponseEntity.ok(registro);
    }

    //@ResponseStatus(HttpStatus.NO_CONTENT) //retorna 204)
    @DeleteMapping("/{id}")
    public ResponseEntity<Cozinha> remover(@PathVariable Long id) {
        Cozinha registro = cozinhaRepository.findById(id).orElse(null);
        if (Objects.isNull(registro)) return ResponseEntity.notFound().build();

        try {
            cozinhaRepository.delete(registro);
            return ResponseEntity.noContent().build();
        } catch (DataIntegrityViolationException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(registro);
        }
    }

}
