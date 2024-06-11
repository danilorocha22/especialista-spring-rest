package com.dan.esr.domain.services.grupo;

import com.dan.esr.domain.entities.Grupo;
import com.dan.esr.domain.entities.Permissao;
import com.dan.esr.domain.exceptions.EntidadeEmUsoException;
import com.dan.esr.domain.exceptions.EntidadeNaoPersistidaException;
import com.dan.esr.domain.exceptions.grupo.GrupoNaoEncontradoException;
import com.dan.esr.domain.repositories.GrupoRepository;
import com.dan.esr.domain.services.permissao.PermissaoService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GrupoService {
    private final GrupoRepository grupoRepository;
    private final PermissaoService permissaoService;

    public Grupo buscarPor(Long id) {
        return grupoRepository.com(id)
                .orElseThrow(() -> new GrupoNaoEncontradoException(id));
    }

    public List<Grupo> buscarTodos() {
        List<Grupo> grupos = grupoRepository.todos();
        if (grupos.isEmpty()) {
            throw new GrupoNaoEncontradoException();
        }
        return grupos;
    }

    @Transactional
    public Grupo salvarOuAtualizar(Grupo grupo) {
        return this.grupoRepository.salvarOuAtualizar(grupo)
                .orElseThrow(() -> {
                    if (grupo.isNova()) {
                        return new EntidadeNaoPersistidaException("Não foi possível cadastrar o grupo: %s."
                                .formatted(grupo.getNome()));
                    } else {
                        return new EntidadeNaoPersistidaException("Não foi possível atualizar o grupo: %s."
                                .formatted(grupo.getNome()));
                    }
                });
    }

    @Transactional
    public void remover(Long id) {
        Grupo grupo = this.buscarPor(id);
        try {
            this.grupoRepository.remover(grupo.getId());
        } catch (DataIntegrityViolationException ex) {
            throw new EntidadeEmUsoException(
                    "Não foi possível excluir o grupo '%s', pois está em uso por outra entidade.");
        }
    }

    @Transactional
    public Grupo adicionarPermissao(Long grupoId, Long permissaoId) {
        Grupo grupo = this.buscarPor(grupoId);
        Permissao permissao = this.permissaoService.buscarPor(permissaoId);
        grupo.adicionar(permissao);
        return grupo;
    }

    @Transactional
    public Grupo removerPermissao(Long grupoId, Long permissaoId) {
        Grupo grupo = this.buscarPor(grupoId);
        Permissao permissao = this.permissaoService.buscarPor(permissaoId);
        grupo.remover(permissao);
        return grupo;
    }
}