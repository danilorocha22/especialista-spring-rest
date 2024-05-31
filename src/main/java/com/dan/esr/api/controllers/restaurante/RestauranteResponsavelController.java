package com.dan.esr.api.controllers.restaurante;

import com.dan.esr.api.models.output.restaurante.RestauranteResponsaveisOutput;
import com.dan.esr.core.assemblers.RestauranteModelAssembler;
import com.dan.esr.domain.entities.Restaurante;
import com.dan.esr.domain.services.restaurante.RestauranteResponsavelService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/restaurantes/{restauranteId}/responsaveis")
public class RestauranteResponsavelController {
    private final RestauranteResponsavelService responsavelService;
    private final RestauranteModelAssembler restauranteModelAssembler;

    @GetMapping
    public RestauranteResponsaveisOutput buscarResponsaveis(@PathVariable Long restauranteId) {
        Restaurante restaurante = this.responsavelService.buscarComResponsaveis(restauranteId);
        return this.restauranteModelAssembler.toModelComResponsaveis(restaurante);
    }

    @PutMapping("/{usuarioId}")
    public RestauranteResponsaveisOutput adicionarResponsavel(
            @PathVariable Long restauranteId,
            @PathVariable Long usuarioId
    ) {
        Restaurante restaurante = this.responsavelService.adicionarResponsavel(restauranteId, usuarioId);
        return this.restauranteModelAssembler.toModelComResponsaveis(restaurante);
    }

    @DeleteMapping("/{usuarioId}")
    public RestauranteResponsaveisOutput removerResponsavel(
            @PathVariable Long restauranteId,
            @PathVariable Long usuarioId
    ) {
        Restaurante restaurante = this.responsavelService.removerResponsavel(restauranteId, usuarioId);
        return this.restauranteModelAssembler.toModelComResponsaveis(restaurante);
    }
}