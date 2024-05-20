package com.dan.esr.domain.services;

import com.dan.esr.domain.entities.FormasPagamento;
import com.dan.esr.domain.exceptions.EntidadeEmUsoException;
import com.dan.esr.domain.exceptions.EntidadeNaoPersistidaException;
import com.dan.esr.domain.exceptions.formapagamento.FormaPagamentoNaoEncontradoException;
import com.dan.esr.domain.repositories.FormasPagamentoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FormasPagamentoService {
    private final FormasPagamentoRepository formasPagamentoRepository;

    public FormasPagamento buscarPor(Long id) {
        return formasPagamentoRepository.buscarPor(id)
                .orElseThrow(() -> new FormaPagamentoNaoEncontradoException(id));
    }

    public List<FormasPagamento> buscarTodos() {
        List<FormasPagamento> formasPagamentos = formasPagamentoRepository.todos();
        if (formasPagamentos.isEmpty()) {
            throw new FormaPagamentoNaoEncontradoException();
        }
        return formasPagamentos;
    }

    @Transactional
    public FormasPagamento salvarOuAtualizar(FormasPagamento formasPagamento) {
        return formasPagamentoRepository.salvarOuAtualizar(formasPagamento)
                .orElseThrow(() -> {
                    String descricao = formasPagamento.getDescricao();
                    if (formasPagamento.isNova()) {
                        return new EntidadeNaoPersistidaException(
                                "Não foi possível cadastrar a forma de pagamento: %s.".formatted(descricao));
                    } else {
                        return new EntidadeNaoPersistidaException(
                                "Não foi possível atualizar a forma de pagamento: %s.".formatted(descricao));
                    }
                });
    }

    @Transactional
    public void remover(Long id) {
        FormasPagamento formasPagamento = this.buscarPor(id);
        try {
            this.formasPagamentoRepository.remover(formasPagamento.getId());
        } catch (DataIntegrityViolationException ex) {
            throw new EntidadeEmUsoException("Forma de pagamento não pode ser excluída, pois está " +
                    "em uso por outra entidade");
        }
    }
}