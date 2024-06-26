package com.dan.esr.api.controllers.restaurante;

import com.dan.esr.api.models.output.restaurante.RestauranteResponsaveisOutput;
import com.dan.esr.api.openapi.documentation.restaurante.RestauranteResponsavelDocumentation;
import com.dan.esr.core.assemblers.RestauranteModelAssembler;
import com.dan.esr.domain.entities.Restaurante;
import com.dan.esr.domain.services.restaurante.RestauranteResponsavelService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/restaurantes/{restauranteId}/responsaveis", produces = APPLICATION_JSON_VALUE)
public class RestauranteResponsavelController implements RestauranteResponsavelDocumentation {
    private final RestauranteResponsavelService responsavelService;
    private final RestauranteModelAssembler restauranteModelAssembler;

    @Override
    @GetMapping
    public RestauranteResponsaveisOutput buscarResponsaveis(@PathVariable Long restauranteId) {
        Restaurante restaurante = this.responsavelService.buscarComResponsaveis(restauranteId);
        return this.restauranteModelAssembler.toModelComResponsaveis(restaurante);
    }

    @Override
    @PutMapping("/{usuarioId}")
    public RestauranteResponsaveisOutput adicionarResponsavel(
            @PathVariable Long restauranteId,
            @PathVariable Long usuarioId
    ) {
        Restaurante restaurante = this.responsavelService.adicionarResponsavel(restauranteId, usuarioId);
        return this.restauranteModelAssembler.toModelComResponsaveis(restaurante);
    }

    @Override
    @DeleteMapping("/{usuarioId}")
    public RestauranteResponsaveisOutput removerResponsavel(
            @PathVariable Long restauranteId,
            @PathVariable Long usuarioId
    ) {
        Restaurante restaurante = this.responsavelService.removerResponsavel(restauranteId, usuarioId);
        return this.restauranteModelAssembler.toModelComResponsaveis(restaurante);
    }
}