package com.dan.esr.core.assemblers;

import com.dan.esr.api.controllers.usuario.UsuarioPesquisaController;
import com.dan.esr.api.models.input.usuario.UsuarioAtualizadoInput;
import com.dan.esr.api.models.input.usuario.UsuarioInput;
import com.dan.esr.api.models.input.usuario.UsuarioSenhaInput;
import com.dan.esr.api.models.output.usuario.UsuarioGruposOutput;
import com.dan.esr.api.models.output.usuario.UsuarioOutput;
import com.dan.esr.domain.entities.Usuario;
import lombok.NonNull;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UsuarioAssembler extends RepresentationModelAssemblerSupport<Usuario, UsuarioOutput> {
    private final ModelMapper mapper;

    public UsuarioAssembler(ModelMapper mapper) {
        super(UsuarioPesquisaController.class, UsuarioOutput.class);
        this.mapper = mapper;
    }

    @NonNull
    @Override
    public UsuarioOutput toModel(@NonNull Usuario usuario) {
        UsuarioOutput usuarioOutput = createModelWithId(usuario.getId(), usuario);
        this.mapper.map(usuario, usuarioOutput);
        usuarioOutput.add(linkTo(methodOn(UsuarioPesquisaController.class).usuarioGrupos(usuario.getId()))
                .withSelfRel());
        return usuarioOutput;
    }

    public UsuarioGruposOutput toModelUsuarioGrupos(Usuario usuario) {
        UsuarioGruposOutput usuarioGruposOutput = this.mapper.map(usuario, UsuarioGruposOutput.class);
        usuarioGruposOutput.add(linkTo(methodOn(UsuarioPesquisaController.class).usuario(usuario.getId()))
                .withSelfRel());
        return usuarioGruposOutput;
    }

    @NonNull
    @Override
    public CollectionModel<UsuarioOutput> toCollectionModel(@NonNull Iterable<? extends Usuario> entities) {
        return super.toCollectionModel(entities)
                .add(linkTo(methodOn(UsuarioPesquisaController.class).usuarios()).withSelfRel());
    }

    public Usuario toDomain(UsuarioInput usuarioInput) {
        return mapper.map(usuarioInput, Usuario.class);
    }

    public Usuario toDomain(UsuarioSenhaInput usuarioSenhaInput) {
        return mapper.map(usuarioSenhaInput, Usuario.class);
    }

    public Usuario toDomain(UsuarioAtualizadoInput usuarioAtualizado) {
        return mapper.map(usuarioAtualizado, Usuario.class);
    }

    public void copyToDomain(UsuarioAtualizadoInput usuarioAtualizadoInput, Usuario usuario) {
        mapper.map(usuarioAtualizadoInput, usuario);
    }
}