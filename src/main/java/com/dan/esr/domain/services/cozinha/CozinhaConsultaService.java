package com.dan.esr.domain.services.cozinha;

import com.dan.esr.domain.entities.Cozinha;
import com.dan.esr.domain.exceptions.EntidadeNaoEncontradaException;
import com.dan.esr.domain.exceptions.NegocioException;
import com.dan.esr.domain.exceptions.cozinha.CozinhaNaoEncontradaException;
import com.dan.esr.domain.repositories.CozinhaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.dan.esr.core.util.MensagensUtil.MSG_COZINHA_NAO_ENCONTRADA_COM_NOME;
import static com.dan.esr.core.util.ValidacaoUtil.validarSeVazio;

@Service
@RequiredArgsConstructor
public class CozinhaConsultaService {
    private final CozinhaRepository cozinhaRepository;

    public Cozinha buscarPor(Long id) {
        return this.cozinhaRepository.com(id)
                .orElseThrow(() -> new CozinhaNaoEncontradaException(id));
    }

    public Cozinha buscarPrimeira() {
        return this.cozinhaRepository.primeiro()
                .orElseThrow(CozinhaNaoEncontradaException::new);
    }

    public Cozinha buscarPrimeiraComNomeSemelhante(String nome) {
        return this.cozinhaRepository.primeiroComNomeSemelhante(nome)
                .orElseThrow(() -> new NegocioException(
                        MSG_COZINHA_NAO_ENCONTRADA_COM_NOME.formatted(nome)));

    }

    public Cozinha buscarPorNomeIgual(String nome) {
        return this.cozinhaRepository.comNomeIgual(nome)
                .orElseThrow(() -> new NegocioException(
                        MSG_COZINHA_NAO_ENCONTRADA_COM_NOME.formatted(nome)));

    }

    public List<Cozinha> buscarTodasComNomeSemelhante(String nome) {
        try {
            List<Cozinha> cozinhas = this.cozinhaRepository.comNomeSemelhante(nome);
            validarSeVazio(cozinhas);
            return cozinhas;
        } catch (EntidadeNaoEncontradaException ex) {
            throw new CozinhaNaoEncontradaException();
        }
    }

    public Page<Cozinha> todosPaginados(Pageable pageable) {
        try {
            Page<Cozinha> cozinhaPage = this.cozinhaRepository.findAll(pageable);
            validarSeVazio(cozinhaPage.getContent());
            return cozinhaPage;
        } catch (EntidadeNaoEncontradaException ex) {
            throw new NegocioException("Nenhuma cozinha encontrada");
        }
    }

    public boolean existePorNomeIgual(String nome) {
        return this.cozinhaRepository.existsByNomeIgnoreCase(nome);
    }
}