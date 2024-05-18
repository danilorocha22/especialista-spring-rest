package com.dan.esr.domain.services.cozinha;

import com.dan.esr.domain.entities.Cozinha;
import com.dan.esr.domain.exceptions.CozinhaNaoEncontradaException;
import com.dan.esr.domain.repositories.CozinhaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.dan.esr.core.util.MessagesUtil.MSG_COZINHA_NAO_ENCONTRADA;
import static com.dan.esr.core.util.MessagesUtil.MSG_COZINHA_NAO_ENCONTRADA_COM_NOME;

@Service
@RequiredArgsConstructor
public class CozinhaConsultaService {
    private final CozinhaRepository cozinhaRepository;

    public Cozinha buscarPor(Long id) {
        try {
            return this.cozinhaRepository.buscarPor(id).orElse(null);

        } catch (EmptyResultDataAccessException ex) {
            throw new CozinhaNaoEncontradaException(id, ex.getCause());
        }
    }

    public Cozinha buscarPrimeira() {
        return this.cozinhaRepository.buscarPrimeira()
                .orElseThrow(() -> new CozinhaNaoEncontradaException(MSG_COZINHA_NAO_ENCONTRADA));
    }

    public Cozinha buscarPrimeiraComNomeSemelhante(String nome) {
        try {
            return this.cozinhaRepository.buscarPrimeiraComNomeContendo(nome)
                    .orElse(null);

        } catch (EmptyResultDataAccessException ex) {
            throw new CozinhaNaoEncontradaException(MSG_COZINHA_NAO_ENCONTRADA_COM_NOME
                    .formatted(nome), ex.getCause());
        }
    }

    public Cozinha buscarPorNomeIgual(String nome) {
        try {
            return this.cozinhaRepository.buscarPorNomeIgual(nome)
                    .orElse(null);

        } catch (EmptyResultDataAccessException ex) {
            throw new CozinhaNaoEncontradaException(MSG_COZINHA_NAO_ENCONTRADA_COM_NOME
                    .formatted(nome), ex.getCause());
        }
    }

    public List<Cozinha> buscarTodasComNomeSemelhante(String nome) {
        List<Cozinha> cozinhas = this.cozinhaRepository.buscarPorNomeSemelhante(nome);
        validar(cozinhas);
        return cozinhas;
    }

    public List<Cozinha> buscarTodos() {
        List<Cozinha> cozinhas = this.cozinhaRepository.buscarTodos();
        validar(cozinhas);
        return cozinhas;
    }

    public boolean existePorNomeIgual(String nome) {
        return this.cozinhaRepository.existsByNomeIgnoreCase(nome);
    }

    private void validar(List<Cozinha> cozinhas) {
        if (cozinhas.isEmpty()) {
            throw new CozinhaNaoEncontradaException(MSG_COZINHA_NAO_ENCONTRADA);
        }
    }
}