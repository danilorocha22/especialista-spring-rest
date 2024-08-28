package com.dan.esr.api.v1.controllers.estado;

import com.dan.esr.api.v1.models.input.estado.EstadoInput;
import com.dan.esr.api.v1.models.output.estado.EstadoOutput;
import com.dan.esr.api.v1.openapi.documentation.estado.EstadoDocumentation;
import com.dan.esr.api.v1.assemblers.EstadoAssembler;
import com.dan.esr.domain.entities.Estado;
import com.dan.esr.domain.repositories.EstadoRepository;
import com.dan.esr.domain.services.estado.EstadoService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/estados")
public class EstadoController implements EstadoDocumentation {
    private final EstadoService estadoService;
    private final EstadoRepository estadoRepository;
    private final EstadoAssembler estadoAssembler;

    @Override
    @GetMapping(path = "/{id}", produces = APPLICATION_JSON_VALUE)
    public EntityModel<EstadoOutput> estado(@PathVariable Long id) {
        Estado estado = this.estadoService.buscarPor(id);
        return EntityModel.of(
                this.estadoAssembler.toModel(estado)
        );
    }

    @Override
    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public CollectionModel<EstadoOutput> estados() {
        return this.estadoAssembler.toCollectionModel(
                this.estadoRepository.findAll()
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
        Estado estado = this.estadoService.buscarPor(id);
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
}