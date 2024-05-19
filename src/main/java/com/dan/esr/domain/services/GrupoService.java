package com.dan.esr.domain.services;

import com.dan.esr.domain.entities.Grupo;
import com.dan.esr.domain.exceptions.EntidadeEmUsoException;
import com.dan.esr.domain.exceptions.EntidadeNaoEncontradaException;
import com.dan.esr.domain.exceptions.EntidadeNaoPersistidaException;
import com.dan.esr.domain.repositories.GrupoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GrupoService {
    private final GrupoRepository grupoRepository;

    // todos os métodos de CRUD
    public Grupo buscarPor(Long id) {
        return grupoRepository.buscarPor(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException(
                        "Nenhum grupo encontrado com ID %s".formatted(id)));
    }

    public List<Grupo> buscarTodos() {
        List<Grupo> grupos = grupoRepository.buscarTodos();
        if (grupos.isEmpty()) {
            throw new EntidadeNaoEncontradaException("Nenhuma grupo cadastrado.");
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
}