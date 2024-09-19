package com.dan.esr.domain.services.usuario;

import com.dan.esr.domain.entities.Grupo;
import com.dan.esr.domain.entities.Usuario;
import com.dan.esr.domain.exceptions.EntidadeEmUsoException;
import com.dan.esr.domain.exceptions.EntidadeNaoPersistidaException;
import com.dan.esr.domain.exceptions.NegocioException;
import com.dan.esr.domain.exceptions.usuario.UsuarioNaoEncontradoException;
import com.dan.esr.domain.repositories.UsuarioRepository;
import com.dan.esr.domain.services.grupo.GrupoService;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioCadastroService {
    private final UsuarioConsultaService usuarioConsulta;
    private final UsuarioRepository usuarioRepository;
    private final GrupoService grupoService;
    private final CodificacaoSenhaService codificacaoSenhaService;

    @Transactional
    public Usuario salvarOuAtualizar(Usuario usuario) {
        if (usuario.isNovo()) {
            this.validarUsuarioComEmailJaCadastrado(usuario);
            usuario.setSenha(this.codificacaoSenhaService.criptografar(usuario.getSenha()));
        }
        return this.usuarioRepository.salvarOuAtualizar(usuario)
                .orElseThrow(() -> {
                    String nome = usuario.getNome();
                    if (usuario.isNovo()) {
                        return new EntidadeNaoPersistidaException(
                                "Não foi possível cadastrar o usuario: %s.".formatted(nome));
                    } else {
                        return new EntidadeNaoPersistidaException(
                                "Não foi possível atualizar o usuario: %s.".formatted(nome));
                    }
                });
    }

    @Transactional
    public void remover(Long id) {
        try {
            this.usuarioRepository.remover(id)
                    .orElseThrow(() -> new UsuarioNaoEncontradoException(id));

        } catch (DataIntegrityViolationException ex) {
            throw new EntidadeEmUsoException(
                    "Não foi possível excluir o usuário '%s', pois está em uso por outra entidade.");
        }
    }

    @Transactional
    public void atualizarSenha(Usuario usuarioAtualizado) {
        Usuario usuarioExistente = this.usuarioConsulta.buscarPor(usuarioAtualizado.getId());
        if (this.codificacaoSenhaService.diferente(usuarioAtualizado.getSenha(), usuarioExistente.getSenha())) {
            throw new NegocioException("A senha atual informada não confere.");
        }
        usuarioExistente.setSenha(this.codificacaoSenhaService.criptografar(usuarioAtualizado.getNovaSenha()));
    }

    public void validarUsuarioComEmailJaCadastrado(Usuario usuario) {
        this.usuarioRepository.buscarPorEmail(usuario.getEmail())
                .ifPresent(usuarioExistente -> {
                    if (usuarioExistente.isDiferente(usuario))  {
                        throw new NegocioException("Já existe usuário cadastrado com o e-mail: %s."
                                .formatted(usuario.getEmail()));
                    }
                });
    }

    @Transactional
    public void adicionarNoGrupo(Long usuarioId, Long grupoId) {
        Usuario usuario = this.usuarioConsulta.buscarPor(usuarioId);
        Grupo grupo = this.grupoService.buscarPor(grupoId);
        usuario.adicionar(grupo);
    }

    @Transactional
    public void removerDoGrupo(Long usuarioId, Long grupoId) {
        Usuario usuario = this.usuarioConsulta.buscarPor(usuarioId);
        Grupo grupo = this.grupoService.buscarPor(grupoId);
        usuario.remover(grupo);
    }
}