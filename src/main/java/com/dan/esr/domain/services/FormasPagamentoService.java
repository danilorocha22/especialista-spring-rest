package com.dan.esr.domain.services;

import com.dan.esr.domain.entities.FormasPagamento;
import com.dan.esr.domain.exceptions.EntidadeEmUsoException;
import com.dan.esr.domain.exceptions.EntidadeNaoPersistidaException;
import com.dan.esr.domain.repositories.FormasPagamentoRepository;
import jakarta.persistence.PersistenceException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FormasPagamentoService {
    private final FormasPagamentoRepository formasPagamentoRepository;

    // todos os métodos de CRUD
    public FormasPagamento buscarPor(Long id) {
        return formasPagamentoRepository.buscarPor(id)
                .orElseThrow(() -> new EntidadeNaoPersistidaException(
                        "Forma de pagamento não encontrada com ID %s".formatted(id)));
    }

    public List<FormasPagamento> buscarTodos() {
        List<FormasPagamento> formasPagamentos = formasPagamentoRepository.buscarTodos();
        if (formasPagamentos.isEmpty()) {
            throw new EntidadeNaoPersistidaException("Nenhuma forma de pagamento foi encontrada");
        }
        return formasPagamentos;
    }

    @Transactional
    public FormasPagamento salvarOuAtualizar(FormasPagamento formasPagamento) {
        try {
            return formasPagamentoRepository.salvarOuAtualizar(formasPagamento)
                    .orElseThrow(() -> new EntidadeNaoPersistidaException("Não foi possível cadastrar a forma de pagamento"));

        } catch (PersistenceException e) {
            if (formasPagamento.isNova()) {
                throw new EntidadeNaoPersistidaException("Não foi possível cadastrar a forma de pagamento");
            } else {
                throw new EntidadeNaoPersistidaException("Não foi possível atualizar a forma de pagamento");
            }
        }
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