package com.dan.esr.api.controllers;

import com.dan.esr.domain.entities.Cidade;
import com.dan.esr.domain.entities.Estado;
import com.dan.esr.domain.services.ListaEstadoService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/cidades")
public class CidadeController {

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    private List<Cidade> listar() {
        /*List<Estado> estados = estadoService.estados();
        System.out.println("Quantidade Estados "+ estados.size());
        return estados;*/
        return null;
    }

}
