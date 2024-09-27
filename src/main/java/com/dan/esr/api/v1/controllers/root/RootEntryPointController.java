package com.dan.esr.api.v1.controllers.root;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import static com.dan.esr.api.v1.links.Links.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@ApiIgnore
@RestController
@RequestMapping(path = "/v1", produces = APPLICATION_JSON_VALUE)
public final class RootEntryPointController {

    @SuppressWarnings("all")
    @GetMapping
    public RootEntryPointModel rootEntryPoint() {
        var rootEntryPointModel = new RootEntryPointModel();
        rootEntryPointModel.add(linkToCozinhas());
        rootEntryPointModel.add(linkToPedidos());
        rootEntryPointModel.add(linkToRestaurantes());
        rootEntryPointModel.add(linkToGrupos());
        rootEntryPointModel.add(linkToUsuarios());
        //rootEntryPointModel.add(linkToPermissoes());
        //rootEntryPointModel.add(linkToFormasPagamento());
        rootEntryPointModel.add(linkToEstados());
        rootEntryPointModel.add(linkToCidades());
        rootEntryPointModel.add(linkToEstatisticas());
        return rootEntryPointModel;
    }

    private static class RootEntryPointModel extends RepresentationModel<RootEntryPointModel> {
    }
}