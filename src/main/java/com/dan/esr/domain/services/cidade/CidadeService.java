package com.dan.esr.domain.services.cidade;

import com.dan.esr.domain.entities.Cidade;
import com.dan.esr.domain.entities.Estado;
import com.dan.esr.domain.exceptions.EntidadeEmUsoException;
import com.dan.esr.domain.exceptions.NegocioException;
import com.dan.esr.domain.exceptions.cidade.CidadeNaoEncontradaException;
import com.dan.esr.domain.exceptions.cidade.CidadeNaoPersistidaException;
import com.dan.esr.domain.exceptions.estado.EstadoNaoEncontradoException;
import com.dan.esr.domain.repositories.CidadeRepository;
import com.dan.esr.domain.services.estado.EstadoService;
import lombok.RequiredArgsConstructor;
import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.dan.esr.core.util.MensagensUtil.*;

@Service
@RequiredArgsConstructor
public class CidadeService {
    private static final Logger logger = LoggerFactory.getLogger(CidadeService.class);
    private final CidadeRepository cidadeRepository;
    private final EstadoService estadoService;

    @Transactional
    public Cidade salvarOuAtualizar(Cidade cidade) {
        try {
            return save(cidade);

        } catch (EstadoNaoEncontradoException ex) {
            logger.error("salvarOuAtualizar(cidade) -> Erro: {}", ex.getLocalizedMessage(), ex.getCause());
            throw new NegocioException(ex.getMessage());

        } catch (HibernateException ex) {
            logger.error("salvarOuAtualizar(cidade) -> Erro: {}", ex.getLocalizedMessage(), ex.getCause());
            String nome = cidade.getNome();
            if (cidade.isNova()) {
                throw new CidadeNaoPersistidaException(MSG_CIDADE_NAO_SALVA.formatted(nome), ex.getCause());
            } else {
                throw new CidadeNaoPersistidaException(MSG_CIDADE_NAO_ATUALIZADA.formatted(nome), ex.getCause());
            }
        }
    }

    private Cidade save(Cidade cidade) {
        configurarCidade(cidade);
        return this.cidadeRepository.salvarOuAtualizar(cidade)
                .orElseThrow();
    }

    private void configurarCidade(Cidade cidade) {
        Long estadoId = cidade.getEstado().getId();
        Estado estado = this.estadoService.buscarPor(estadoId);
        cidade.setEstado(estado);
    }

    @Transactional
    public void remover(Long id) {
        try {
            this.cidadeRepository.remover(id);
        } catch (DataIntegrityViolationException ex) {
            throw new EntidadeEmUsoException(MSG_CIDADE_EM_USO.formatted(id), ex.getCause());
        }
    }

    public Cidade buscarPor(Long id) {
        try {
            return this.cidadeRepository.por(id)
                    .orElse(null);

        } catch (EmptyResultDataAccessException | DataIntegrityViolationException ex) {
            throw new CidadeNaoEncontradaException(id, ex.getCause());
        }
    }

    public List<Cidade> buscarTodos() {
        List<Cidade> cidades = this.cidadeRepository.todos();
        validar(cidades);
        return cidades;
    }

    private void validar(List<Cidade> cidades) {
        if (cidades.isEmpty()) {
            throw new CidadeNaoEncontradaException(MSG_CIDADE_NAO_ENCONTRADA);
        }
    }
}
