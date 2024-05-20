package com.dan.esr.core.assemblers;

import com.dan.esr.api.models.input.usuario.UsuarioAtualizadoInput;
import com.dan.esr.api.models.input.usuario.UsuarioInput;
import com.dan.esr.api.models.input.usuario.UsuarioSenhaInput;
import com.dan.esr.api.models.output.UsuarioOutput;
import com.dan.esr.domain.entities.Usuario;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UsuarioAssembler {
    private final ModelMapper mapper;

    public UsuarioOutput toModel(Usuario usuario) {
        return mapper.map(usuario, UsuarioOutput.class);
    }

    public List<UsuarioOutput> toCollectionModel(List<Usuario> usuarios) {
        return usuarios.stream()
                .map(this::toModel)
                .toList();
    }

    public Usuario toDomain(UsuarioInput usuarioInput) {
        return mapper.map(usuarioInput, Usuario.class);
    }
    public Usuario toDomain(UsuarioSenhaInput usuarioSenhaInput) {
        return mapper.map(usuarioSenhaInput, Usuario.class);
    }

    public void copyToDomain(UsuarioAtualizadoInput usuarioAtualizadoInput, Usuario usuario) {

        mapper.map(usuarioAtualizadoInput, usuario);
    }

    public Usuario toDomain(UsuarioAtualizadoInput usuarioAtualizado) {
        return mapper.map(usuarioAtualizado, Usuario.class);
    }
}