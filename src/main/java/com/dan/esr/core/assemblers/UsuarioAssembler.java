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

import static com.dan.esr.api.helper.links.Links.linkToGrupos;
import static com.dan.esr.api.helper.links.Links.linkToUsuarios;

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
        return usuarioOutput
                .add(linkToGrupos(usuario.getGrupos()))
                .add(linkToUsuarios());
    }

    public UsuarioGruposOutput toModelUsuarioGrupos(Usuario usuario) {
        UsuarioOutput usuarioOutput = this.toModel(usuario);
        UsuarioGruposOutput usuarioGruposOutput = this.mapper.map(usuario, UsuarioGruposOutput.class);
        return usuarioGruposOutput.add(usuarioOutput.getLinks());
    }

    @NonNull
    @Override
    public CollectionModel<UsuarioOutput> toCollectionModel(@NonNull Iterable<? extends Usuario> entities) {
        return super.toCollectionModel(entities).add(linkToUsuarios());
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