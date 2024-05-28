package com.dan.esr.domain.services.permissao;

import com.dan.esr.domain.entities.Permissao;
import com.dan.esr.domain.exceptions.EntidadeEmUsoException;
import com.dan.esr.domain.exceptions.EntidadeNaoPersistidaException;
import com.dan.esr.domain.exceptions.permissao.PermissaoNaoEncontradoException;
import com.dan.esr.domain.repositories.PermissaoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PermissaoService {
    private final PermissaoRepository permissaoRepository;

    public Permissao buscarPor(Long id) {
        return permissaoRepository.buscarPor(id)
                .orElseThrow(() -> new PermissaoNaoEncontradoException(id));
    }

    public List<Permissao> buscarTodos() {
        List<Permissao> permissoes = permissaoRepository.todos();
        if (permissoes.isEmpty()) {
            throw new PermissaoNaoEncontradoException();
        }
        return permissoes;
    }

    @Transactional
    public Permissao salvarOuAtualizar(Permissao permissao) {
        return this.permissaoRepository.salvarOuAtualizar(permissao)
                .orElseThrow(() -> {
                    if (permissao.isNova()) {
                        return new EntidadeNaoPersistidaException("Não foi possível cadastrar a permissão: %s."
                                .formatted(permissao.getNome()));
                    } else {
                        return new EntidadeNaoPersistidaException("Não foi possível atualizar a permissão: %s."
                                .formatted(permissao.getNome()));
                    }
                });
    }

    @Transactional
    public void remover(Long id) {
        Permissao permissao = this.buscarPor(id);
        try {
            this.permissaoRepository.remover(permissao.getId());
        } catch (DataIntegrityViolationException ex) {
            throw new EntidadeEmUsoException(
                    "Não foi possível excluir a permissão '%s', pois está em uso por outra entidade.");
        }
    }
}
