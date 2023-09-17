package com.dan.esr.api.controllers;

import com.dan.esr.domain.entities.Estado;
import com.dan.esr.domain.services.EstadoService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/estados")
public class EstadoController {

    private EstadoService estadoService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    private List<Estado> listar() {
        System.out.println(this.estadoService.estados());
        return estadoService.estados();
    }

}
