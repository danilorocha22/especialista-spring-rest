package com.dan.esr.api.controllers.estado;

import com.dan.esr.api.models.input.estado.EstadoInput;
import com.dan.esr.api.models.output.estado.EstadoOutput;
import com.dan.esr.api.openapi.documentation.estado.EstadoDocumentation;
import com.dan.esr.core.assemblers.EstadoAssembler;
import com.dan.esr.domain.entities.Estado;
import com.dan.esr.domain.repositories.EstadoRepository;
import com.dan.esr.domain.services.estado.EstadoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping("/estados")
public class EstadoController implements EstadoDocumentation {
    private final EstadoService estadoService;
    private final EstadoRepository estadoRepository;
    private final EstadoAssembler estadoAssembler;

    @Override
    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public List<EstadoOutput> estados() {
        return this.estadoAssembler.toModelList(
                this.estadoRepository.findAll()
        );
    }

    @Override
    @GetMapping(path = "/{id}", produces = APPLICATION_JSON_VALUE)
    public EstadoOutput estado(@PathVariable Long id) {
        return this.estadoAssembler.toModel(
                this.estadoService.buscarPorId(id)
        );
    }

    @Override
    @PostMapping(produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public EstadoOutput novoEstado(@RequestBody @Valid EstadoInput estadoInput) {
        Estado estado = this.estadoAssembler.toDomain(estadoInput);
        return this.estadoAssembler.toModel(
                this.estadoService.salvarOuAtualizar(estado)
        );
    }

    @Override
    @PutMapping(path = "/{id}", produces = APPLICATION_JSON_VALUE)
    public EstadoOutput atualizarEstado(@PathVariable Long id, @Valid @RequestBody EstadoInput estadoInput) {
        Estado estado = this.estadoService.buscarPorId(id);
        BeanUtils.copyProperties(estadoInput, estado, "id");
        return this.estadoAssembler.toModel(
                this.estadoService.salvarOuAtualizar(estado)
        );
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluirEstado(@PathVariable Long id) {
        this.estadoService.remover(id);
    }

    /*@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    private List<Estado> listarEstadosApi() {
        List<Estado> estados = estadoService.estados();
        System.out.println("Quantidade Estados "+ estados.size());

        return estados;
    }*/
}