package com.dan.esr.domain.services;

import com.dan.esr.domain.entities.Cozinha;
import com.dan.esr.domain.exceptions.CozinhaNaoEncontradaException;
import com.dan.esr.domain.exceptions.CozinhaNaoPersistidaException;
import com.dan.esr.domain.exceptions.EntidadeEmUsoException;
import com.dan.esr.domain.repositories.CozinhaRepository;
import lombok.AllArgsConstructor;
import org.hibernate.HibernateException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.dan.esr.core.util.MessagesUtil.*;
import static org.springframework.beans.BeanUtils.copyProperties;

@AllArgsConstructor
@Service
public class CozinhaService {
    private CozinhaRepository cozinhaRepository;

    @Transactional
    public Cozinha salvarOuAtualizar(Cozinha cozinha) {
        try {
            return this.cozinhaRepository.salvarOuAtualizar(cozinha).orElseThrow();

        } catch (HibernateException ex) {
            if (cozinha.isNova()) {
                throw new CozinhaNaoPersistidaException(MSG_COZINHA_NAO_SALVA, ex.getCause());
            } else {
                throw new CozinhaNaoPersistidaException(MSG_COZINHA_NAO_ATUALIZADA, ex.getCause());
            }
        }
    }

    @Transactional
    public void remover(Long id) {
        Cozinha cozinha = this.buscarPorId(id);

        try {
            this.cozinhaRepository.remover(cozinha.getId());
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(MSG_COZINHA_EM_USO.formatted(id));
        }
    }

    public Cozinha buscarPorId(Long id) {
        try {
            return this.cozinhaRepository.buscarPorId(id).orElse(null);

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

    public boolean existePorNome(String nome) {
        return this.cozinhaRepository.existeRegistroCom(nome);
    }

    private void validar(List<Cozinha> cozinhas) {
        if (cozinhas.isEmpty()) {
            throw new CozinhaNaoEncontradaException(MSG_COZINHA_NAO_ENCONTRADA);
        }
    }
}