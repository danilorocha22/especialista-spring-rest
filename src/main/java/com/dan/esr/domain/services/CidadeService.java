package com.dan.esr.domain.services;

import com.dan.esr.domain.entities.Cidade;
import com.dan.esr.domain.entities.Estado;
import com.dan.esr.domain.exceptions.CidadeNaoEncontradaException;
import com.dan.esr.domain.exceptions.CidadeNaoPersistidaException;
import com.dan.esr.domain.exceptions.EntidadeEmUsoException;
import com.dan.esr.domain.repositories.CidadeRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.HibernateException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.dan.esr.core.util.MessagesUtil.*;
import static com.dan.esr.core.util.ValidacaoCampoObrigatorioUtil.validarCampoObrigatorio;
import static org.springframework.beans.BeanUtils.copyProperties;

@Service
@RequiredArgsConstructor
public class CidadeService {
    private final CidadeRepository cidadeRepository;
    private final EstadoService estadoService;

    @Transactional
    public Cidade salvarOuAtualizar(Cidade cidade) {
        validarCampoObrigatorio(cidade.getEstado(), "Estado");
        Long estadoId = cidade.getEstado().getId();
        Estado estado = this.estadoService.buscarEstadoPorId(estadoId);
        cidade.setEstado(estado);

        try {
            return cidade.isNova() ? this.salvar(cidade) : this.atualizar(cidade);
        } catch (HibernateException ex) {
            if (cidade.isNova()) {
                throw new CidadeNaoPersistidaException(MSG_CIDADE_NAO_SALVA
                        .formatted(cidade.getNome()), ex.getCause());
            } else {
                throw new CidadeNaoPersistidaException(MSG_CIDADE_NAO_ATUALIZADA
                        .formatted(cidade.getNome()), ex.getCause());
            }
        }
    }

    private Cidade salvar(Cidade cidade) {
        return this.cidadeRepository.salvarOuAtualizar(cidade)
                .orElse(null);
    }

    private Cidade atualizar(Cidade cidade) {
        Cidade cidadeRegistro = this.buscarCidadePorId(cidade.getId());
        copyProperties(cidade, cidadeRegistro, "id");
        return this.cidadeRepository.salvarOuAtualizar(cidadeRegistro).orElse(null);
    }

    @Transactional
    public void remover(Long id) {
        Cidade cidadeRegistro = this.buscarCidadePorId(id);

        try {
            this.cidadeRepository.remover(cidadeRegistro.getId());
        } catch (DataIntegrityViolationException ex) {
            throw new EntidadeEmUsoException(MSG_CIDADE_EM_USO.formatted(id), ex.getCause());
        }
    }

    public Cidade buscarCidadePorId(Long id) {
        try {
            return this.cidadeRepository.buscarPorId(id).orElse(null);

        } catch (EmptyResultDataAccessException ex) {
            throw new CidadeNaoEncontradaException(id, ex.getCause());
        }
    }

    public List<Cidade> buscarTodos() {
        List<Cidade> cidades = this.cidadeRepository.buscarTodos();
        validar(cidades);
        return cidades;
    }

    private void validar(List<Cidade> cidades) {
        if (cidades.isEmpty()) {
            throw new CidadeNaoEncontradaException(MSG_CIDADE_NAO_ENCONTRADA);
        }
    }
}
