package com.dan.esr.domain.services.usuario;

import com.dan.esr.domain.entities.Usuario;
import com.dan.esr.domain.exceptions.usuario.UsuarioNaoEncontradoException;
import com.dan.esr.domain.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioConsultaService {
    private final UsuarioRepository usuarioRepository;

    public Usuario buscarPor(Long id) {
        return usuarioRepository.com(id)
                .orElseThrow(() -> new UsuarioNaoEncontradoException(id));
    }

    public Usuario buscarPrimeiro() {
        return this.usuarioRepository.primeiro()
                .orElseThrow(UsuarioNaoEncontradoException::new);
    }

    public List<Usuario> buscarTodos() {
        List<Usuario> Usuarios = usuarioRepository.todos();
        if (Usuarios.isEmpty()) {
            throw new UsuarioNaoEncontradoException();
        }
        return Usuarios;
    }

    public Usuario buscarPrimeiroComNomeSemelhante(String nome) {
        return this.usuarioRepository.primeiroComNomeSemelhante(nome)
                .orElseThrow(() -> new UsuarioNaoEncontradoException(
                        "Nenhum usuário encontrado com o nome: %s.".formatted(nome)));
    }
}