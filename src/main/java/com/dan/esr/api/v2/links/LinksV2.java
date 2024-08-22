package com.dan.esr.api.v2.links;

import com.dan.esr.api.v2.controllers.cidade.CidadeControllerV2;
import com.dan.esr.api.v2.controllers.cozinha.CozinhaPesquisaControllerV2;
import org.springframework.hateoas.Link;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public final class LinksV2 {

    private LinksV2() {
    }

    public static Link linkToCidades() {
        return linkTo(CidadeControllerV2.class).withSelfRel();
    }

    public static Link linkToCozinhas() {
        return linkTo(CozinhaPesquisaControllerV2.class).withSelfRel();
    }

    public static Link linkToCozinha(Long cozinhaId) {
        return linkTo(methodOn(CozinhaPesquisaControllerV2.class).cozinha(cozinhaId)).withSelfRel();
    }

}