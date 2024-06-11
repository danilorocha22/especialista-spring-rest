package com.dan.esr.domain.services.formaspagamento;

import com.dan.esr.domain.entities.FormaPagamento;
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
public class FormaPagamentoService {
    private final FormasPagamentoRepository formasPagamentoRepository;

    public FormaPagamento buscarPor(Long id) {
        return formasPagamentoRepository.com(id)
                .orElseThrow(() -> new FormaPagamentoNaoEncontradoException(id));
    }

    public List<FormaPagamento> buscarTodos() {
        List<FormaPagamento> formaPagamentos = formasPagamentoRepository.todos();
        if (formaPagamentos.isEmpty()) {
            throw new FormaPagamentoNaoEncontradoException();
        }
        return formaPagamentos;
    }

    @Transactional
    public FormaPagamento salvarOuAtualizar(FormaPagamento formaPagamento) {
        return formasPagamentoRepository.salvarOuAtualizar(formaPagamento)
                .orElseThrow(() -> {
                    String descricao = formaPagamento.getNome();
                    if (formaPagamento.isNova()) {
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
        FormaPagamento formaPagamento = this.buscarPor(id);
        try {
            this.formasPagamentoRepository.remover(formaPagamento.getId());
        } catch (DataIntegrityViolationException ex) {
            throw new EntidadeEmUsoException("Forma de pagamento não pode ser excluída, pois está " +
                    "em uso por outra entidade");
        }
    }
}