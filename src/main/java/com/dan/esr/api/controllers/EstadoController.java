package com.dan.esr.api.controllers;

import com.dan.esr.domain.entities.Estado;
import com.dan.esr.domain.repositories.EstadoRepository;
import com.dan.esr.domain.services.CadastroEstadoService;
import com.dan.esr.domain.services.ListaEstadoService;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.dan.esr.domain.util.ValidarCamposObrigatoriosUtil.validarCampoObrigatorio;

@AllArgsConstructor
@RestController
@RequestMapping("/estados")
public class EstadoController {

    //private ListaEstadoService estadoService;
    private CadastroEstadoService cadastroEstado;
    private EstadoRepository estadoRepo;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Estado> listar() {
        return this.estadoRepo.findAll();
    }

    @GetMapping("/{id}")
    public Estado buscarPorId(@PathVariable Long id) {
        return this.cadastroEstado.buscarEstadoPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Estado salvar(@RequestBody Estado estado) {
        return this.cadastroEstado.salvar(estado);
    }

    public Estado atualizar(@PathVariable Long id, @RequestBody Estado estado) {
        validarCampoObrigatorio(estado, "Estado");
        Estado estadoRegistro = this.cadastroEstado.buscarEstadoPorId(id);
        BeanUtils.copyProperties(estado, estadoRegistro, "id");

        return this.cadastroEstado.salvar(estadoRegistro);
    }

    /*@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    private List<Estado> listarEstadosApi() {
        List<Estado> estados = estadoService.estados();
        System.out.println("Quantidade Estados "+ estados.size());

        return estados;
    }*/

}
