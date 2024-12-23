package com.dan.esr.domain.exceptions.formapagamento;

import com.dan.esr.domain.exceptions.EntidadeNaoEncontradaException;

public class FormaPagamentoNaoEncontradoException extends EntidadeNaoEncontradaException {

    public FormaPagamentoNaoEncontradoException(String message) {
        this(message, null);
    }

    public FormaPagamentoNaoEncontradoException(Long id) {
        this(id, null);
    }

    public FormaPagamentoNaoEncontradoException(Long id, Throwable cause) {
        this("Nenhuma forma de pagamento encontrada com o ID %s".formatted(id), cause);
    }

    public FormaPagamentoNaoEncontradoException(String message, Throwable causa) {
        super(message, causa);
    }

    public FormaPagamentoNaoEncontradoException() {
        this("Nenhuma forma de pagamento encontrada.");
    }
}
